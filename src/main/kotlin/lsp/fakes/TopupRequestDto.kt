package lsp.fakes

abstract class TopupRequestDto {
    private var type: TopupType? = null
    private var requestedAmount: Double? = null
    private var zoneId: String? = null

    constructor()
    constructor(type: TopupType?, requestedAmount: Double?, zoneId: String?) {
        this.type = type
        this.requestedAmount = requestedAmount
        this.zoneId = zoneId
    }

    val topupType: TopupType?
        get() = type

    open fun getTaxPercentage(): Int {
        return 10
    }

    abstract fun retryTopup()
}