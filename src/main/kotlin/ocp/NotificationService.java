package ocp;


import ocp.fakes.NotificationChannel;
import ocp.fakes.NotificationLog;
import ocp.fakes.NotificationPayloadDto;
import ocp.fakes.NotificationRepository;
import ocp.fakes.SMSGateway;
import ocp.fakes.SMTPEmailGateway;
import ocp.fakes.SlackGateway;

import java.util.Map;


public class NotificationService {

    public final NotificationRepository notificationRepository;
    private Map<NotificationChannel, NotificationGateway> notificationGatewayMap = Map.of(
            NotificationChannel.SMS, new SMSGateway(),
            NotificationChannel.SLACK, new SlackGateway(),
            NotificationChannel.SMTP, new SMSGateway()
    );

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void sendNotification(final String source, final NotificationChannel channel, final NotificationPayloadDto payload) throws Exception {
        //update the referenceId if its present

        final String referenceId = "ref";
        NotificationLog notificationLog = new NotificationLog("refId", "sender", "receiver");

        notificationLog = notificationRepository.save(notificationLog);

        try {
            NotificationGateway notificationGateway = getNotificationGateway(channel);
            notificationGateway.sendNotification(payload);
        } catch (final Exception ex) {
            ex.printStackTrace();
        } finally {
            notificationRepository.save(notificationLog);
        }
    }

    private NotificationGateway getNotificationGateway(NotificationChannel channel) {
        return this.notificationGatewayMap.get(channel);
    }
}