package org.crealytics.exception;

/**
 * {@link AppException} is custom exception to throw back any logical, validation, internal error to caller by wrapping
 * specific code from {@link ExceptionCode} and message from {@link ExceptionMessage}
 */
public class AppException extends Exception{
    private ErrorDetail detail;

    public AppException() {
        detail = new ErrorDetail(ExceptionCode.INTERNAL_SERVER_EXCEPTION,ExceptionMessage.INTERNAL_SERVER_EXCEPTION);
    }

    public ErrorDetail getDetail() {
        return detail;
    }

    public AppException(Integer errorcode, String errormessage) {
        super();
        detail = new ErrorDetail(errorcode,errormessage);
    }

    public AppException(Integer errorcode,String errormessage, Throwable cause) {
        super(cause);
        detail = new ErrorDetail(errorcode,errormessage);
    }

    public AppException(Throwable cause) {
        super(cause);
        detail = new ErrorDetail(ExceptionCode.INTERNAL_SERVER_EXCEPTION,ExceptionMessage.INTERNAL_SERVER_EXCEPTION);
    }

    @Override
    public String getMessage() {
        return String.format("Error [ code: %s, message: %s ]",detail.errorcode,detail.errormessage);
    }

    /**
     * Sub class to convert exception int parsable data
     */
    public class ErrorDetail{
        private Integer errorcode;
        private String errormessage;

        public ErrorDetail(Integer errorcode, String errormessage) {
            this.errorcode = errorcode;
            this.errormessage = errormessage;
        }

        public Integer getErrorcode() {
            return errorcode;
        }

        public void setErrorcode(Integer errorcode) {
            this.errorcode = errorcode;
        }

        public String getErrormessage() {
            return errormessage;
        }

        public void setErrormessage(String errormessage) {
            this.errormessage = errormessage;
        }
    }
}

