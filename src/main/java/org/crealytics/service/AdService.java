package org.crealytics.service;

import org.crealytics.bean.AdDetail;
import org.crealytics.bean.AdDetailReport;
import org.crealytics.exception.AppException;

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
     * This method is to specify feature  to save bulk record at once record.
     * @param adDetails records to save
     * @return persisted version of records
     */
    List<AdDetail> insertAll(List<AdDetail> adDetails);

    /**
     * This method is to specify feature to fetch records by month-site in aggregated form
     * @param month month string
     * @param site site string
     * @return send single report
     * @throws AppException for bad request
     */
    AdDetailReport aggregatedReport(String month, String site) throws AppException;

    /**
     * This method is to specify feature to fetch records by month-site combination in list form
     * @param month month string
     * @param site site string
     * @return send single report
     * @throws AppException for bad request
     */
    List<AdDetailReport> report(String month, String site) throws AppException;
}
