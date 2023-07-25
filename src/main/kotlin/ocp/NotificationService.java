package ocp;


public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void sendNotification(final String source, final NotificationChannel channel, final NotificationPayloadDto payload) throws Exception {
        //update the referenceId if its present

        final String referenceId = ofNullable(payload.getFromMeta(REFERENCE_ID)).orElse("NA");
        notificationLog.setReferenceId(referenceId);

        notificationLog = notificationRepository.save(notificationLog);
        payload.setMessageId(notificationLog.getId());

        try {
            if (channel == NotificationChannel.sms) {
                sms.sendNotification(payload);
            } else if (channel == NotificationChannel.otp) {
                otp.sendNotification(payload);
            } else if (channel == NotificationChannel.email) {
                email.sendNotification(payload);
            }
            notificationLog.setResponse(response);
        } catch (final Exception ex) {
            notificationLog.setResponse(new ChannelResponseDto(ChannelResponse.ERROR, ex.getMessage()));
        } finally {
            notificationRepository.save(notificationLog);
        }
    }
}