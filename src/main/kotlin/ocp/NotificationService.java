package com.bazaar.api.notification.service;

import com.bazaar.api.notification.constant.ChannelResponse;
import com.bazaar.api.notification.constant.NotificationChannel;
import com.bazaar.api.notification.dto.ChannelResponseDto;
import com.bazaar.api.notification.dto.NotificationPayloadDto;
import com.bazaar.api.notification.model.NotificationLog;
import com.bazaar.api.notification.registry.ChannelGatewayRegistry;
import com.bazaar.api.notification.repository.NotificationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import static com.bazaar.api.notification.constant.PayloadConstant.REFERENCE_ID;
import static java.util.Optional.ofNullable;

@Service
public class NotificationService {
    private static final Logger logger = LogManager.getLogger(NotificationService.class);

    private final NotificationRepository notificationRepository;
    private final ChannelGatewayRegistry channelGatewayRegistry;

    public NotificationService(NotificationRepository notificationRepository, ChannelGatewayRegistry channelGatewayRegistry) {
        this.notificationRepository = notificationRepository;
        this.channelGatewayRegistry = channelGatewayRegistry;
    }

    public void sendNotification(final String source, final NotificationChannel channel, final NotificationPayloadDto payload) throws Exception {
        logger.info("invoked method [{}]", "sendNotification");
        NotificationLog notificationLog = toNotificationLog(source, channel, payload);
        //update the referenceId if its present

        final String referenceId = ofNullable(payload.getFromMeta(REFERENCE_ID)).orElse("NA");
        notificationLog.setReferenceId(referenceId);

        notificationLog = notificationRepository.save(notificationLog);
        logger.info("persisted notificationLog with id [{}] and referenceId [{}]", notificationLog.getId(), referenceId);
        payload.setMessageId(notificationLog.getId());

        try {
            if (channel == NotificationChannel.sms) {
                sms.sendNotification(payload)
            } else if (channel == NotificationChannel.otp) {
                otp.sendNotification(payload)
            } else if (channel == NotificationChannel.email) {
                email.sendNotification(payload)
            }
            //final ChannelResponseDto response = channelGatewayRegistry.getChannelGateway(channel).sendNotification(payload);
            logger.info("api response [{}]", response);
            notificationLog.setResponse(response);
        } catch (final Exception ex) {
            notificationLog.setResponse(new ChannelResponseDto(ChannelResponse.ERROR, ex.getMessage()));
            logger.error("an error occured while sending notification", ex);
        } finally {
            notificationRepository.save(notificationLog);
        }
    }
}