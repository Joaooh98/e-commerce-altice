package com.altice.domain.enums;

import com.altice.domain.utils.EnumUtil;

public enum EnumCheckoutStatus implements IEnum {

    PENDING("PENDING", "pending"),
    COMPLETED("COMPLETED", "completed");

    private final String key;
    private final String value;

    EnumCheckoutStatus(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean containsInEnum(String key) {
        return parseByKey(key) != null;
    }

    public static EnumCheckoutStatus parseByKey(String key) {
        return EnumUtil.parseByKey(EnumCheckoutStatus.class, key);
    }

    public static EnumCheckoutStatus parseByValue(String value) {
        return EnumUtil.parseByValue(EnumCheckoutStatus.class, value);
    }
}