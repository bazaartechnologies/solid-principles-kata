package ocp.fakes;


import ocp.NotificationGateway;

public class SlackGateway implements NotificationGateway {

    @Override
    public void sendNotification(NotificationPayloadDto payload) {
        // some logic here
    }

    public NotificationChannel getChannel() {
        return NotificationChannel.SLACK;
    }

}
