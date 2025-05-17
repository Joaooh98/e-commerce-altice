package com.altice.domain.usecases.user;

import java.util.UUID;

import com.altice.domain.enums.EnumErrorCode;
import com.altice.domain.repositories.IUserRepository;
import com.altice.domain.utils.StringUtils;
import com.altice.domain.utils.exception.AlticeException;

public class RemoveUser {

    private final IUserRepository userRepository;

    public RemoveUser(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(String id) {
        if (StringUtils.isNullOrEmpty(id)) {
            throw new AlticeException(EnumErrorCode.REQUIRED_OBJECT_FOR, "id", "remove user");
        }

        userRepository.remove(UUID.fromString(id));
    }
}
