package com.altice.infra.data.panache.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.altice.domain.bo.ProductBO;
import com.altice.domain.enums.EnumErrorCode;
import com.altice.domain.repositories.IProductRepository;
import com.altice.domain.utils.exception.AlticeException;
import com.altice.infra.data.panache.mappers.ModalProductMapper;
import com.altice.infra.data.panache.modal.ModalProduct;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductRepository implements IProductRepository {

    @Override
    public ProductBO save(ProductBO bo) {
        var modal = ModalProductMapper.toModal(bo);
        try {
            modal.persistAndFlush();
        } catch (Exception e) {
            throw new AlticeException(EnumErrorCode.INTERNAL_ERROR, e);
        }
        return ModalProductMapper.toBO(modal);
    }

    @Override
    public List<ProductBO> findAll() {
        try {

            List<ModalProduct> list = ModalProduct.findAll().list();

            if (list == null || list.isEmpty()) {
                return new ArrayList<>();
            }

            return list.stream()
                    .map(value -> ModalProductMapper.toBO(value))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new AlticeException(EnumErrorCode.INTERNAL_ERROR, e);
        }
    }

    @Override
    public ProductBO findById(UUID id) {
        try {
            ModalProduct modal = ModalProduct.findById(id);

            if (modal == null) {
                return null;
            }

            return ModalProductMapper.toBO(modal);

        } catch (Exception e) {
            throw new AlticeException(EnumErrorCode.INTERNAL_ERROR, e);
        }
    }

    @Override
    public void remove(UUID id) {
        try {

            var modal = ModalProduct.findById(id);

            if (modal == null) {
                throw new AlticeException(EnumErrorCode.OBJECT_NOT_FOUND, "Product");
            }

            modal.delete();

            ModalProduct.flush();

        } catch (AlticeException ae) {
            throw ae;
        } catch (Exception e) {
            throw new AlticeException(EnumErrorCode.INTERNAL_ERROR, e);
        }
    }

    @Override
    public ProductBO updated(ProductBO bo) {
        try {
            var existingModal = ModalProduct.findById(bo.getId().getValue());

            if (existingModal == null) {
                throw new AlticeException(EnumErrorCode.OBJECT_NOT_FOUND, "Product");
            }

            var updatedModal = ModalProductMapper.toModal(bo);

            ModalProduct.getEntityManager().merge(updatedModal);
            ModalProduct.getEntityManager().flush();

            return ModalProductMapper.toBO(updatedModal);
        } catch (AlticeException ae) {
            throw ae;
        } catch (Exception e) {
            throw new AlticeException(EnumErrorCode.INTERNAL_ERROR, e);
        }
    }
}
