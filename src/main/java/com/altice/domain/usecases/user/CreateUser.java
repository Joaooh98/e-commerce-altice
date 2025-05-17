package com.altice.domain.usecases.user;

import com.altice.domain.dto.UserDTO;
import com.altice.domain.enums.EnumErrorCode;
import com.altice.domain.mappers.UserDomainMapper;
import com.altice.domain.repositories.IUserRepository;
import com.altice.domain.utils.exception.AlticeException;

public class CreateUser {

    private final IUserRepository userRepository;

    public CreateUser(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO execute(UserDTO userDTO) {
        if (userDTO == null) {
            throw new AlticeException(EnumErrorCode.REQUIRED_OBJECT_FOR, "userDTO", "create user");
        }

        var response = userRepository.save(UserDomainMapper.toBO(userDTO));
        return UserDomainMapper.toDTO(response);
    }
}
