package org.crealytics.utility;

import org.crealytics.exception.AppException;
import org.crealytics.exception.ExceptionCode;
import org.crealytics.exception.ExceptionMessage;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.math.BigDecimal;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Global utility contains utility functions for regular use
 */
public class GlobalUtils {

    private static Map<String,Integer> shortMonthToOrdinal = Arrays.stream(Month.values()).collect(Collectors.toMap(month -> month.getDisplayName(TextStyle.SHORT,Locale.UK).toUpperCase(),
                Month::getValue));

    public static <T> T parseValue(Class<T> clazz, String value){
        PropertyEditor editor = PropertyEditorManager.findEditor(clazz);
        editor.setAsText(value);
        return (T)editor.getValue();
    }

    public static int getMonth(String monthStr) throws AppException {
        int month =0;
        if(monthStr.matches("[0-9]+")){
            month = Integer.parseInt(monthStr);
            if (month < 1 || month > 12) {
                throw new AppException(ExceptionCode.INVALID_MONTH,ExceptionMessage.INVALID_MONTH);
            }
        }else if(monthStr.length()==3){
            month = shortMonthToOrdinal.get(monthStr.toUpperCase());
        }else{
            try {
                month = Month.valueOf(monthStr.toUpperCase()).getValue();
            } catch (IllegalArgumentException e) {
                throw new AppException(ExceptionCode.INVALID_MONTH,ExceptionMessage.INVALID_MONTH);
            }
        }
        return month;
    }

    public static String getSite(String site) throws AppException {
        try {
            return SITE.valueOf(site).getName();
        } catch (IllegalArgumentException e) {
            throw new AppException(ExceptionCode.INVALID_SITE,ExceptionMessage.INVALID_SITE);
        }
    }

    public enum SITE {
        desktop_web( "desktop web"),
        mobile_web( "mobile web"),
        android( "android"),
        iOS( "iOS");

        private String name;

        SITE(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static float precise(float value){
        return new BigDecimal(value).setScale(2,BigDecimal.ROUND_DOWN).floatValue();
    }

    public static double precise(double value){
        return new BigDecimal(value).setScale(2,BigDecimal.ROUND_DOWN).doubleValue();
    }

    public static <T> T safeValue(T t,T defaultVal){
        return t != null? t:defaultVal;
    }
}
