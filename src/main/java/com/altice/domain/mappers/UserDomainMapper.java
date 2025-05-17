package com.altice.domain.mappers;

import com.altice.domain.bo.UserBO;
import com.altice.domain.dto.UserDTO;
import com.altice.domain.enums.EnumErrorCode;
import com.altice.domain.utils.exception.AlticeException;
import com.altice.domain.vo.UuidVO;

public class UserDomainMapper {

    public static UserBO toBO(UserDTO userDTO) {
        if (userDTO == null) {
            throw new AlticeException(EnumErrorCode.REQUIRED_OBJECT, "userDTO");
        }

        return UserBO.builder()
                .id(new UuidVO(userDTO.getId()))
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .build();
    }

    public static UserDTO toDTO(UserBO userBO) {
        if (userBO == null) {
            throw new AlticeException(EnumErrorCode.REQUIRED_OBJECT, "userBO");
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId(userBO.getId() != null ? userBO.getId().getValue().toString() : null);
        userDTO.setName(userBO.getName());
        userDTO.setEmail(userBO.getEmail());
        userDTO.setPassword(userBO.getPassword());

        return userDTO;
    }

}
