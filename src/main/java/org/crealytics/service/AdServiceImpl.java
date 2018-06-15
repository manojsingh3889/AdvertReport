package org.crealytics.service;

import org.crealytics.bean.AdDetail;
import org.crealytics.repository.AdRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
