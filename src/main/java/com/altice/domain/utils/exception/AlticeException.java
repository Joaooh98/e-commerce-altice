package com.altice.domain.utils.exception;



import com.altice.domain.enums.EnumErrorCode;
import com.altice.domain.utils.StringUtils;

/**
 *
 * @author JoaoCP
 */
public class AlticeException extends RuntimeException {

    private String errorCode = "-1";

    public AlticeException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public AlticeException(EnumErrorCode error) {
        super(error.getValue());
        this.errorCode = error.getKey();
    }

    public AlticeException(EnumErrorCode error, Object... args) {
        super(StringUtils.stringPatternFormat(error.getValue(), args));
        this.errorCode = error.getKey();
    }

    public String getErrorCode() {
        return errorCode;
    }

}
