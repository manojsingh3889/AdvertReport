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
import org.springframework.data.domain.Example;
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
     * @param monthStr month string
     * @param siteStr site string
     * @return send single report
     * @throws AppException
     */
    public AdDetailReport aggregatedReport(String monthStr, String siteStr) throws AppException {
        int month = GlobalUtils.getMonth(monthStr);
        String site = null;

        if(siteStr != null)
            site = GlobalUtils.getSite(siteStr);

        return aggregatedReport(month,site);
    }

    private AdDetailReport aggregatedReport(Integer month, String site) throws AppException {
        List<AdDetail> ads = get(month,site);

        if(ads.isEmpty())
            throw new AppException(ExceptionCode.NO_RECORD_FOUND,ExceptionMessage.NO_RECORD_FOUND);

        AdDetailReport detailReport = new AdDetailReport()
                .setMonth(Month.of(month).toString())
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
     * @param siteStr site string
     * @return send single report
     * @throws AppException
     */
    public List<AdDetailReport> report(String monthStr, String siteStr) throws AppException {
        int month = GlobalUtils.getMonth(monthStr);
        String site = null;

        if(siteStr != null)
            site = GlobalUtils.getSite(siteStr);

        return report(month,site);
    }


    private List<AdDetailReport> report(int month, String site) throws AppException {
        List<AdDetail> ads = get(month,site);

        if(ads.isEmpty())
            throw new AppException(ExceptionCode.NO_RECORD_FOUND,ExceptionMessage.NO_RECORD_FOUND);

        return ads.stream()
                .map(ad -> new AdDetailReport(ad))
                .collect(Collectors.toList());
    }

    private List<AdDetail> get(Integer month, String site){
        if(site == null)
            return repository.findByMonth(month);
        else
            return repository.findByMonthAndSite(month,site);
    }
}
