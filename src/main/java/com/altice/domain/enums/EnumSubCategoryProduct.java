package com.altice.domain.enums;

import com.altice.domain.utils.EnumUtil;

public enum EnumSubCategoryProduct implements IEnum {

    // MOBILE_PHONE Category
    SMARTPHONES("SMARTPHONES", "smartphones"),
    MOBILE_ACCESSORIES("MOBILE_ACCESSORIES", "mobile accessories"),
    CASES_COVERS("CASES_COVERS", "cases and covers"),
    CHARGERS_CABLES("CHARGERS_CABLES", "chargers and cables"),

    // GAMING Category
    CONSOLES("CONSOLES", "consoles"),
    GAMES("GAMES", "games"),
    GAMING_ACCESSORIES("GAMING_ACCESSORIES", "gaming accessories"),
    CONTROLLERS("CONTROLLERS", "controllers"),
    HEADSETS_GAMING("HEADSETS_GAMING", "gaming headsets"),

    // COMPUTING Category
    MONITORS("MONITORS", "monitors"),
    KEYBOARDS("KEYBOARDS", "keyboards"),
    MICE("MICE", "mice"),
    PROCESSORS("PROCESSORS", "processors"),
    MEMORY_RAM("MEMORY_RAM", "memory and RAM"),
    STORAGE("STORAGE", "storage devices"),
    GRAPHICS_CARDS("GRAPHICS_CARDS", "graphics cards"),
    LAPTOPS("LAPTOPS", "laptops"),
    DESKTOPS("DESKTOPS", "desktop computers"),

    // TELEVISIONS Category
    LED_TV("LED_TV", "LED televisions"),
    OLED_TV("OLED_TV", "OLED televisions"),
    SMART_TV("SMART_TV", "smart televisions"),
    TV_ACCESSORIES("TV_ACCESSORIES", "TV accessories"),
    SOUND_BARS("SOUND_BARS", "sound bars");

    private final String key;
    private final String value;

    EnumSubCategoryProduct(String key, String value) {
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

    public static EnumSubCategoryProduct parseByKey(String key) {
        return EnumUtil.parseByKey(EnumSubCategoryProduct.class, key);
    }

    public static EnumSubCategoryProduct parseByValue(String value) {
        return EnumUtil.parseByValue(EnumSubCategoryProduct.class, value);
    }

}