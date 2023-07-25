package srp

import org.junit.jupiter.api.Test
import srp.fakes.OrganizationId.BAZAAR_INDUSTRIAL
import srp.fakes.OrganizationId.BAZAAR_RETAIL
import srp.fakes.ProductCityLevelResponseDto
import srp.fakes.ProductVariant
import srp.fakes.ProductVariantRepository
import srp.fakes.ProductVariantZone
import srp.fakes.ProductVariantZoneRepository
import srp.fakes.ZoneType

import kotlin.test.assertEquals

class ProductServiceTest {

    @Test
    fun `fetch Products City Level Details for Bazaar`() {
        val productVariant = ProductVariant(
            "variantId",
            "description",
            10.0,
            18.0,
            20.0
        )
        val productVariantZone = ProductVariantZone(
            "variantId",
            ZoneType.CITY,
            "zoneId",
            "description",
            10.0,
            2.0,
            18.0,
            20.0,
        )

        val productService = ProductService(
            FakeProductVariantZoneRepository(productVariantZone),
            FakeProductVariantRepository(productVariant)
        )

        val productCityLevelResponseDto = listOf(
            ProductCityLevelResponseDto(
                variantId = "variantId",
                zoneType = "CITY",
                zoneId = "zoneId",
                description = "description",
                priceRetail = 18.0,
                costPerItem = 20.0,)
        )

        assertEquals(productCityLevelResponseDto,
            productService.fetchProductsCityLevelDetails(
                ZoneType.CITY,
                "123",
                listOf("4211", "4212"),
                BAZAAR_RETAIL)
        )
    }

    @Test
    fun `fetch Products City Level Details for Industrial`() {
        val productVariant = ProductVariant(
            "variantId",
            "description",
            20.0,
            18.0,
            18.0
        )
        val productVariantZone = ProductVariantZone(
            "variantId",
            ZoneType.CITY,
            "zoneId",
            "description",
            20.0,
            2.0,
            18.0,
            20.0,
        )

        val productService = ProductService(
            FakeProductVariantZoneRepository(productVariantZone),
            FakeProductVariantRepository(productVariant)
        )

        val productCityLevelResponseDto = listOf(
            ProductCityLevelResponseDto(
                variantId = "variantId",
                zoneType = "CITY",
                zoneId = "zoneId",
                description = "description",
                priceRetail = 18.0,
                costPerItem = 20.0,)
        )

        assertEquals(productCityLevelResponseDto,
            productService.fetchProductsCityLevelDetails(
                ZoneType.CITY,
                "123",
                listOf("4211", "4212"),
                BAZAAR_INDUSTRIAL)
        )
    }
}

class FakeProductVariantZoneRepository(private val productVariantZone: ProductVariantZone = ProductVariantZone()) : ProductVariantZoneRepository {
    override fun findByZoneTypeAndZoneIdAndVariantIdIn(
        zoneType: ZoneType?,
        zoneId: String?,
        variantIds: MutableList<String>?
    ): MutableList<ProductVariantZone> {
        return mutableListOf(productVariantZone)
    }

}

class FakeProductVariantRepository(private val productVariant: ProductVariant = ProductVariant()) : ProductVariantRepository {

    override fun findByIdIn(variantIds: MutableList<String>?): MutableList<ProductVariant> {
        return mutableListOf(productVariant)
    }
}
