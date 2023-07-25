package ocp.fakes;

import com.bazaar.api.notification.constant.ChannelResponse;
import com.bazaar.api.notification.constant.NotificationChannel;
import com.bazaar.api.notification.dto.ChannelResponseDto;
import com.bazaar.api.notification.dto.NotificationPayloadDto;
import com.bazaar.api.notification.gateway.CommunicationGateway;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@Qualifier("smtpEmailGateway")
public class SMTPEmailGateway implements CommunicationGateway {
    private static final Logger logger = LogManager.getLogger(SMTPEmailGateway.class);
    public static final String SUBJECT = "Your Email from Bazaar";
    private final JavaMailSender javaMailSender;

    public SMTPEmailGateway(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public ChannelResponseDto sendNotification(NotificationPayloadDto payloadDto) {

        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setTo(payloadDto.getReceiver());
        msg.setSubject(SUBJECT);
        msg.setText(payloadDto.getMessage());

        try {
            logger.info("Sending email using SMTP gateway");
            javaMailSender.send(msg);
        } catch (MailException ex) {
            return new ChannelResponseDto(ChannelResponse.ERROR, ex.getMessage());
        }

        return new ChannelResponseDto(ChannelResponse.SUCCESS, "sent");
    }

    @Override
    public NotificationChannel getChannel() {
        return NotificationChannel.SMTP;
    }
}
