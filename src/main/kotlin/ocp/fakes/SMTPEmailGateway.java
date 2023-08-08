package ocp.fakes;

import ocp.NotificationGateway;

public class SMTPEmailGateway implements NotificationGateway {

    public void sendNotification(NotificationPayloadDto payloadDto) {
        // some logic here
    }

    public NotificationChannel getChannel() {
        return NotificationChannel.SMTP;
    }
}
