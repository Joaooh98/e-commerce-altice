package com.altice.domain.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.altice.domain.bo.ItemBO;
import com.altice.domain.bo.ShoppingCartBO;
import com.altice.domain.dto.ItemDTO;
import com.altice.domain.dto.ShoppingCartDTO;
import com.altice.domain.enums.EnumShoppingStatus;
import com.altice.domain.utils.StringUtils;
import com.altice.domain.vo.UuidVO;

public class ShoppingCartDomainMapper {

    public static ShoppingCartBO toBO(ShoppingCartDTO dto) {
        if (dto == null) {
            return null;
        }
        return ShoppingCartBO.builder()
                .id(new UuidVO(dto.getId()))
                .user(dto.getUser() != null ? UserDomainMapper.toBO(dto.getUser()) : null)
                .items(toItemsBO(dto.getItems()))
                .status(StringUtils.isNotNullOrEmpty(dto.getStatus()) ? EnumShoppingStatus.parseByKey(dto.getStatus())
                        : EnumShoppingStatus.ACTIVE)
                .amount(dto.getAmount())
                .build();
    }

    public static ShoppingCartDTO toDTO(ShoppingCartBO bo) {

        if (bo == null) {
            return null;
        }
        
        var dto = new ShoppingCartDTO();

        dto.setId(bo.getId().getValue().toString());
        dto.setAmount(bo.getAmount());
        dto.setStatus(bo.getStatus().getValue());
        dto.setUser(bo.getUser() != null ? UserDomainMapper.toDTO(bo.getUser()) : null);
        dto.setCreatedAt(bo.getCreatedAt() != null ? bo.getCreatedAt().toString() : null);
        dto.setUpdatedAt(bo.getUpdatedAt() != null ? bo.getUpdatedAt().toString() : null);
        dto.setItems(toItemsDTO(bo.getItems()));

        return dto;
    }

    private static List<ItemBO> toItemsBO(List<ItemDTO> items) {
        return items == null ? new ArrayList<>()
                : items.stream()
                        .map(ItemsDomainMapper::toBO)
                        .collect(Collectors.toList());
    }

    private static List<ItemDTO> toItemsDTO(List<ItemBO> items) {
        return items == null ? new ArrayList<>()
                : items.stream()
                        .map(ItemsDomainMapper::toDTO)
                        .collect(Collectors.toList());
    }

}
