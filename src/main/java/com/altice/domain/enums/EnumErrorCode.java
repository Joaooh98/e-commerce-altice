package com.altice.domain.enums;

import org.apache.http.HttpStatus;

import com.altice.domain.utils.EnumUtil;

public enum EnumErrorCode implements IEnum {

    // API Errors
    REQUIRED_FIELD_FOR("001", "The field ({0}) is required for ({1})!", HttpStatus.SC_BAD_REQUEST),
    REQUIRED_OBJECT("002", "The object ({0}) must be provided!", HttpStatus.SC_BAD_REQUEST),
    REQUIRED_OBJECT_FOR("002", "The object ({0}) must be provided for ({1})!", HttpStatus.SC_BAD_REQUEST),
    INVALID_FORMAT("003", "Invalid format", HttpStatus.SC_BAD_REQUEST),
    VALUE_NOT_ALLOWED("004", "Value ({0}) is not allowed: ({1})", HttpStatus.SC_BAD_REQUEST),
    INTERNAL_ERROR("005", "ERROR ({0})", HttpStatus.SC_BAD_REQUEST),
    OBJECT_NOT_FOUND("007", "The requested resource ({0}) was not found!", HttpStatus.SC_NOT_FOUND),
    INVALID_FORMAT_FIELD("008", "Invalid format ({0})", HttpStatus.SC_BAD_REQUEST),
    REQUIRED_FIELD("009", "The field ({0}) is required!", HttpStatus.SC_BAD_REQUEST),
    NOT_STOCK("010", "Not enough stock of product ({0})", HttpStatus.SC_BAD_REQUEST),
    ITEM_ALREADY_EXISTS("010", "Item already exists in cart : ({0}) ", HttpStatus.SC_BAD_REQUEST),
    CART_IS_EMPTY("011", "Cannot checkout empty cart", HttpStatus.SC_BAD_REQUEST),
    CART_MUST_HAVE_USER("012", "Cart must be associated with a user for checkout", HttpStatus.SC_BAD_REQUEST),
    INSUFFICIENT_STOCK("013", "Insufficient stock for product ({0}). Available: ({1}), Requested: ({2})",
            HttpStatus.SC_BAD_REQUEST),
    PAYMENT_PROCESSING_FAILED("014", "Payment processing failed: ({0})", HttpStatus.SC_BAD_REQUEST),
    INVALID_PAYMENT_TYPE("015", "Invalid payment type: ({0})", HttpStatus.SC_BAD_REQUEST),
    CART_ALREADY_CHECKED_OUT("016", "Cart ({0}) has already been checked out", HttpStatus.SC_BAD_REQUEST);

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
