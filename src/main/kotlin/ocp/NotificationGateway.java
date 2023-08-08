package ocp;

import ocp.fakes.NotificationPayloadDto;

public interface NotificationGateway {
    void sendNotification(NotificationPayloadDto payloadDto);
}