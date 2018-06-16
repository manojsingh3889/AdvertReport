package org.crealytics.integeration;

import org.assertj.core.api.Assertions;
import org.crealytics.BootStrap;
import org.crealytics.bean.AdDetail;
import org.crealytics.bean.AdDetailReport;
import org.crealytics.controller.FrontController;
import org.crealytics.exception.AppException;
import org.crealytics.exception.ExceptionCode;
import org.crealytics.exception.ExceptionMessage;
import org.crealytics.service.AdService;
import org.crealytics.service.AdServiceImpl;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootStrap.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class JavaLevelIntegeration {

    @Autowired
    FrontController controller;

    @Test
    public void reportsAsList() {
        try {
            AdDetail adDetail = new AdDetail("desktop web",12483775l,11866157l,
                    30965l,7608l,23555.46f,1);
            List<AdDetailReport> adDetailReports = new ArrayList<>();
            adDetailReports.add(new AdDetailReport(adDetail));

            ResponseEntity<List<AdDetailReport>> report = controller.reportsAsList("1","desktop_web");
            assertEquals(report.getBody().get(0).getClicks(),adDetailReports.get(0).getClicks());
            assertEquals(report.getBody().get(0).getImpressions(),adDetailReports.get(0).getImpressions());
            assertEquals(report.getBody().get(0).getCr(),adDetailReports.get(0).getCr());
            assertEquals(report.getBody().get(0).getCtr(),adDetailReports.get(0).getCtr());
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
            AdDetail adDetail = new AdDetail("desktop web",12483775l,11866157l,
                    30965l,7608l,23555.46f,1);
            AdDetailReport detailReport = new AdDetailReport(adDetail);
            ResponseEntity<AdDetailReport> report = controller.reports("1","desktop_web");
            assertEquals(report.getBody().getClicks(),detailReport.getClicks());
            assertEquals(report.getBody().getImpressions(),detailReport.getImpressions());
            assertEquals(report.getBody().getCr(),detailReport.getCr());
            assertEquals(report.getBody().getCtr(),detailReport.getCtr());
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