package com.altice.domain.enums;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author JoaoCP
 */
public enum EnumDateFormat {

    DDMMYYYYHHMMSS(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
    HHMM(DateTimeFormatter.ofPattern("HH:mm"));

    private final DateTimeFormatter dateFormat;

    private EnumDateFormat(DateTimeFormatter dateFormat) {
        this.dateFormat = dateFormat;
    }

    public DateTimeFormatter getFormat() {
        return dateFormat;
    }

    public final String format(LocalDate date) {
        return dateFormat.format(date);
    }

    public LocalDate parse(String source) throws ParseException {
        return LocalDate.parse(source);
    }

}
