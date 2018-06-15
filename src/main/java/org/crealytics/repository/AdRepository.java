package org.crealytics.repository;

import org.crealytics.bean.AdDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This class is extended {@link Repository} to CRUD {@link JpaRepository}, whose implementation will be injected by Spring automatically
 */
@Repository
public interface AdRepository extends JpaRepository<AdDetail, Long> {
    /**
     * TO find all the Addetails matching to supplied month and site
     * @param month month ordinal
     * @param site site name
     * @return list of {@link AdDetail}
     */
     List<AdDetail> findByMonthAndSite(int month, String site);

    /**
     * TO find all the Addetails matching to supplied month
     * @param month month ordinal
     * @return list of {@link AdDetail}
     */
    List<AdDetail> findByMonth(int month);
}
