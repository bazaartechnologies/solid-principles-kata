package ocp;


import ocp.fakes.NotificationChannel;
import ocp.fakes.NotificationLog;
import ocp.fakes.NotificationPayloadDto;
import ocp.fakes.NotificationRepository;
import ocp.gateway.NotificationGateway;

public class NotificationService {

    public final NotificationRepository notificationRepository;
    private final NotificationGatewayFactory notificationGatewayFactory;

    public NotificationService(NotificationRepository notificationRepository, NotificationGatewayFactory notificationGatewayFactory) {
        this.notificationRepository = notificationRepository;
        this.notificationGatewayFactory = notificationGatewayFactory;
    }

    public void sendNotification(final String source, final NotificationChannel channel, final NotificationPayloadDto payload) throws Exception {
        //update the referenceId if its present

        final String referenceId = "ref";
        NotificationLog notificationLog = new NotificationLog("refId", "sender", "receiver");

        notificationLog = notificationRepository.save(notificationLog);

        try {
            NotificationGateway notificationGateway = notificationGatewayFactory.getNotificationGateway(channel);
            notificationGateway.sendNotification(payload);
        } catch (final Exception ex) {
            ex.printStackTrace();
        } finally {
            notificationRepository.save(notificationLog);
        }
    }
}