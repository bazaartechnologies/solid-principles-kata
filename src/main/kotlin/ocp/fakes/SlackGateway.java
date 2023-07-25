package ocp.fakes;


public class SlackGateway {

    public void sendNotification(NotificationPayloadDto payload) {

    }

    public NotificationChannel getChannel() {
        return NotificationChannel.SLACK;
    }

}
