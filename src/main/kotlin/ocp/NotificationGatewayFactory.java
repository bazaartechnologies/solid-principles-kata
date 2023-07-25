package ocp;

import ocp.fakes.NotificationChannel;
import ocp.fakes.SMSGateway;
import ocp.fakes.SMTPEmailGateway;
import ocp.fakes.SlackGateway;
import ocp.gateway.NotificationGateway;

import java.util.HashMap;
import java.util.Map;

public class NotificationGatewayFactory {
    Map<NotificationChannel, NotificationGateway> notificationGatewayMap;


    public NotificationGatewayFactory() {
        notificationGatewayMap = new HashMap<>();
        notificationGatewayMap.put(NotificationChannel.SMS, new SMSGateway());
        notificationGatewayMap.put(NotificationChannel.SLACK, new SlackGateway());
        notificationGatewayMap.put(NotificationChannel.SMTP, new SMTPEmailGateway());
    }

    public NotificationGateway getNotificationGateway(NotificationChannel notificationChannel) {
        return notificationGatewayMap.get(notificationChannel);
    }
}
