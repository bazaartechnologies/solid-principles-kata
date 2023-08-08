package lsp.fakes;

public interface TopupGateway {

    PaymentResponseDto createTopupRequest(
            String apiKey,
            String userId,
            UserChannel userChannel,
            TopupRequestDto requestPayload
    );

}