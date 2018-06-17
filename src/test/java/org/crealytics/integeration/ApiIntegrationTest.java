package org.crealytics.integeration;

import org.crealytics.BootStrap;
import org.crealytics.repository.AdRepository;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootStrap.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")

public class ApiIntegrationTest {
private static final String REPORTS_API = "/api/reports";
    private static final String REPORTS_LIST_API = "/api/reports/list";
    @Autowired
    private MockMvc mvc;

    @Autowired
    private AdRepository adRepositoryImple;

    //positive cases
    @Test
    public void test_report_api_month_site() throws Exception {
        mvc.perform(get(REPORTS_API+"?month=1&site=desktop_web"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.month",CoreMatchers.is("January")))
                .andExpect(jsonPath("$.impressions",CoreMatchers.is(11866157)));
    }

    @Test
    public void test_report_list_api_month_site() throws Exception {
        mvc.perform(get(REPORTS_LIST_API+"?month=1&site=desktop_web"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].month",CoreMatchers.is("January")))
                .andExpect(jsonPath("$[0].impressions",CoreMatchers.is(11866157)))
                .andExpect(jsonPath("$.length()",CoreMatchers.is(1)));

    }
    //positive case - to show how api are capable of handling 3 formats of month case insensitively
    @Test
    public void test_report_api_short_month_site() throws Exception {
        mvc.perform(get(REPORTS_API+"?month=JAN&site=desktop_web"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.month",CoreMatchers.is("January")))
                .andExpect(jsonPath("$.impressions",CoreMatchers.is(11866157)));
    }

    @Test
    public void test_report_list_api_short_month_site() throws Exception {
        mvc.perform(get(REPORTS_LIST_API+"?month=Jan&site=desktop_web"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].month",CoreMatchers.is("January")))
                .andExpect(jsonPath("$[0].impressions",CoreMatchers.is(11866157)))
                .andExpect(jsonPath("$.length()",CoreMatchers.is(1)));

    }

    @Test
    public void test_report_api_full_month_site() throws Exception {
        mvc.perform(get(REPORTS_API+"?month=January&site=desktop_web"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.month",CoreMatchers.is("January")))
                .andExpect(jsonPath("$.impressions",CoreMatchers.is(11866157)));
    }

    @Test
    public void test_report_list_api_full_month_site() throws Exception {
        mvc.perform(get(REPORTS_LIST_API+"?month=january&site=desktop_web"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].month",CoreMatchers.is("January")))
                .andExpect(jsonPath("$[0].impressions",CoreMatchers.is(11866157)))
                .andExpect(jsonPath("$.length()",CoreMatchers.is(1)));

    }

    //positive test case - to show how missing site turns api into month aggregator type
    @Test
    public void test_report_api_month() throws Exception {
        mvc.perform(get(REPORTS_API+"?month=2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.month",CoreMatchers.is("February")))
                .andExpect(jsonPath("$.conversions",CoreMatchers.is(18071)));
    }

    @Test
    public void test_report_list_api_month() throws Exception {
        mvc.perform(get(REPORTS_LIST_API+"?month=2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[2].month", CoreMatchers.is("February")))
                .andExpect(jsonPath("$[2].impressions", CoreMatchers.is(8342439)))
                .andExpect(jsonPath("$.length()", CoreMatchers.is(4)));

    }

    //negative case - missing month
    @Test
    public void test_report_api_missing_month_only_site() throws Exception {
        mvc.perform(get(REPORTS_API+"?month=&site=desktop_web"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorcode",CoreMatchers.is(411)))
                .andExpect(jsonPath("$.errormessage",CoreMatchers.is("INVALID_MONTH: Month cannot be out of 1-12, Jan-Dec or January-December")));
    }

    @Test
    public void test_report_list_api_missing_month_only_site() throws Exception {
        mvc.perform(get(REPORTS_LIST_API+"?month=&site=desktop_web"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorcode",CoreMatchers.is(411)))
                .andExpect(jsonPath("$.errormessage",CoreMatchers.is("INVALID_MONTH: Month cannot be out of 1-12, Jan-Dec or January-December")));

    }

    @Test
    public void test_report_api_missing_month() throws Exception {
        mvc.perform(get(REPORTS_API+"?month="))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorcode",CoreMatchers.is(411)))
                .andExpect(jsonPath("$.errormessage",CoreMatchers.is("INVALID_MONTH: Month cannot be out of 1-12, Jan-Dec or January-December")));
    }

    @Test
    public void test_report_list_api_missing_month() throws Exception {
        mvc.perform(get(REPORTS_LIST_API+"?month="))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorcode",CoreMatchers.is(411)))
                .andExpect(jsonPath("$.errormessage",CoreMatchers.is("INVALID_MONTH: Month cannot be out of 1-12, Jan-Dec or January-December")));
    }

    //negative case - invalid month type
    @Test
    public void test_report_api_month_invalid_ordinal() throws Exception {
        mvc.perform(get(REPORTS_API+"?month=13"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorcode",CoreMatchers.is(411)))
                .andExpect(jsonPath("$.errormessage",CoreMatchers.is("INVALID_MONTH: Month cannot be out of 1-12, Jan-Dec or January-December")));
    }

    @Test
    public void test_report_list_api_month_invalid_ordinal() throws Exception {
        mvc.perform(get(REPORTS_LIST_API+"?month=13"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorcode",CoreMatchers.is(411)))
                .andExpect(jsonPath("$.errormessage",CoreMatchers.is("INVALID_MONTH: Month cannot be out of 1-12, Jan-Dec or January-December")));
    }

    @Test
    public void test_report_api_month_invalid_short_name() throws Exception {
        mvc.perform(get(REPORTS_API+"?month=Jax"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorcode",CoreMatchers.is(411)))
                .andExpect(jsonPath("$.errormessage",CoreMatchers.is("INVALID_MONTH: Month cannot be out of 1-12, Jan-Dec or January-December")));
    }

    @Test
    public void test_report_list_api_month_invalid_short_name() throws Exception {
        mvc.perform(get(REPORTS_LIST_API+"?month=Jax"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorcode",CoreMatchers.is(411)))
                .andExpect(jsonPath("$.errormessage",CoreMatchers.is("INVALID_MONTH: Month cannot be out of 1-12, Jan-Dec or January-December")));
    }

    @Test
    public void test_report_api_month_invalid_full_name() throws Exception {
        mvc.perform(get(REPORTS_API+"?month=octobar"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorcode",CoreMatchers.is(411)))
                .andExpect(jsonPath("$.errormessage",CoreMatchers.is("INVALID_MONTH: Month cannot be out of 1-12, Jan-Dec or January-December")));
    }

    @Test
    public void test_report_list_api_month_invalid_full_name() throws Exception {
        mvc.perform(get(REPORTS_LIST_API+"?month=sptember"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorcode",CoreMatchers.is(411)))
                .andExpect(jsonPath("$.errormessage",CoreMatchers.is("INVALID_MONTH: Month cannot be out of 1-12, Jan-Dec or January-December")));
    }

    //negative case - invalid site type
    @Test
    public void test_report_api_month_invalid_site() throws Exception {
        mvc.perform(get(REPORTS_API+"?month=feb&site=mobile_app"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorcode",CoreMatchers.is(412)))
                .andExpect(jsonPath("$.errormessage",CoreMatchers.is("INVALID_SITE:Site cannot be other than desktop_web,mobile_web,android or iOS")));
    }

    @Test
    public void test_report_list_api_month_invalid_site() throws Exception {
        mvc.perform(get(REPORTS_LIST_API+"?month=1&site=desktop_site"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorcode", CoreMatchers.is(412)))
                .andExpect(jsonPath("$.errormessage", CoreMatchers.is("INVALID_SITE:Site cannot be other than desktop_web,mobile_web,android or iOS")));
    }
}
