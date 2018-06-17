package org.crealytics.service;

import org.crealytics.bean.AdDetail;
import org.crealytics.bean.AdDetailReport;
import org.crealytics.exception.AppException;
import org.crealytics.exception.ExceptionCode;
import org.crealytics.exception.ExceptionMessage;
import org.crealytics.repository.AdRepository;
import org.crealytics.utility.GlobalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This spring based service is to provide implementation for features of {@link AdService}
 */
@Service
public class AdServiceImpl implements AdService{
    private static final Logger LOGGER = LoggerFactory.getLogger(AdServiceImpl.class);

    @Autowired
    AdRepository repository;

    /**
     * This method is used to save single record.
     * @param adDetail record to save
     * @return persisted version of record
     */
    public AdDetail insert(AdDetail adDetail){
        LOGGER.info("inserting single AdDetail");
        LOGGER.debug(adDetail.toString());
        return repository.save(adDetail);
    }

    /**
     * This method is used to save bulk record at once record.
     * @param adDetails records to save
     * @return persisted version of records
     */
    public List<AdDetail> insertAll(List<AdDetail> adDetails){
        LOGGER.info("inserting multiple AdDetails");
        LOGGER.debug(adDetails.toString());
        return repository.saveAll(adDetails);
    }

    /**
     * This method is to fetch records by month-site in aggregated form
     * @param monthStr month string, to provide flexibility to use MMM, month-ordinal or full name of month
     * @param siteStr site string (optional)
     * @return send single report
     * @throws AppException for bad request
     */
    public AdDetailReport aggregatedReport(String monthStr, String siteStr) throws AppException {
        //fetch appropriate ordinal for month string
        int month = GlobalUtils.getMonth(monthStr);
        String site = null;
        //if site not null then fetch appropriate value else call the function for month aggregate
        if(siteStr != null)
            site = GlobalUtils.getSite(siteStr);

        return aggregatedReport(month,site);
    }

    /**
     * This method is internal functionality and overloaded version of {@link #aggregatedReport(String, String)} which
     * works once month and site feasible values are obtained
     * @param month ordinal
     * @param site site type (optional)
     * @return returns Adreport for month-site combination in which site can be optional
     * @throws AppException for ambiguous situation or for no record
     */
    private AdDetailReport aggregatedReport(Integer month, String site) throws AppException {
        List<AdDetail> ads = get(month,site);

        if(ads.isEmpty())
            throw new AppException(ExceptionCode.NO_RECORD_FOUND,ExceptionMessage.NO_RECORD_FOUND);

        String m = Month.of(month).toString();
        AdDetailReport detailReport = new AdDetailReport()
                .setMonth(m.substring(0,1)+m.substring(1).toLowerCase())
                .setSite(site)
                .setRequests(ads.stream().mapToLong(AdDetail::getRequests).sum())
                .setImpressions(ads.stream().mapToLong(AdDetail::getImpressions).sum())
                .setClicks(ads.stream().mapToLong(AdDetail::getClicks).sum())
                .setConversions(ads.stream().mapToLong(AdDetail::getConversions).sum())
                .setRevenue((float) ads.stream().mapToDouble(AdDetail::getRevenue).sum())
                .fullBuild();

        return detailReport;
    }

    /**
     * This method is fetch records by month-site combination in list form
     * @param monthStr month string
     * @param siteStr site string (optional)
     * @return send single report
     * @throws AppException for bad request
     */
    public List<AdDetailReport> report(String monthStr, String siteStr) throws AppException {
        int month = GlobalUtils.getMonth(monthStr);
        String site = null;

        if(siteStr != null)
            site = GlobalUtils.getSite(siteStr);

        return report(month,site);
    }

    /**
     * This mehtod is internal functionality and overloaded version of {@link #report(String, String)}  which
     * works once month and site feasible values are obtained.
     * @param month ordinal
     * @param site site type (optional)
     * @return returns Adreport for month-site combination in which site can be optional
     * @throws AppException for ambiguous situation or for no record
     */
    private List<AdDetailReport> report(int month, String site) throws AppException {
        List<AdDetail> ads = get(month,site);

        if(ads.isEmpty())
            throw new AppException(ExceptionCode.NO_RECORD_FOUND,ExceptionMessage.NO_RECORD_FOUND);

        return ads.stream()
                .map(ad -> new AdDetailReport(ad))
                .collect(Collectors.toList());
    }

    /**
     * Core functional to support data extraction from JPA repository for {@link #report(int, String)} and {@link #aggregatedReport(Integer, String)}
     * @param month ordinal
     * @param site site type (optional)
     * @return list of JPA entity {@link AdDetail}
     */
    private List<AdDetail> get(Integer month, String site){
        if(site == null)
            return repository.findByMonth(month);
        else
            return repository.findByMonthAndSite(month,site);
    }
}
