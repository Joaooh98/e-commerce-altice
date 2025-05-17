package com.altice.domain.enums;

import com.altice.domain.utils.EnumUtil;

public enum EnumShoppingStatus implements IEnum {

    ACTIVE("ACTIVE", "active"),
    ABANDONED("ABANDONED", "abandoned"),
    CHECKED_OUT("CHECKED_OUT", "checked out");

    private final String key;
    
    private final String value;

    EnumShoppingStatus(String key, String value) {
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

    public static EnumShoppingStatus parseByKey(String key) {
        return EnumUtil.parseByKey(EnumShoppingStatus.class, key);
    }

    public static EnumShoppingStatus parseByValue(String value) {
        return EnumUtil.parseByValue(EnumShoppingStatus.class, value);
    }
}
