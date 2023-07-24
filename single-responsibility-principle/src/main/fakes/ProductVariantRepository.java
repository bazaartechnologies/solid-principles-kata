package src.main.fakes;

import com.bazaar.api.catalog.controller.dto.response.ProductPropertiesResponseDto;
import com.bazaar.api.catalog.model.ProductVariant;

import java.util.List;

public interface ProductVariantRepository {
    List<ProductVariant> findByProductIds(List<String> productIds);

    List<ProductVariant> findByIdIn(List<String> variantIds);

    List<ProductPropertiesResponseDto> findPropertiesByIdIn(List<String> variantIds);

}
