package com.altice.infra.data.panache.mappers;

import com.altice.domain.bo.CheckoutBO;
import com.altice.domain.enums.EnumCheckoutStatus;
import com.altice.domain.enums.EnumErrorCode;
import com.altice.domain.utils.exception.AlticeException;
import com.altice.domain.vo.UuidVO;
import com.altice.infra.data.panache.modal.ModalCheckout;

public abstract class ModalCheckoutMapper {

    public static ModalCheckout toModal(CheckoutBO bo) {
        if (bo == null) {
            throw new AlticeException(EnumErrorCode.REQUIRED_OBJECT, "checkoutBO");
        }

        ModalCheckout modal = new ModalCheckout();

        if (bo.getId() != null) {
            modal.setId(bo.getId().getValue());
        }

        if (bo.getShoppingCartId() != null) {
            modal.setShoppingCartId(bo.getShoppingCartId().getValue());
        }

        if (bo.getUserId() != null) {
            modal.setUserId(bo.getUserId().getValue());
        }

        modal.setAmount(bo.getAmount());
        modal.setStatus(bo.getStatus() != null ? bo.getStatus().getKey() : null);
        modal.setPaymentUrl(bo.getPaymentUrl());
        modal.setCreatedAt(bo.getCreatedAt());
        modal.setUpdatedAt(bo.getUpdatedAt());

        return modal;
    }

    public static CheckoutBO toBO(ModalCheckout modal) {
        if (modal == null) {
            throw new AlticeException(EnumErrorCode.REQUIRED_OBJECT, "modalCheckout");
        }

        CheckoutBO.Builder builder = CheckoutBO.builder();

        if (modal.getId() != null) {
            builder.id(new UuidVO(modal.getId().toString()));
        }

        if (modal.getShoppingCartId() != null) {
            builder.shoppingCartId(new UuidVO(modal.getShoppingCartId().toString()));
        }

        if (modal.getUserId() != null) {
            builder.userId(new UuidVO(modal.getUserId().toString()));
        }

        builder.amount(modal.getAmount());
        builder.status(modal.getStatus() != null ? EnumCheckoutStatus.parseByKey(modal.getStatus()) : null);
        builder.paymentUrl(modal.getPaymentUrl());
        builder.createdAt(modal.getCreatedAt());
        builder.updatedAt(modal.getUpdatedAt());

        return builder.build();
    }
}