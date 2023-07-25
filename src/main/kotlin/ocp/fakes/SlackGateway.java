package ocp.fakes;


public class SlackGateway {

    public void sendNotification(NotificationPayloadDto payload) {
        // some logic here
    }

    public NotificationChannel getChannel() {
        return NotificationChannel.SLACK;
    }

}
