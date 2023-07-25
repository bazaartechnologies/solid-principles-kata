package ocp.fakes;


public class NotificationPayloadDto {
    public String referenceId;
    public String sender;

    public String receiver;

    public NotificationPayloadDto(String referenceId, String sender, String receiver) {
        this.referenceId = referenceId;
        this.sender = sender;
        this.receiver = receiver;
    }
}
