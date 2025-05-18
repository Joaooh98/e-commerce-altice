package com.altice.infra.data.panache.repositories;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.altice.domain.bo.ShoppingCartBO;
import com.altice.domain.enums.EnumErrorCode;
import com.altice.domain.repositories.IShoppingCartRepository;
import com.altice.domain.utils.exception.AlticeException;
import com.altice.infra.data.panache.mappers.ModalShoppingCartMapper;
import com.altice.infra.data.panache.modal.ModalShoppingCart;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ShoppingCartRepository implements IShoppingCartRepository {

    @Override
    public ShoppingCartBO save(ShoppingCartBO bo) {
        var modal = ModalShoppingCartMapper.toModal(bo);
        try {
            modal.persistAndFlush();
        } catch (Exception e) {
            throw new AlticeException(EnumErrorCode.INTERNAL_ERROR, e);
        }
        return ModalShoppingCartMapper.toBO(modal);
    }

    @Override
    public ShoppingCartBO findById(UUID id) {
        try {
            ModalShoppingCart modal = ModalShoppingCart.findById(id);

            if (modal == null) {
                return null;
            }

            return ModalShoppingCartMapper.toBO(modal);

        } catch (Exception e) {
            throw new AlticeException(EnumErrorCode.INTERNAL_ERROR, e);
        }
    }

    @Override
    public void remove(UUID id) {
        try {

            var modal = ModalShoppingCart.findById(id);

            if (modal == null) {
                throw new AlticeException(EnumErrorCode.OBJECT_NOT_FOUND, "ModalShoppingCart");
            }

            modal.delete();

            ModalShoppingCart.flush();

        } catch (AlticeException ae) {
            throw ae;
        } catch (Exception e) {
            throw new AlticeException(EnumErrorCode.INTERNAL_ERROR, e);
        }
    }

    @Override
    public ShoppingCartBO updated(ShoppingCartBO bo) {
        try {
            ModalShoppingCart existingModal = ModalShoppingCart.findById(bo.getId().getValue());

            if (existingModal == null) {
                throw new AlticeException(EnumErrorCode.OBJECT_NOT_FOUND, "ModalShoppingCart");
            }

            var updatedModal = ModalShoppingCartMapper.toModal(bo);

            ModalShoppingCart.getEntityManager().merge(updatedModal);
            ModalShoppingCart.getEntityManager().flush();

            return ModalShoppingCartMapper.toBO(updatedModal);
        } catch (AlticeException ae) {
            throw ae;
        } catch (Exception e) {
            throw new AlticeException(EnumErrorCode.INTERNAL_ERROR, e);
        }
    }

    @Override
    public List<ShoppingCartBO> findAll() {
        try {
            List<ModalShoppingCart> modalCarts = ModalShoppingCart.findAll().list();

            return modalCarts.stream()
                    .map(ModalShoppingCartMapper::toBO)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new AlticeException(EnumErrorCode.INTERNAL_ERROR, e);
        }
    }

}
