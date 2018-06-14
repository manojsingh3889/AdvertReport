package org.crealytics.repository;

import org.crealytics.bean.AdDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<AdDetail, Long> {
    /**
     * TO find all the addetails matching to supplied month and site
     * @param month month ordinal
     * @param site site name
     * @return list of {@link AdDetail}
     */
     List<AdDetail> findByMonthAndSite(int month, String site);
}
