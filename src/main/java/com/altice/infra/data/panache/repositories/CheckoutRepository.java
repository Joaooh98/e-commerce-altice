package com.altice.infra.data.panache.repositories;

import java.util.UUID;

import com.altice.domain.bo.CheckoutBO;
import com.altice.domain.enums.EnumErrorCode;
import com.altice.domain.repositories.ICheckoutRepository;
import com.altice.domain.utils.exception.AlticeException;
import com.altice.infra.data.panache.mappers.ModalCheckoutMapper;
import com.altice.infra.data.panache.modal.ModalCheckout;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CheckoutRepository implements ICheckoutRepository {

    @Override
    public CheckoutBO save(CheckoutBO bo) {
        var modal = ModalCheckoutMapper.toModal(bo);
        try {
            modal.persistAndFlush();
        } catch (Exception e) {
            throw new AlticeException(EnumErrorCode.INTERNAL_ERROR, e);
        }
        return ModalCheckoutMapper.toBO(modal);
    }

    @Override
    public CheckoutBO findById(UUID id) {
        try {
            ModalCheckout modal = ModalCheckout.findById(id);

            if (modal == null) {
                return null;
            }

            return ModalCheckoutMapper.toBO(modal);

        } catch (Exception e) {
            throw new AlticeException(EnumErrorCode.INTERNAL_ERROR, e);
        }
    }

    @Override
    public CheckoutBO updated(CheckoutBO bo) {
        try {
            var existingModal = ModalCheckout.findById(bo.getId().getValue());

            if (existingModal == null) {
                throw new AlticeException(EnumErrorCode.OBJECT_NOT_FOUND, "ModalCheckout");
            }

            var updatedModal = ModalCheckoutMapper.toModal(bo);

            ModalCheckout.getEntityManager().merge(updatedModal);
            ModalCheckout.getEntityManager().flush();

            return ModalCheckoutMapper.toBO(updatedModal);
        } catch (AlticeException ae) {
            throw ae;
        } catch (Exception e) {
            throw new AlticeException(EnumErrorCode.INTERNAL_ERROR, e);
        }
    }

}