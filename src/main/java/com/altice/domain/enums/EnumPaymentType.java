package com.altice.domain.enums;

import com.altice.domain.utils.EnumUtil;

public enum EnumPaymentType implements IEnum {

    CARD("CARD", "card"),
    MBWAY("MBWAY", "mbway");

    private final String key;
    
    private final String value;

    EnumPaymentType(String key, String value) {
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

    public static EnumPaymentType parseByKey(String key) {
        return EnumUtil.parseByKey(EnumPaymentType.class, key);
    }

    public static EnumPaymentType parseByValue(String value) {
        return EnumUtil.parseByValue(EnumPaymentType.class, value);
    }
}
