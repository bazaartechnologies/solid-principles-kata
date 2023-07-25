package ocp.fakes;

import ocp.gateway.NotificationGateway;

public class SMTPEmailGateway  implements NotificationGateway {

    public void sendNotification(NotificationPayloadDto payloadDto) {
        // some logic here
    }

    public NotificationChannel getChannel() {
        return NotificationChannel.SMTP;
    }
}
