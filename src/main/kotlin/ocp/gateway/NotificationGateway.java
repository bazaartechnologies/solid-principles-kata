package ocp.gateway;

import ocp.fakes.NotificationChannel;
import ocp.fakes.NotificationPayloadDto;

public interface NotificationGateway {
    void sendNotification(NotificationPayloadDto payload);

    NotificationChannel getChannel();
}
