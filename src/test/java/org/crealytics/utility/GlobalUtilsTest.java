package org.crealytics.utility;

import org.junit.Test;

import static org.crealytics.utility.GlobalUtils.parseValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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
}