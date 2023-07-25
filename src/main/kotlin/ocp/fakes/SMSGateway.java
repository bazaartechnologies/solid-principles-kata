package ocp.fakes;

import ocp.gateway.NotificationGateway;

public class SMSGateway implements NotificationGateway {

    public void sendNotification(NotificationPayloadDto dto) {
        // some logic here
    }

    public NotificationChannel getChannel() {
        return NotificationChannel.SMS;
    }
}
