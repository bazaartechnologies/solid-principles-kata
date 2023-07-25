package srp.fakes;


import srp.fakes.ProductVariant;

import java.util.List;

public interface ProductVariantRepository {
    List<ProductVariant> findByIdIn(List<String> variantIds);

}
