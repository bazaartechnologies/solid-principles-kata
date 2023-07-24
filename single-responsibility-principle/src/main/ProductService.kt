package src.main

import com.bazaar.api.catalog.constant.OrganizationId
import com.bazaar.api.catalog.controller.dto.response.ProductCityLevelResponseDto
import com.bazaar.api.catalog.exception.FailedToGetProductsCityLevelDetailsByCityIdAndVariantIds
import com.bazaar.api.common.constant.ZoneType


class ProductService(
    val productVariantZoneRepository: ProductVariantZoneRepository,
    val productVariantRepository: ProductVariantRepository,
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
            throw FailedToGetProductsCityLevelDetailsByCityIdAndVariantIds(zoneId, variantIds, ex)
        }
    }
}