package org.crealytics.controller;

import org.assertj.core.api.Assertions;
import org.crealytics.bean.AdDetail;
import org.crealytics.bean.AdDetailReport;
import org.crealytics.exception.AppException;
import org.crealytics.service.AdService;
import org.crealytics.service.AdServiceImpl;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FrontControllerTest {

    @MockBean
    AdService service;

    @Autowired
    FrontController controller;

    @Before
    public void setup() throws AppException {
        AdDetail adDetail = new AdDetail("desktop web",100L,1000L,12L,2L,100.0f,2);
        List<AdDetailReport> adDetailReports = new ArrayList<>();
        adDetailReports.add(new AdDetailReport(adDetail));

        Mockito.when(service.aggregatedReport("1","desktop_web"))
                .thenReturn(new AdDetailReport(adDetail));
        Mockito.when(service.report("1","desktop_web"))
                .thenReturn(adDetailReports);
    }

    @Test
    public void reports() {
        try {
            AdDetail adDetail = new AdDetail("desktop web",100L,1000L,12L,2L,100.0f,2);
            List<AdDetailReport> adDetailReports = new ArrayList<>();
            adDetailReports.add(new AdDetailReport(adDetail));

            ResponseEntity<List<AdDetailReport>> report = controller.reportsAsList("1","desktop_web");
            assertEquals(report.getBody().get(0).getClicks(),adDetail.getClicks());
            assertEquals(report.getBody().get(0).getImpressions(),adDetail.getImpressions());
            assertEquals(report.getBody().get(0).getCr(),adDetail.getCr());
            assertEquals(report.getBody().get(0).getCtr(),adDetail.getCtr());
        } catch (AppException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void reportsAsList() {
        try {
            AdDetail adDetail = new AdDetail("desktop web",100L,1000L,12L,2L,100.0f,2);
            ResponseEntity<AdDetailReport> report = controller.reports("1","desktop_web");
            assertEquals(report.getBody().getClicks(),adDetail.getClicks());
            assertEquals(report.getBody().getImpressions(),adDetail.getImpressions());
            assertEquals(report.getBody().getCr(),adDetail.getCr());
            assertEquals(report.getBody().getCtr(),adDetail.getCtr());
        } catch (AppException e) {
            Assertions.fail(e.getMessage());
        }
    }
}