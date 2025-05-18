package com.altice.domain.mappers;

import com.altice.domain.bo.CheckoutBO;
import com.altice.domain.dto.CheckoutDTO;
import com.altice.domain.enums.EnumCheckoutStatus;
import com.altice.domain.enums.EnumErrorCode;
import com.altice.domain.utils.exception.AlticeException;
import com.altice.domain.vo.UuidVO;

public class CheckoutDomainMapper {

    public static CheckoutBO toBO(CheckoutDTO dto) {
        if (dto == null) {
            throw new AlticeException(EnumErrorCode.REQUIRED_OBJECT, "checkoutDTO");
        }

        return CheckoutBO.builder()
                .id(new UuidVO(dto.getId()))
                .shoppingCartId(new UuidVO(dto.getShoppingCartId()))
                .userId(new UuidVO(dto.getUserId()))
                .amount(dto.getAmount())
                .status(EnumCheckoutStatus.parseByKey(dto.getStatus()))
                .paymentUrl(dto.getPaymentUrl())
                .build();
    }

    public static CheckoutDTO toDTO(CheckoutBO bo) {
        if (bo == null) {
            throw new AlticeException(EnumErrorCode.REQUIRED_OBJECT, "checkoutBO");
        }

        CheckoutDTO dto = new CheckoutDTO();
        dto.setId(bo.getId() != null ? bo.getId().getValue().toString() : null);
        dto.setShoppingCartId(bo.getShoppingCartId() != null ? bo.getShoppingCartId().getValue().toString() : null);
        dto.setUserId(bo.getUserId() != null ? bo.getUserId().getValue().toString() : null);
        dto.setAmount(bo.getAmount());
        dto.setStatus(bo.getStatus() != null ? bo.getStatus().getValue() : null);
        dto.setPaymentUrl(bo.getPaymentUrl());
        dto.setCreatedAt(bo.getCreatedAt() != null ? bo.getCreatedAt().toString() : null);
        dto.setUpdatedAt(bo.getUpdatedAt() != null ? bo.getUpdatedAt().toString() : null);

        return dto;
    }
}