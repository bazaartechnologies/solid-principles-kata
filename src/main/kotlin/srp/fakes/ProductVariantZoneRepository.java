package srp.fakes;

import java.util.List;

public interface ProductVariantZoneRepository {

    List<ProductVariantZone> findByZoneTypeAndZoneIdAndVariantIdIn(ZoneType zoneType, String zoneId, List<String> variantIds);

}
