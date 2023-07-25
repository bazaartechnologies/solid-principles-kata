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
    ) : List<ProductCityLevelResponseDto> {
        try {
            return if(organizationId == OrganizationId.BAZAAR_INDUSTRIAL){

                val productVariants = productVariantRepository.findByIdIn(variantIds)

                productVariants.map { productVariant ->
                    ProductCityLevelResponseDto.createFrom(productVariant)
                }.distinctBy { it.variantId }
            } else{
                val productVariantZones = productVariantZoneRepository.findByZoneTypeAndZoneIdAndVariantIdIn(zoneType, zoneId, variantIds)

                productVariantZones.map { productVariantZone ->
                    ProductCityLevelResponseDto.createFrom(productVariantZone)
                }.distinctBy { it.variantId }
            }

        } catch (ex : Exception){
            throw ex
        }
    }
}