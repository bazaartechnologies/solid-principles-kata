package srp.fakes


data class ProductCityLevelResponseDto(
    var variantId: String = "",
    var zoneType: String = "",
    var zoneId: String = "",
    var description: String = "",
    var priceRetail: Double = 0.0,
    var costPerItem: Double = 0.0,
    var salesTaxId: String = "",
    var purchaseTaxId: String = "",
    var advanceTaxApplicable: Boolean = false,
    var status: String = "",
    var channels: List<String> = listOf(""),
    var storageBlock: String = ""
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
//                salesTaxId = productVariantZone.productVariant.product.salesTaxId,
//                purchaseTaxId = productVariantZone.productVariant.product.purchaseTaxId,
//                advanceTaxApplicable = productVariantZone.productVariant.product.advanceTaxApplicable,
                status = productVariantZone.status.name,
                channels = productVariantZone.channels.toList(),
                storageBlock = productVariantZone.storageBlock.name
            )
        }

        fun createFrom(
            productVariant: ProductVariant
        ): ProductCityLevelResponseDto {
            return ProductCityLevelResponseDto(
                variantId = productVariant.id,
                description = productVariant.description,
                priceRetail = productVariant.retailPrice,
                costPerItem = productVariant.originalPrice,
                salesTaxId = productVariant.product.salesTaxId,
                purchaseTaxId = productVariant.product.purchaseTaxId,
                advanceTaxApplicable = productVariant.product.advanceTaxApplicable,
                channels = productVariant.channels.toList()
            )
        }
    }
}
