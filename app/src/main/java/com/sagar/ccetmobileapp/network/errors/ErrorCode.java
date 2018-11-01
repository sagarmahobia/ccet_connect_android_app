package com.sagar.ccetmobileapp.network.errors;

/**
 * Created by SAGAR MAHOBIA on 01-Nov-18. at 15:21
 */
public enum ErrorCode {
    NULL(0),
    UserNotFoundException(1001),
    InvalidInputException(1002),
    InvalidCredentialsException(1003),
    EmailAlreadyUsedException(1005),
    InternalServerException(1006),
    UnknownDatabaseException(1004);


    private int errorCode;
    private static ErrorCode[] values = ErrorCode.values();

    ErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public static ErrorCode getErrorCode(int errorCode) {
        for (ErrorCode ec : values) {
            if (ec.getErrorCode() == errorCode) {
                return ec;
            }
        }
        return NULL;
    }

    private int getErrorCode() {
        return errorCode;
    }
}
