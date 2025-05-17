package com.altice.infra.data.panache.mappers;

import com.altice.domain.bo.UserBO;
import com.altice.domain.enums.EnumErrorCode;
import com.altice.domain.utils.exception.AlticeException;
import com.altice.domain.vo.UuidVO;
import com.altice.infra.data.panache.modal.ModalUser;

public abstract class ModalUserMapper {

    public static ModalUser toModal(UserBO bo) {
        if (bo == null) {
            throw new AlticeException(EnumErrorCode.REQUIRED_OBJECT, "userBO");
        }

        ModalUser modal = new ModalUser();

        if (bo.getId() != null) {
            modal.setId(bo.getId().getValue());
        }

        modal.setName(bo.getName());
        modal.setEmail(bo.getEmail());
        modal.setPassword(bo.getPassword());

        return modal;
    }

    public static UserBO toBO(ModalUser modal) {
        if (modal == null) {
            throw new AlticeException(EnumErrorCode.REQUIRED_OBJECT, "modalUser");
        }

        return UserBO.builder()
                .id(new UuidVO(modal.getId().toString()))
                .name(modal.getName())
                .email(modal.getEmail())
                .password(modal.getPassword())
                .build();
    }
}