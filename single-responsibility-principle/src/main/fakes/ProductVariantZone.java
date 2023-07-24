package src.main.fakes;

import com.bazaar.api.catalog.constant.ProductChannel;
import com.bazaar.api.common.constant.StorageBlock;
import com.bazaar.api.common.constant.ZoneType;
import com.bazaar.api.common.constant.catalog.CustomerChannel;
import com.bazaar.api.common.event.product.dto.ProductStatus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.bazaar.api.catalog.constant.ProductChannel.ADMIN_COMMANDO;
import static com.bazaar.api.catalog.constant.ProductChannel.AGENT_APP;
import static com.bazaar.api.catalog.constant.ProductChannel.CUSTOMER_APP;
import static com.bazaar.api.catalog.constant.ProductChannel.KHAATA_APP;
import static com.bazaar.api.common.constant.catalog.CustomerChannel.GENERAL_TRADE;
import static java.util.Optional.ofNullable;
import static javax.persistence.EnumType.STRING;
import static org.apache.commons.lang3.StringUtils.isNotBlank;


public class ProductVariantZone extends BaseEntity {

    private static final long serialVersionUID = -606544563942468576L;
    private static final String ACTIVE = "Active";

    @Column(name = "variant_id")
    private String variantId;

    @Enumerated(STRING)
    @Column(name = "zone_type")
    private ZoneType zoneType;

    @Enumerated(STRING)
    @Column(name = "customer_channel")
    private CustomerChannel customerChannel;

    @Column(name = "zone_id")
    private String zoneId;

    @Column(name = "description")
    private String description;

    @Column(name = "price_actual")
    private Double priceActual;

    @Column(name = "price_discounted")
    private Double priceDiscounted;

    @Column(name = "price_retail")
    private Double priceRetail;

    @Column(name = "cost_per_item")
    private Double costPerItem;

    @Column(name = "tax")
    private Double tax;

    @Enumerated(STRING)
    @Column(name = "status")
    private ProductStatus status;

    @Type(type = "json")
    @Column(name = "channels", columnDefinition = "json")
    private String[] channels;

    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id", insertable = false, updatable = false)
    private ProductVariant productVariant;


    @Column(name = "storage_block")
    @Enumerated(STRING)
    private StorageBlock storageBlock = StorageBlock.GENERAL;

    public ProductVariantZone() {
    }

    private ProductVariantZone(String id) {
        super(id);
    }

    public String getVariantId() {
        return variantId;
    }

    public ZoneType getZoneType() {
        return zoneType;
    }

    public CustomerChannel getCustomerChannel() {
        return customerChannel;
    }

    public String getZoneId() {
        return zoneId;
    }

    public String getDescription() {
        return description;
    }

    public Double getPriceActual() {
        return priceActual;
    }

    public void setPriceActual(Double priceActual) {
        this.priceActual = priceActual;
    }

    public Double getPriceDiscounted() {
        return priceDiscounted;
    }

    public void setPriceDiscounted(Double priceDiscounted) {
        this.priceDiscounted = priceDiscounted;
    }

    public Double getPriceRetail() {
        return priceRetail;
    }

    public Double getCostPerItem() {
        return costPerItem;
    }

