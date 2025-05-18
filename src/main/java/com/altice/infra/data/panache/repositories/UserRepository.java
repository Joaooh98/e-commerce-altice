package com.altice.infra.data.panache.repositories;

import java.util.UUID;

import com.altice.domain.bo.UserBO;
import com.altice.domain.enums.EnumErrorCode;
import com.altice.domain.repositories.IUserRepository;
import com.altice.domain.utils.exception.AlticeException;
import com.altice.infra.data.panache.mappers.ModalUserMapper;
import com.altice.infra.data.panache.modal.ModalUser;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements IUserRepository {

    @Override
    public UserBO save(UserBO bo) {
        var modal = ModalUserMapper.toModal(bo);
        try {
            modal.persistAndFlush();
        } catch (Exception e) {
            throw new AlticeException(EnumErrorCode.INTERNAL_ERROR, e);
        }
        return ModalUserMapper.toBO(modal);
    }

    @Override
    public UserBO findById(UUID id) {
        try {
            ModalUser modal = ModalUser.findById(id);

            if (modal == null) {
                return null;
            }

            return ModalUserMapper.toBO(modal);

        } catch (Exception e) {
            throw new AlticeException(EnumErrorCode.INTERNAL_ERROR, e);
        }
    }

    @Override
    public void remove(UUID id) {
        try {

            var modal = ModalUser.findById(id);

            if (modal == null) {
                throw new AlticeException(EnumErrorCode.OBJECT_NOT_FOUND, "ModalUser");
            }

            modal.delete();

            ModalUser.flush();

        } catch (AlticeException ae) {
            throw ae;
        } catch (Exception e) {
            throw new AlticeException(EnumErrorCode.INTERNAL_ERROR, e);
        }
    }

    @Override
    public UserBO updated(UserBO bo) {
        try {
            var existingModal = ModalUser.findById(bo.getId().getValue());

            if (existingModal == null) {
                throw new AlticeException(EnumErrorCode.OBJECT_NOT_FOUND, "ModalUser");
            }

            var updatedModal = ModalUserMapper.toModal(bo);

            ModalUser.getEntityManager().merge(updatedModal);
            ModalUser.getEntityManager().flush();

            return ModalUserMapper.toBO(updatedModal);
        } catch (AlticeException ae) {
            throw ae;
        } catch (Exception e) {
            throw new AlticeException(EnumErrorCode.INTERNAL_ERROR, e);
        }
    }

}
