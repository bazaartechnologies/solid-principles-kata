package srp.fakes;


import srp.fakes.ProductVariant;

import java.util.List;

public interface ProductVariantRepository {
    List<ProductVariant> findByProductIds(List<String> productIds);

    List<ProductVariant> findByIdIn(List<String> variantIds);


}
