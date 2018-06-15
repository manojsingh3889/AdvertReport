package org.crealytics.exception;

import org.junit.Test;

import static org.junit.Assert.*;

public class AppExceptionTest {

    @Test
    public void getDetail() {
        AppException appException = new AppException(ExceptionCode.NO_RECORD_FOUND,ExceptionMessage.NO_RECORD_FOUND);
        AppException.ErrorDetail errorDetail = new AppException().new ErrorDetail(ExceptionCode.NO_RECORD_FOUND,ExceptionMessage.NO_RECORD_FOUND);
        assertEquals(appException.getDetail().getErrorcode(),errorDetail.getErrorcode());
        assertEquals(appException.getDetail().getErrormessage(),errorDetail.getErrormessage());
    }
}