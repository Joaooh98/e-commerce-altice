package com.altice.domain.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.jboss.logging.Logger;

import com.altice.domain.enums.EnumDateFormat;

import jakarta.inject.Inject;

public class DateUtils {

    @Inject
    public static Logger logger;

    public static final DateTimeFormatter DDMMYYYYHHMMSS = EnumDateFormat.DDMMYYYYHHMMSS.getFormat();

    public static String formatDDMMYYYYHHMMSS(LocalDateTime date) {
        return date != null ? DDMMYYYYHHMMSS.format(date) : "";
    }

}
