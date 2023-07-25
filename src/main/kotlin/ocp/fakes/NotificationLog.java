package ocp.fakes;

public class NotificationLog {

    public String referenceId;
    public String sender;

    public String receiver;


    public NotificationLog() {
    }

    public NotificationLog(String referenceId, String sender, String receiver) {
        this.referenceId = referenceId;
        this.sender = sender;
        this.receiver = receiver;
    }
}
