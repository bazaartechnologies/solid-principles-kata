package ocp;


import ocp.fakes.NotificationChannel;
import ocp.fakes.NotificationLog;
import ocp.fakes.NotificationPayloadDto;
import ocp.fakes.NotificationRepository;
import ocp.fakes.SlackGateway;

public class NotificationService {

    public final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void sendNotification(final String source, final NotificationChannel channel, final NotificationPayloadDto payload) throws Exception {
        //update the referenceId if its present

        final String referenceId = "ref";
        NotificationLog notificationLog = new NotificationLog("refId", "sender", "receiver");

        notificationLog = notificationRepository.save(notificationLog);

        try {
            if (channel == NotificationChannel.SMS) {
                sms.sendNotification(payload);
            } else if (channel == NotificationChannel.SLACK) {
                new SlackGateway().sendNotification(payload);
            } else if (channel == NotificationChannel.SMS_M3) {
                email.sendNotification(payload);
            }
        } catch (final Exception ex) {
            ex.printStackTrace();
        } finally {
            notificationRepository.save(notificationLog);
        }
    }
}