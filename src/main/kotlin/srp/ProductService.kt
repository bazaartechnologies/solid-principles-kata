package srp

import srp.fakes.OrganizationId
import srp.fakes.ProductCityLevelResponseDto
import srp.fakes.ProductVariantRepository
import srp.fakes.ProductVariantZoneRepository
import srp.fakes.ZoneType


class ProductService(
    private val productVariantZoneRepository: ProductVariantZoneRepository,
    private val productVariantRepository: ProductVariantRepository,
) {

    fun fetchProductsCityLevelDetails(
        zoneType: ZoneType,
        zoneId: String,
        variantIds: List<String>,
        organizationId: String
    ): List<ProductCityLevelResponseDto> {
        try {
            if (organizationId == OrganizationId.BAZAAR_INDUSTRIAL)
                return getProductCityLevelResponseDtosForIndustrial(variantIds)

            return getProductCityLevelResponseDtos(zoneType, zoneId, variantIds)

        } catch (ex: Exception) {
            throw ex
        }
    }

    private fun getProductCityLevelResponseDtos(
        zoneType: ZoneType,
        zoneId: String,
        variantIds: List<String>
    ): List<ProductCityLevelResponseDto> {
        val productVariantZones =
            productVariantZoneRepository.findByZoneTypeAndZoneIdAndVariantIdIn(zoneType, zoneId, variantIds)

        return productVariantZones.map { productVariantZone ->
            ProductCityLevelResponseDto.createFrom(productVariantZone)
        }.distinctBy { it.variantId }
    }

    private fun getProductCityLevelResponseDtosForIndustrial(variantIds: List<String>): List<ProductCityLevelResponseDto> {
        val productVariants = productVariantRepository.findByIdIn(variantIds)

        return productVariants.map { productVariant ->
            ProductCityLevelResponseDto.createFrom(productVariant)
        }.distinctBy { it.variantId }
    }
}