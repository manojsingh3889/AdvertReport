package org.crealytics.exception;

/**
 * Constant container for error code in number
 */
public interface ExceptionCode {
    /*Using range 4xx to easily map with http level errorcode*/
    int INTERNAL_SERVER_EXECPTION = 410;
    int INVALID_MONTH = 411;
    int INVALID_SITE = 412;
    int NO_RECORD_FOUND = 413;
}
