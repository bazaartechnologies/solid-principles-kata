package lsp

import lsp.fakes.*

class TopupService(
    topupGateway: TopupGateway,
    apiKey: String
) {
    private val topupGateway: TopupGateway
    private val apiKey: String

    init {
        this.topupGateway = topupGateway
        this.apiKey = apiKey
    }

    fun createTopupRequest(
        userId: String?,
        userChannel: UserChannel?,
        requestPayload: TopupRequestDto
    ): PaymentResponseDto {
        return try {
            val tax = requestPayload.getTaxPercentage()
            val response: PaymentResponseDto =
                topupGateway.createTopupRequest(apiKey, userId, userChannel, requestPayload)
            response
        } catch (exception: Exception) {
            throw TopupCreationFailedException(userId, exception)
        }
    }
}