package org.crealytics.integeration;

import org.assertj.core.api.Assertions;
import org.crealytics.BootStrap;
import org.crealytics.bean.AdDetail;
import org.crealytics.bean.AdDetailReport;
import org.crealytics.controller.FrontController;
import org.crealytics.exception.AppException;
import org.crealytics.exception.ExceptionCode;
import org.crealytics.exception.ExceptionMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.crealytics.utility.GlobalUtils.precise;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootStrap.class)
public class JavaLevelIntegerationTest {

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
            ResponseEntity<AdDetailReport> responseEntity = controller.reports("1","desktop_web");
            AdDetailReport report = responseEntity.getBody();
            assertEquals(report.getCtr(),new Float(precise(((float)report.getClicks()/report.getImpressions())*100)));
            assertEquals(report.getCr(),new Float(precise(((float)report.getConversions()/report.getImpressions())*100)));
            assertEquals(report.getFillRate(),new Float(precise(((float)report.getImpressions()/report.getRequests())*100)));
            assertEquals(report.getEcpm(),new Float(precise(((float)report.getRevenue()*1000)/report.getImpressions())));
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