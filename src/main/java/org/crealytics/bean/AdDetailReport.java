package org.crealytics.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.Month;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;
import static org.crealytics.utility.GlobalUtils.precise;

/**
 * Presentation bean built by data taken from {@link AdDetail}
 * refer {@link AdDetail}
 */
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({
       "month",
        "site",
        "requests",
        "impressions",
        "clicks",
        "conversions",
        "revenue",
        "CTR",
        "CR",
        "fill_rate",
        "ecpm"
})
public class AdDetailReport {
    private String site;
    private Long requests;
    private Long impressions;
    private Long clicks;
    private Long conversions;
    private Float revenue;
    @JsonProperty("CTR")
    private Float ctr;
    @JsonProperty("CR")
    private Float cr;
    @JsonProperty("fill_rate")
    private Float fillRate;
    @JsonProperty("ecpm")
    private Float ecpm;
    @JsonProperty("month")
    private String month;

    public AdDetailReport() {
    }

    /**
     * copy constructor
     * @param ads original
     */
    public AdDetailReport(AdDetail ads) {
        String m = Month.of(ads.getMonth()).toString();
        this.setMonth(m.substring(0,1)+m.substring(1).toLowerCase())
                .setSite(ads.getSite())
                .setRequests(ads.getRequests())
                .setImpressions(ads.getImpressions())
                .setClicks(ads.getClicks())
                .setConversions(ads.getConversions())
                .setRevenue(ads.getRevenue())
                .fullBuild();
    }

    public String getSite() {
        return site;
    }

    public AdDetailReport setSite(String site) {
        this.site = site;
        return this;
    }

    public Long getRequests() {
        return requests;
    }

    public AdDetailReport setRequests(Long requests) {
        this.requests = requests;
        return this;
    }

    public Long getImpressions() {
        return impressions;
    }

    public AdDetailReport setImpressions(Long impressions) {
        this.impressions = impressions;
        return this;
    }

    public Long getClicks() {
        return clicks;
    }

    public AdDetailReport setClicks(Long clicks) {
        this.clicks = clicks;
        return this;
    }

    public Long getConversions() {
        return conversions;
    }

    public AdDetailReport setConversions(Long conversions) {
        this.conversions = conversions;
        return this;
    }

    public Float getRevenue() {
        return revenue;
    }

    public AdDetailReport setRevenue(Float revenue) {
        this.revenue = revenue;
        return this;
    }

    public Float getCtr() {
        return ctr;
    }

    public AdDetailReport setCtr(Float ctr) {
        this.ctr = ctr;
        return this;
    }

    public Float getCr() {
        return cr;
    }

    public AdDetailReport setCr(Float cr) {
        this.cr = cr;
        return this;
    }

    public Float getFillRate() {
        return fillRate;
    }

    public AdDetailReport setFillRate(Float fillRate) {
        this.fillRate = fillRate;
        return this;
    }

    public Float getEcpm() {
        return ecpm;
    }

    public AdDetailReport setEcpm(Float ecpm) {
        this.ecpm = ecpm;
        return this;
    }

    public String getMonth() {
        return month;
    }

    public AdDetailReport setMonth(String month) {
        this.month = month;
        return this;
    }

    /**
     * This method is to calculate relative values
     * @return self
     */
    public AdDetailReport fullBuild(){
        ctr = precise(((float)clicks/impressions)*100);
        cr = precise(((float)conversions/impressions)*100);
        fillRate = precise(((float)impressions/requests)*100);
        ecpm = precise((revenue*1000)/impressions);

        return this;
    }
}
