package srp

import srp.fakes.OrganizationId
import srp.fakes.ProductCityLevelResponseDto
import srp.fakes.ProductVariant
import srp.fakes.ProductVariantRepository
import srp.fakes.ProductVariantZoneRepository
import srp.fakes.ZoneType


interface Organization {
    fun getProductVariant(): List<ProductVariant>
}

class Industrial : Organization {
    override fun getProductVariant(): List<ProductVariant> {
        return listOf()
    }
}

class Retail : Organization {
    override fun getProductVariant(): List<ProductVariant> {
        return listOf()
    }
}


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
            // getOrganization(organization)
            // org.getProducVarint
            //
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