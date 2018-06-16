package org.crealytics.exception;

/**
 * Constant container for error message in string
 */
public interface ExceptionMessage {
    String INTERNAL_SERVER_EXCEPTION = "INTERNAL_SERVER_EXCEPTION: Some internal error. Please contact admin";
    String INVALID_MONTH = "INVALID_MONTH: Month cannot be out of 1-12, Jan-Dec or January-December";
    String INVALID_SITE = "INVALID_SITE:Site cannot be other than desktop_web,mobile_web,android or iOS";
    String NO_RECORD_FOUND = "NO_RECORD_FOUND: No such record";
    String BAD_REQUEST_DATA = "BAD REQUEST DATA";
}
