package srp.fakes;

import java.util.List;

import static java.util.Optional.ofNullable;

public class Product {

    public String title;

    public ProductStatus status;

    public String hsCode;

    public String brandId;

    public String productType;

    public String salesTaxId;

    public String purchaseTaxId;

    public Boolean advanceTaxApplicable = false;

    public String vendor;

    public String createdBy;

    public String updatedBy;

    public String organizationId = "7931660281019108183210";
    
    public List<ProductVariant> productVariants;


    public Product() {
    }
}