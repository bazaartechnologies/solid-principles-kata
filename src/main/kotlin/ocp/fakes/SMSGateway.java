package ocp.fakes;

import ocp.NotificationGateway;

public class SMSGateway implements NotificationGateway {

    @Override
     public void sendNotification(NotificationPayloadDto dto) {
        // some logic here
    }

    public NotificationChannel getChannel() {
        return NotificationChannel.SMS;
    }
}
