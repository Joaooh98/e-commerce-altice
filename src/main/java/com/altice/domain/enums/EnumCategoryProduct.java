package com.altice.domain.enums;

import com.altice.domain.utils.EnumUtil;

public enum EnumCategoryProduct implements IEnum {

    MOBILE_PHONE("MOBILE_PHONE", "mobile phones"),
    GAMING("GAMING", "gaming"),
    COMPUTING("COMPUTING", "computing"),
    TELEVISIONS("TELEVISIONS", "televisions");

    private final String key;
    
    private final String value;

    EnumCategoryProduct(String key, String value) {
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

    public static EnumCategoryProduct parseByKey(String key) {
        return EnumUtil.parseByKey(EnumCategoryProduct.class, key);
    }

    public static EnumCategoryProduct parseByValue(String value) {
        return EnumUtil.parseByValue(EnumCategoryProduct.class, value);
    }
}
