package ocp.fakes;

import com.bazaar.api.notification.constant.NotificationChannel;
import com.bazaar.api.notification.dto.ChannelResponseDto;
import com.bazaar.api.notification.dto.NotificationPayloadDto;
import com.bazaar.api.notification.gateway.CommunicationGateway;
import com.bazaar.api.notification.gateway.impl.sms.its.ITSSmsGateway;
import com.bazaar.api.notification.gateway.impl.sms.m3.M3SmsGateway;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("smsGateway")
public class SMSGateway implements CommunicationGateway {
    private static final Logger logger = LogManager.getLogger(SMSGateway.class);

    private final ITSSmsGateway itsSmsGateway;
    private final M3SmsGateway m3SmsGateway;

    public SMSGateway(ITSSmsGateway itsSmsGateway, M3SmsGateway m3SmsGateway) {
        this.itsSmsGateway = itsSmsGateway;
        this.m3SmsGateway = m3SmsGateway;
    }

    @Override
    @CircuitBreaker(name = "sms-circuit-breaker", fallbackMethod = "sendViaFallback")
    public ChannelResponseDto sendNotification(NotificationPayloadDto dto) {
        logger.info("Sending SMS ViaFallback for : [{}]", dto.getReceiver());
        final ChannelResponseDto response = itsSmsGateway.sendNotification(dto);
        logger.info("ended SMS ViaFallback for : [{}]", dto.getReceiver());
        return response;
    }

    public ChannelResponseDto sendViaFallback(NotificationPayloadDto dto, final Exception ex) {
        return m3SmsGateway.sendNotification(dto);
    }

    @Override
    public NotificationChannel getChannel() {
        return NotificationChannel.SMS;
    }
}
