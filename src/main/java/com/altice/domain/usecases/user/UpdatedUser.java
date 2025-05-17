package com.altice.domain.usecases.user;

import java.util.UUID;

import com.altice.domain.dto.UserDTO;
import com.altice.domain.enums.EnumErrorCode;
import com.altice.domain.mappers.UserDomainMapper;
import com.altice.domain.repositories.IUserRepository;
import com.altice.domain.utils.StringUtils;
import com.altice.domain.utils.exception.AlticeException;

public class UpdatedUser {

    private final IUserRepository userRepository;

    public UpdatedUser(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO execute(UserDTO userDTO, String id) {
        if (StringUtils.isNullOrEmpty(id)) {
            throw new AlticeException(EnumErrorCode.REQUIRED_FIELD_FOR, "id", "update user");
        }
        
        if (userDTO == null) {
            throw new AlticeException(EnumErrorCode.REQUIRED_OBJECT_FOR, "userDTO", "update user");
        }

        var internalUser = userRepository.findById(UUID.fromString(id));

        if (internalUser == null) {
            throw new AlticeException(EnumErrorCode.OBJECT_NOT_FOUND, "user");
        }

        userDTO.setId(internalUser.getId().getValue().toString());

        var response = userRepository.updated(UserDomainMapper.toBO(userDTO));
        return UserDomainMapper.toDTO(response);
    }
}
