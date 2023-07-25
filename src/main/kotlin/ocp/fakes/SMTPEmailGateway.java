package ocp.fakes;

public class SMTPEmailGateway {

    public void sendNotification(NotificationPayloadDto payloadDto) {
        // some logic here
    }

    public NotificationChannel getChannel() {
        return NotificationChannel.SMTP;
    }
}
