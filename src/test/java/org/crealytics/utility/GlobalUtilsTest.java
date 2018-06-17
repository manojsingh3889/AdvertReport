package org.crealytics.utility;

import org.assertj.core.api.Assertions;
import org.crealytics.exception.AppException;
import org.crealytics.exception.ExceptionCode;
import org.crealytics.exception.ExceptionMessage;
import org.junit.Test;

import static org.crealytics.utility.GlobalUtils.*;
import static org.junit.Assert.*;

public class GlobalUtilsTest {

    @Test
    public void test_parseValue() {
        assertEquals(parseValue(Float.class,"1.23"),new Float(1.23));
        assertEquals(parseValue(Integer.class,"1"),new Integer(1));
    }

    @Test
    public void test_parseValue_negative() {
        assertNotEquals(parseValue(Float.class,"1.23452"),new Float(1.2345));
    }

    @Test(expected = NumberFormatException.class)
    public void test_parseValue_format_issue(){
        parseValue(Integer.class,"1.23");
    }

    @Test
    public void test_getMonth_by_number() throws AppException {
        assertEquals(getMonth("1"),1);
    }

    @Test(expected = AppException.class)
    public void test_getMonth_by_number_out_of_range_exception() throws AppException {
        getMonth("13");
    }

    @Test
    public void test_getMonth_by_number_out_of_range_exception_type() throws AppException {
        try {
            getMonth("14");
        } catch (AppException e) {
            assertEquals(e.getDetail().getErrorcode().intValue(),ExceptionCode.INVALID_MONTH);
            assertEquals(e.getDetail().getErrormessage(),ExceptionMessage.INVALID_MONTH);
        }
    }

    @Test
    public void test_getMonth_by_short_string() throws AppException {
        //check with case insensitive
        assertEquals(getMonth("DEC"),12);
        assertEquals(getMonth("feb"),2);
        assertEquals(getMonth("SeP"),9);
        assertEquals(getMonth("Jan"),1);
    }

    @Test(expected = AppException.class)
    public void test_getMonth_by_short_string_invalid_exception() throws AppException {
        getMonth("Jax");
    }

    @Test
    public void test_getMonth_by_short_string_invalid_exception_type() throws AppException {
        try {
            getMonth("Nova");
        } catch (AppException e) {
            assertEquals(e.getDetail().getErrorcode().intValue(),ExceptionCode.INVALID_MONTH);
            assertEquals(e.getDetail().getErrormessage(),ExceptionMessage.INVALID_MONTH);
        }
    }

    @Test
    public void test_getMonth_by_full_string() throws AppException {
        //check with case insensitive
        assertEquals(getMonth("January"),1);
        assertEquals(getMonth("October"),10);
        assertEquals(getMonth("SePtember"),9);
        assertEquals(getMonth("february"),2);
    }

    @Test(expected = AppException.class)
    public void test_getMonth_by_full_string_invalid_exception() throws AppException {
        getMonth("decembar");
    }

    @Test
    public void test_getMonth_by_full_string_invalid_exception_type() throws AppException {
        try {
            getMonth("Febuary");
        } catch (AppException e) {
            assertEquals(e.getDetail().getErrorcode().intValue(),ExceptionCode.INVALID_MONTH);
            assertEquals(e.getDetail().getErrormessage(),ExceptionMessage.INVALID_MONTH);
        }
    }

    @Test
    public void test_site() throws AppException {
        //check with case insensitive
        assertEquals(getSite("desktop_web"),"desktop web");
        assertEquals(getSite("mobile_web"),"mobile web");
    }

    @Test(expected = AppException.class)
    public void test_invalid_site() throws AppException {
        getSite("desktop site");
    }

    @Test
    public void test_invalid_site_exception_type() throws AppException {
        try {
            getSite("desktop site");
        } catch (AppException e) {
            assertEquals(e.getDetail().getErrormessage(),ExceptionMessage.INVALID_SITE);
            assertEquals(e.getDetail().getErrorcode().intValue(),ExceptionCode.INVALID_SITE);
        }
    }

    @Test
    public void test_SITE_enums(){
        assertEquals(GlobalUtils.SITE.mobile_web.getName(),"mobile web");
    }

    @Test
    public void test_precise_float_methods(){
        float found = GlobalUtils.precise(123.4567891F);
        Assertions.assertThat(found)
                .isNotNull()
                .isOfAnyClassIn(Float.class);
        assertTrue(found == 123.45f);
    }

    @Test
    public void test_precise_double_methods(){
        Double found = GlobalUtils.precise(123.4567891);
        Assertions.assertThat(found)
                .isNotNull()
                .isOfAnyClassIn(Double.class);
        assertTrue(found == 123.45);
    }

}