    public Double getTax() {
        return tax;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public String[] getChannels() {
        return channels;
    }

    public boolean hasChannel(ProductChannel channel) {
        return Arrays.asList(channels).contains(channel.name());
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public ProductVariant getProductVariant() {
        return productVariant;
    }

    public StorageBlock getStorageBlock() {
        return storageBlock;
    }

    public String validateDescription(String newDescription) {
        return isNotBlank(newDescription) ? newDescription : this.description;
    }

    public Double validateRetailPrice(Integer priceRetail) {
        return priceRetail == null ? this.priceRetail : priceRetail.doubleValue();
    }

    public String[] getChannelProperties(Map<String, String> newAvailability) {

        Map<ProductChannel, String> channelMap = new HashMap<>();
        channelMap.put(CUSTOMER_APP, computeActiveState(newAvailability.get("CUSTOMER_APP"), CUSTOMER_APP, this.channels));
        channelMap.put(AGENT_APP, computeActiveState(newAvailability.get("AGENT_APP"), AGENT_APP, this.channels));
        channelMap.put(ADMIN_COMMANDO, computeActiveState(newAvailability.get("ADMIN_COMMANDO"), ADMIN_COMMANDO, this.channels));
        channelMap.put(KHAATA_APP, computeActiveState(newAvailability.get("KHAATA_APP"), KHAATA_APP, this.channels));


        return channelMap.entrySet().
                stream().filter(channel -> channel.getValue().equalsIgnoreCase(ACTIVE)).
                map(channel -> channel.getKey().name()).toArray(String[]::new);
    }

    public String computeActiveState(String available, ProductChannel channel, String[] channels) {
        Map<Boolean, String> channelState = new HashMap<>();
        channelState.put(true, "ACTIVE");
        channelState.put(false, "INACTIVE");

        return channelState.get(available.trim().equalsIgnoreCase(ACTIVE) || (available.isBlank() && (Arrays.asList(channels).contains(channel.name()))));

    }

    public static final class Builder {
        private String id;
        private String variantId;
        private ZoneType zoneType;
        private String zoneId;
        private CustomerChannel customerChannel = GENERAL_TRADE;
        private String description;
        private Double priceActual = 0.0;
        private Double priceDiscounted = 0.0;
        private Double priceRetail = 0.0;
        private Double costPerItem = 0.0;
        private Double tax = 0.0;
        private ProductStatus status;
        private String[] channels = new String[]{};
        private String createdBy;
        private String updatedBy;

        // entity relationships
        private ProductVariant productVariant;
        private StorageBlock storageBlock = StorageBlock.GENERAL;

        public Builder(ProductVariantZone model) {
            this.id = model.id;
            this.variantId = model.variantId;
            this.zoneType = model.zoneType;
            this.customerChannel = model.customerChannel;
            this.zoneId = model.zoneId;
            this.description = model.description;
            this.priceActual = model.priceActual;
            this.priceDiscounted = model.priceDiscounted;
            this.priceRetail = model.priceRetail;
            this.costPerItem = model.costPerItem;
            this.tax = model.tax;
            this.status = model.status;
            this.channels = model.channels;
            this.createdBy = model.createdBy;
            this.updatedBy = model.updatedBy;
            this.productVariant = model.productVariant;
            this.storageBlock = model.storageBlock;
        }

        public Builder(
                String id,
                String variantId,
                ZoneType zoneType,
                String zoneId,
                CustomerChannel customerChannel
        ) {
            this.id = id;
            this.variantId = variantId;
            this.zoneType = zoneType;
            this.zoneId = zoneId;
            ofNullable(customerChannel)
                    .ifPresent(channel -> this.customerChannel = channel);
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withPriceActual(Double priceActual) {
            this.priceActual = priceActual;
            return this;
        }

        public Builder withPriceDiscounted(Double priceDiscounted) {
            this.priceDiscounted = ofNullable(priceDiscounted).orElse(this.priceDiscounted);
            return this;
        }

        public Builder withPriceRetail(Double priceRetail) {
            this.priceRetail = ofNullable(priceRetail).orElse(this.priceRetail);
            return this;
        }

        public Builder withCostPerItem(Double costPerItem) {
            this.costPerItem = costPerItem;
            return this;
        }

        public Builder withTax(Double tax) {
            this.tax = tax;
            return this;
        }

        public Builder withStatus(ProductStatus status) {
            this.status = status;
            return this;
        }

        public Builder withChannels(String[] channels) {
            this.channels = channels;
            return this;
        }

        public Builder withCreatedBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public Builder withUpdatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
            return this;
        }

        public Builder withProductVariant(ProductVariant productVariant) {
            this.productVariant = productVariant;
            return this;
        }

        public Builder withStorageBlock(StorageBlock storageBlock) {
            this.storageBlock = storageBlock;
            return this;
        }


        public ProductVariantZone build() {
            ProductVariantZone model = new ProductVariantZone(this.id);
            model.variantId = this.variantId;
            model.zoneType = this.zoneType;
            model.customerChannel = this.customerChannel;
            model.zoneId = this.zoneId;
            model.description = this.description;
            model.priceActual = this.priceActual;
            model.priceDiscounted = this.priceDiscounted;
            model.priceRetail = this.priceRetail;
            model.costPerItem = this.costPerItem;
            model.tax = this.tax;
            model.status = this.status;
            model.channels = this.channels;
            model.createdBy = this.createdBy;
            model.updatedBy = this.updatedBy;
            model.productVariant = this.productVariant;
            model.storageBlock = this.storageBlock;
            return model;
        }
    }

}
