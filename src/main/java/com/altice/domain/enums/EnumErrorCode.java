package com.altice.domain.enums;

import org.apache.http.HttpStatus;

import com.altice.domain.utils.EnumUtil;

public enum EnumErrorCode implements IEnum {

    // API Errors
    REQUIRED_FIELD("001", "The field ({0}) is required!", HttpStatus.SC_BAD_REQUEST),
    REQUIRED_OBJECT("002", "The object ({0}) must be provided!", HttpStatus.SC_BAD_REQUEST),
    INVALID_FORMAT("003", "Invalid format", HttpStatus.SC_BAD_REQUEST),
    VALUE_NOT_ALLOWED("004", "Value ({0}) is not allowed: ({1})", HttpStatus.SC_BAD_REQUEST),
    INTERNAL_ERROR("005", "ERROR ({0})", HttpStatus.SC_BAD_REQUEST),
    OBJECT_NOT_FOUND("007", "The requested resource ({0}) was not found!", HttpStatus.SC_NOT_FOUND),

    // External Errors
    COMMUNICATION_ERROR("081", "An error occurred while processing the request sent to the partner ({0}): {1}.!",
            HttpStatus.SC_BAD_GATEWAY);

    private final String key;

    private final String error;

    private final int httpStatus;

    private EnumErrorCode(String key, String error, int httpStatus) {
        this.key = key;
        this.error = error;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return error;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    @Override
    public boolean containsInEnum(String key) {
        return parseByKey(key) != null;
    }

    public static EnumErrorCode parseByKey(String key) {
        return EnumUtil.parseByKey(EnumErrorCode.class, key);
    }
}
