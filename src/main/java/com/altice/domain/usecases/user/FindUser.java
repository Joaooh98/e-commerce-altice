package com.altice.domain.usecases.user;

import java.util.UUID;

import com.altice.domain.dto.UserDTO;
import com.altice.domain.enums.EnumErrorCode;
import com.altice.domain.mappers.UserDomainMapper;
import com.altice.domain.repositories.IUserRepository;
import com.altice.domain.utils.StringUtils;
import com.altice.domain.utils.exception.AlticeException;

public class FindUser {

    private final IUserRepository userRepository;

    public FindUser(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO execute(String id) {
        if (StringUtils.isNullOrEmpty(id)) {
            throw new AlticeException(EnumErrorCode.REQUIRED_FIELD_FOR, "id", "find user");
        }
        
        var response = userRepository.findById(UUID.fromString(id));

        if (response == null) {
            throw new AlticeException(EnumErrorCode.OBJECT_NOT_FOUND, "User");
        }

        return UserDomainMapper.toDTO(response);
    }
}
