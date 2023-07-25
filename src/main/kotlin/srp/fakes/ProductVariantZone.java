package srp.fakes;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.util.Optional.ofNullable;
import static srp.fakes.CustomerChannel.GENERAL_TRADE;
import static srp.fakes.ProductChannel.ADMIN_COMMANDO;
import static srp.fakes.ProductChannel.AGENT_APP;
import static srp.fakes.ProductChannel.CUSTOMER_APP;
import static srp.fakes.ProductChannel.KHAATA_APP;


public class ProductVariantZone {

    public static final String ACTIVE = "Active";

    public String variantId;

    public ZoneType zoneType;

    public CustomerChannel customerChannel;

    public String zoneId;

    public String description;

    public Double priceActual;

    public Double priceDiscounted;

    public Double priceRetail;

    public Double costPerItem;

    public Double tax;

    public ProductStatus status;

    public String[] channels;

    public String createdBy;

    public String updatedBy;

    public ProductVariant productVariant;


    public StorageBlock storageBlock = StorageBlock.GENERAL;

    public ProductVariantZone() {
    }

    public ProductVariantZone(String variantId, ZoneType zoneType, String zoneId, String description, Double priceActual, Double priceDiscounted, Double priceRetail, Double costPerItem) {
        this.variantId = variantId;
        this.zoneType = zoneType;
        this.zoneId = zoneId;
        this.description = description;
        this.priceActual = priceActual;
        this.priceDiscounted = priceDiscounted;
        this.priceRetail = priceRetail;
        this.costPerItem = costPerItem;
    }
}
