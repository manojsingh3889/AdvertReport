package org.crealytics.controller;

import org.crealytics.bean.AdDetailReport;
import org.crealytics.exception.AppException;
import org.crealytics.service.AdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * {@link RestController} is a webservices provider. It exposes several reporting APIs, which can be consumed by client over HTTP(s)
 * Since it's a spring based component stereotype therefore it automatically get prepared and injected.
 */
@RestController
@RequestMapping("/api")
public class FrontController {

    public static final Logger logger = LoggerFactory.getLogger(RestController.class);

    @Autowired
    AdService service;

    /**
     * {@link #reports(String, String)} supports /api/report. This api server 2 purpose
     *<ul>
     *     <li>Serves as month-site detail reporter</li>
     *     <li>Serves as month aggregator function, to access this feature client simply need to site parameter from request</li>
     *</ul>
     * @param month which month detail required (mandatory)
     * @param site which site detail required (optional)
     * @return Addetail response
     * @throws AppException Bad_request or internal error. This error are caught and comprehensively sent by {@link RestExceptionHandler}
     */
    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public ResponseEntity<AdDetailReport> reports(@RequestParam(value = "month") String month,
                                                  @RequestParam(value = "site", required = false) String site) throws AppException {

        AdDetailReport detailReport = service.aggregatedReport(month, site);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(detailReport, header, HttpStatus.OK);
    }

    /**
     * {@link #reportsAsList(String, String)} } supports /api/report/list.
     * This api is similiar to {@link #reports(String, String)} only difference is it doesn't clubs the entries rather sends them as json array
     * This api server 2 purpose:
     *<ul>
     *     <li>Serves as month-site detail reporter</li>
     *     <li>Serves as month aggregator function, to access this feature client simply need to site parameter from request</li>
     *</ul>
     * @param month which month detail required (mandatory)
     * @param site which site detail required (optional)
     * @return list AdDetils
     * @throws AppException Bad_request or internal error. This error are caught and comprehensively sent by {@link RestExceptionHandler}
     */
    @RequestMapping(value = "/report/list", method = RequestMethod.GET)
    public ResponseEntity<List<AdDetailReport>> reportsAsList(@RequestParam(value = "month") String month,
                                                              @RequestParam(value = "site", required = false) String site) throws AppException {

        List<AdDetailReport> reports = service.report(month, site);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(reports, header, HttpStatus.OK);
    }
}