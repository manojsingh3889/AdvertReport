package org.crealytics.controller;

import org.assertj.core.api.Assertions;
import org.crealytics.bean.AdDetail;
import org.crealytics.bean.AdDetailReport;
import org.crealytics.exception.AppException;
import org.crealytics.exception.ExceptionCode;
import org.crealytics.exception.ExceptionMessage;
import org.crealytics.service.AdService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

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
    public void reportsAsList() {
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

    @Test(expected = AppException.class)
    public void reportsAsList_mandatory_param_missing() throws AppException {
            controller.reportsAsList(null,"desktop_web");
    }

    @Test
    public void reportsAsList_mandatory_param_missing_exception_type() {
        try {
            controller.reportsAsList(null,"desktop_web");
        } catch (AppException e) {
            assertEquals(e.getDetail().getErrorcode().intValue(),ExceptionCode.INVALID_MONTH);
            assertEquals(e.getDetail().getErrormessage(),ExceptionMessage.INVALID_MONTH);
        }
    }

    @Test
    public void reports() {
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

    @Test(expected = AppException.class)
    public void reports_mandatory_param_missing() throws AppException {
        controller.reports(null,"desktop_web");
    }

    @Test
    public void reports_mandatory_param_missing_exception_type() {
        try {
            controller.reports(null,"desktop_web");
        } catch (AppException e) {
            assertEquals(e.getDetail().getErrorcode().intValue(),ExceptionCode.INVALID_MONTH);
            assertEquals(e.getDetail().getErrormessage(),ExceptionMessage.INVALID_MONTH);
        }
    }
}