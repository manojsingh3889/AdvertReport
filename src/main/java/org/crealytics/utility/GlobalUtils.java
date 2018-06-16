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
    /**
     * Map which get initialised in static context.
     * This map contains MMM format month ordinal i.e.
     * <code>
     *     {
     *         {"JAN":1}
     *         {"FEB":2}
     *         ....
     *         {"DEC":12}
     *     }
     * </code>
     *
     * It doesn't need to be built in try cath as it is using only STL.
     */
    private static Map<String,Integer> shortMonthToOrdinal = Arrays.stream(Month.values()).collect(Collectors.toMap(month -> month.getDisplayName(TextStyle.SHORT,Locale.UK).toUpperCase(),
            Month::getValue));

    /**
     * This method converts a String into clazz type and return the value
     * @param clazz java type into which value has to be casted
     * @param value value which has to be casted
     * @param <T> generic param to keep inference in between clazz and return value
     * @return object of clazz type
     */
    public static <T> T parseValue(Class<T> clazz, String value){
        PropertyEditor editor = PropertyEditorManager.findEditor(clazz);
        editor.setAsText(value);
        return (T)editor.getValue();
    }

    /**
     * This method converts month String to month ordinal. It support 3 type of month string format
     * <ul>
     *     <li>Number (1-12)</li>
     *     <li>Initial 3-letters(case insensitive) i.e. Jan, Feb...</li>
     *     <li>Full month name name(case insensitive) i.e. January, February...</li>
     * </ul>
     * @param monthStr month string
     * @return month ordinal
     * @throws AppException if month string format violated
     */
    public static int getMonth(String monthStr) throws AppException {
        int month =0;
        if(monthStr.matches("[0-9]+")){
            month = Integer.parseInt(monthStr);
            if (month < 1 || month > 12) {
                throw new AppException(ExceptionCode.INVALID_MONTH,ExceptionMessage.INVALID_MONTH);
            }
        }else if(monthStr.length()==3){
            if(!shortMonthToOrdinal.containsKey(monthStr.toUpperCase()))
                throw new AppException(ExceptionCode.INVALID_MONTH,ExceptionMessage.INVALID_MONTH);

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

    /**
     * This method simple maps user input to actual values using {@link SITE}i.e.
     *<ul>
     *      <li>desktop_web( "desktop web")</li>
     *      <li>mobile_web( "mobile web")</li>
     *      <li>android( "android")</li>
     *      <li>iOS( "iOS")</li>
     *</ul>
     * @param site user input
     * @return actual site value
     * @throws AppException for non-existing site type
     */
    public static String getSite(String site) throws AppException {
        try {
            return SITE.valueOf(site).getName();
        } catch (IllegalArgumentException e) {
            throw new AppException(ExceptionCode.INVALID_SITE,ExceptionMessage.INVALID_SITE);
        }
    }

    /**
     * {@link SITE} enum is map user input to actual site value i.e. user_input("actual value")
     *<ul>
     *      <li>desktop_web( "desktop web")</li>
     *      <li>mobile_web( "mobile web")</li>
     *      <li>android( "android")</li>
     *      <li>iOS( "iOS")</li>
     *</ul>
     */
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

    /**
     * this method simple round down floating point to the scale of 2
     * @param value value to be scaled
     * @return scaled value
     */
    public static float precise(float value){
        return new BigDecimal(value).setScale(2,BigDecimal.ROUND_DOWN).floatValue();
    }

    /**
     * this method simple round down floating point of double to the scale of 2
     * @param value value to be scaled
     * @return scaled value
     */
    public static double precise(double value){
        return new BigDecimal(value).setScale(2,BigDecimal.ROUND_DOWN).doubleValue();
    }
}
