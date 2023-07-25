package ocp.fakes;

public class SMSGateway {

    public void sendNotification(NotificationPayloadDto dto) {
        // some logic here
    }

    public NotificationChannel getChannel() {
        return NotificationChannel.SMS;
    }
}
