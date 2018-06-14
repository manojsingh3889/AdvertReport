package org.crealytics.service;

import org.crealytics.bean.AdDetail;

import java.util.List;

/**
 * Interface to specify save and search feature for {@link AdDetail} objects.
 */
public interface AdService {
    /**
     * This method is to specify feature to save single record.
     * @param adDetail record to save
     * @return persisted version of record
     */
    AdDetail insert(AdDetail adDetail);

    /**
     * This method is to specify feature  save bulk record at once record.
     * @param adDetails records to save
     * @return persisted version of records
     */
    List<AdDetail> insertAll(List<AdDetail> adDetails);
}
