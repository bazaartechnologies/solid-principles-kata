package ocp.fakes;


import ocp.gateway.NotificationGateway;

public class SlackGateway  implements NotificationGateway {

    public void sendNotification(NotificationPayloadDto payload) {
        // some logic here
    }

    public NotificationChannel getChannel() {
        return NotificationChannel.SLACK;
    }

}
