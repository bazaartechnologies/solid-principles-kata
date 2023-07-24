package src.main.fakes;

import com.bazaar.api.catalog.model.ProductVariantZone;
import com.bazaar.api.common.constant.StorageBlock;
import com.bazaar.api.common.constant.ZoneType;
import com.bazaar.api.common.constant.catalog.CustomerChannel;
import com.bazaar.api.common.event.product.dto.ProductStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface ProductVariantZoneRepository extends JpaRepository<ProductVariantZone, String> {

    List<ProductVariantZone> findByVariantId(String variantId);

    List<ProductVariantZone> findByVariantIdAndStatus(String variantId, ProductStatus status);

    @Modifying
    @Query(value = " UPDATE ProductVariantZone pz " +
            " SET pz.status = com.bazaar.api.common.event.product.dto.ProductStatus.INACTIVE " +
            " WHERE " +
            " pz.variantId = :variantId " +
            " AND " +
            " pz.zoneType = :zoneType "
    )
    void deactivateProductVariantZones(String variantId, ZoneType zoneType);


    @Query(" SELECT pvz " +
            " FROM ProductVariantZone pvz " +
            " INNER JOIN FETCH pvz.productVariant pv " +
            " INNER JOIN FETCH pv.product " +
            " WHERE pvz.zoneId = :cityId " +
            "   AND pvz.customerChannel = :customerChannel " +
            "   AND pvz.status = com.bazaar.api.common.event.product.dto.ProductStatus.ACTIVE " +
            "   AND pvz.variantId IN (:variantIds) ")
    List<ProductVariantZone> filterActiveVariantsForCityByCustomerChannel(String cityId, List<String> variantIds, CustomerChannel customerChannel);

    @Query(" SELECT pvz " +
            " FROM ProductVariantZone pvz " +
            " INNER JOIN FETCH pvz.productVariant pv " +
            " INNER JOIN FETCH pv.product " +
            " WHERE pvz.zoneId = :cityId " +
            "   AND pvz.customerChannel = :customerChannel " +
            "   AND pvz.status = com.bazaar.api.common.event.product.dto.ProductStatus.ACTIVE " +
            "   AND JSON_CONTAINS(pvz.channels, CONCAT('\"', :productChannel, '\"'), '$') = 1 " +
            "   AND pvz.variantId IN (:variantIds) ")
    List<ProductVariantZone> filterActiveVariantsForCityChannelByCustomerChannel(String cityId, List<String> variantIds, String productChannel, CustomerChannel customerChannel);

    @Query(" SELECT pvz " +
            " FROM ProductVariantZone pvz " +
            " WHERE pvz.customerChannel NOT IN (:customerChannels) " +
            " AND updatedAt >= :from ")
    List<ProductVariantZone> filterProductVariantZonesByDate(List<CustomerChannel> customerChannels, Timestamp from, Pageable pageable);


    List<ProductVariantZone> findAllByVariantIdAndZoneTypeAndZoneIdAndStatus(String variantId, ZoneType zoneType, String zoneId, ProductStatus status);

    Optional<List<ProductVariantZone>> findByVariantIdAndZoneTypeAndZoneId(String variantId, ZoneType zoneType, String zoneId);

    List<ProductVariantZone> findByVariantIdAndZoneIdAndZoneType(String variantId, String cityId, ZoneType zoneType);

    List<ProductVariantZone> findByVariantIdInAndZoneTypeAndZoneId(List<String> variantIds, ZoneType zoneType, String zoneId);

    List<ProductVariantZone> findByVariantIdIn(List<String> variantIds);

    List<ProductVariantZone> findByIdIn(List<String> id);


    @Query(" SELECT DISTINCT pv.productId FROM ProductVariant pv " +
            " JOIN pv.variantZones pvz " +
            " Where pvz.zoneType = :zoneType " +
            " AND pvz.zoneId = :cityId " +
            " AND pvz.storageBlock = :storageBlock " +
            " AND pvz.status = com.bazaar.api.common.event.product.dto.ProductStatus.ACTIVE " )
    List<String> findProductIdsByCityAndStorageBlock(String cityId, ZoneType zoneType, StorageBlock storageBlock);

    @Query("SELECT pvz FROM ProductVariantZone  pvz JOIN pvz.productVariant pv where pv.product.id = :productId")
    List<ProductVariantZone> findByProductId(String productId);

    List<ProductVariantZone> findByZoneTypeAndZoneIdAndVariantIdIn(ZoneType zoneType, String zoneId, List<String> variantIds);

}
