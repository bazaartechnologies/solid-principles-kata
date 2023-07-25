package srp.fakes;

import java.util.List;

public class ProductVariant {

    public String id;
    public String productId;

    public String title;

    public String sku;

    public String description;
    
    public Double originalPrice;

    public Double displayPrice;

    public Double retailPrice = 0.0;

    public Long quantityAvailable;

    public Long quantityReserved;

    public Double tax;

    
    public String[] channels;

    public String barcode;

    public String barcodePurchase = "";

    public String barcodeStock = "";

    public String barcodeSale = "";
    
    public String createdBy="";

    public String updatedBy="";
    public Product product;

    public List<ProductVariantZone> variantZones;

    public ProductVariant() {

    }

    public ProductVariant(String id, String description, Double originalPrice, Double displayPrice, Double retailPrice) {
        this.id = id;
        this.description = description;
        this.originalPrice = originalPrice;
        this.displayPrice = displayPrice;
        this.retailPrice = retailPrice;
    }
}
