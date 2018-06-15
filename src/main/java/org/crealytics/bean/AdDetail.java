package org.crealytics.bean;

import org.crealytics.utility.CSVProperty;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Objects;

/**
 * The {@link AdDetail} class represents monthly site wise report of Advert system.
 * This is ORM entity which will be represented as ad_detail relational entity in DB.
 * <p>
 * Ad detail contains several parameter which can help viewer to determine month and site
 * wise report for advert bussiness
 *
 * Data member:
 * <blockquote><pre>
 * Site: type of platform such desktop_web,android
 *
 * Request: In Ad Tech, it refers to a request for a creative or ad tag.
 *
 * Impression: A creative served to a single user at a single point in time.
 *
 * Clicks: Total clicks on particular platform and month combination.
 *
 * Conversion: When a user makes a purchase, or performs some other desired action in response to an ad.
 *
 * Revenue: The amount of money a publisher earns from ads showing.
 *
 * CTR (%): Click-through rate. Expressed as a percentage. Literally, the ratio of users who click on a specific link to the number of total users who view an advertisement. CTR = (clicks ÷ impressions) × 100%
 *
 * CR (%): Conversion rate. The ratio of conversions to the number of impressions. CR = (conversions ÷ impressions) × 100%
 *
 * Fill Rate: The ratio of impressions to the number of requests. It varies by inventory. Fill Rate = (impressions ÷ requests) × 100%
 *
 * ecpm: Effective Cost Per Thousand. A translation from CPM, expressed as such from a publisher's point of view. ecpm = (revenue × 1000) ÷ impressions
 *
 * <b>month</b>: month of year
 * </pre></blockquote>
 *
 * @author  Manoj
 */
@Entity
@Table(name="ad_detail")
public class AdDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String site;
    private Long requests;
    private Long impressions;
    private Long clicks;
    private Long conversions;
    @CSVProperty("revenue (USD)")
    private Float revenue;
    private Integer month;

    private Float ctr;
    private Float cr;
    private Float fillRate;
    private Float ecpm;

    @Transient
    private Boolean full = false;

    /**
     * no-arg constructor for building proxies etc..
     */
    public AdDetail() {
    }

    /**
     * parameterized  constructor
     * @param site site type
     * @param requests total request
     * @param impressions total creative served
     * @param clicks total clicks
     * @param conversions total positive product purchase
     * @param revenue total revenue
     */
    public AdDetail(@NonNull String site,@NonNull Long requests,@NonNull Long impressions,
                    @NonNull Long clicks,@NonNull Long conversions,@NonNull Float revenue,
                    @NonNull Integer month) {
        this.site = site;
        this.requests = requests;
        this.impressions = impressions;
        this.clicks = clicks;
        this.conversions = conversions;
        this.revenue = revenue;
        this.month = month;
        fullBuild();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Long getRequests() {
        return requests;
    }

    public void setRequests(Long requests) {
        this.requests = requests;
    }

    public Long getImpressions() {
        return impressions;
    }

    public void setImpressions(Long impressions) {
        this.impressions = impressions;
    }

    public Long getClicks() {
        return clicks;
    }

    public void setClicks(Long clicks) {
        this.clicks = clicks;
    }

    public Long getConversions() {
        return conversions;
    }

    public void setConversions(Long conversions) {
        this.conversions = conversions;
    }

    public Float getRevenue() {
        return revenue;
    }

    public void setRevenue(Float revenue) {
        this.revenue = revenue;
    }

    public Float getCtr() {
        return ctr;
    }

    public void setCtr(Float ctr) {
        this.ctr = ctr;
    }

    public Float getCr() {
        return cr;
    }

    public void setCr(Float cr) {
        this.cr = Float.valueOf(String.format("%.2f", cr));
    }

    public Float getFillRate() {
        return fillRate;
    }

    public void setFillRate(Float fillRate) {
        this.fillRate = fillRate;
    }

    public Float getEcpm() {
        return ecpm;
    }

    public void seteCPM(Float eCPM) {
        this.ecpm = eCPM;
    }


    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdDetail detail = (AdDetail) o;
        return Objects.equals(id, detail.id) &&
                Objects.equals(site, detail.site) &&
                Objects.equals(requests, detail.requests) &&
                Objects.equals(impressions, detail.impressions) &&
                Objects.equals(clicks, detail.clicks) &&
                Objects.equals(conversions, detail.conversions) &&
                Objects.equals(revenue, detail.revenue) &&
                Objects.equals(month,detail.month);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, site, requests, impressions, clicks, conversions, revenue, month);
    }

    /**
     * This method is to calculate relative values
     * @return self
     */
    public AdDetail fullBuild(){
        if (!full) {
            setCr(((float)clicks/impressions)*100);
            ctr = Float.valueOf((float)clicks/impressions)*100;
            cr = ((float)conversions/impressions)*100;
            fillRate = ((float)impressions/requests)*100;
            ecpm = (float)(revenue*1000)/impressions;
        }

        return this;
    }

    /**
     * This is pre persist hook to make sure relative values are built before saving to DB
     */
    @PreUpdate
    @PrePersist
    protected void onCreate() {
        if(!full)
            fullBuild();
    }

    @Override
    public String toString() {
        return "AdDetail{" +
                "site='" + site + '\'' +
                ", requests=" + requests +
                ", impressions=" + impressions +
                ", clicks=" + clicks +
                ", conversions=" + conversions +
                ", revenue=" + revenue +
                ", month=" + month +
                '}';
    }
}
