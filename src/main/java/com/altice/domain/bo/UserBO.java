package com.altice.domain.bo;

import java.util.regex.Pattern;

import com.altice.domain.enums.EnumErrorCode;
import com.altice.domain.utils.StringUtils;
import com.altice.domain.utils.exception.AlticeException;
import com.altice.domain.vo.UuidVO;

public class UserBO {

    private final UuidVO id;
    private final String name;
    private final String email;
    private final String password;

    private static final Pattern EMAIL_PATTERN = Pattern
            .compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

    private UserBO(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.email = builder.email;
        this.password = builder.password;
    }

    public UuidVO getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private UuidVO id;
        private String name;
        private String email;
        private String password;

        public Builder id(UuidVO id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public UserBO build() {
            validate();
            return new UserBO(this);
        }

        private void validate() {
            if (StringUtils.isNullOrEmpty(name)) {
                throw new AlticeException(EnumErrorCode.REQUIRED_FIELD_FOR, "name", "user");
            }

            if (StringUtils.isNullOrEmpty(email)) {
                throw new AlticeException(EnumErrorCode.REQUIRED_FIELD_FOR, "email", "user");
            } else if (!EMAIL_PATTERN.matcher(email).matches()) {
                throw new AlticeException(EnumErrorCode.INVALID_FORMAT_FIELD, "email", "user");
            }

            if (StringUtils.isNullOrEmpty(password)) {
                throw new AlticeException(EnumErrorCode.REQUIRED_FIELD_FOR, "password", "user");
            }

        }

    }

}