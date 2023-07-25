package srp.fakes


data class ProductCityLevelResponseDto(
    var variantId: String = "",
    var zoneType: String = "",
    var zoneId: String = "",
    var description: String = "",
    var priceRetail: Double = 0.0,
    var costPerItem: Double = 0.0
) {
    companion object {
        fun createFrom(
            productVariantZone: ProductVariantZone
        ): ProductCityLevelResponseDto {
            return ProductCityLevelResponseDto(
                variantId = productVariantZone.variantId,
                zoneType = productVariantZone.zoneType.name,
                zoneId = productVariantZone.zoneId,
                description = productVariantZone.description,
                priceRetail = productVariantZone.priceRetail,
                costPerItem = productVariantZone.costPerItem,
            )
        }

        fun createFrom(
            productVariant: ProductVariant
        ): ProductCityLevelResponseDto {
            return ProductCityLevelResponseDto(
                variantId = productVariant.id,
                zoneType = "CITY",
                zoneId = "zoneId",
                description = productVariant.description,
                priceRetail = productVariant.retailPrice,
                costPerItem = productVariant.originalPrice,
            )
        }
    }
}
