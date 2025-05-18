package com.altice.domain.bo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.altice.domain.enums.EnumErrorCode;
import com.altice.domain.enums.EnumShoppingStatus;
import com.altice.domain.utils.exception.AlticeException;
import com.altice.domain.vo.UuidVO;

public class ShoppingCartBO {

    private final UuidVO id;
    private final UserBO user;
    private final List<ItemBO> items;
    private final EnumShoppingStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final BigDecimal amount;

    private ShoppingCartBO(Builder builder) {
        this.id = builder.id;
        this.user = builder.user;
        this.items = builder.items;
        this.status = builder.status;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.amount = builder.amount;
    }

    public UuidVO getId() {
        return id;
    }

    public UserBO getUser() {
        return user;
    }

    public List<ItemBO> getItems() {
        return items != null ? new ArrayList<>(items) : new ArrayList<>();
    }

    public EnumShoppingStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private UuidVO id;
        private UserBO user;
        private List<ItemBO> items;
        private EnumShoppingStatus status;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private BigDecimal amount;

        private Builder() {
        }

        public Builder id(UuidVO id) {
            this.id = id;
            return this;
        }

        public Builder user(UserBO user) {
            this.user = user;
            return this;
        }

        public Builder items(List<ItemBO> items) {
            this.items = items != null ? new ArrayList<>(items) : null;
            return this;
        }

        public Builder status(EnumShoppingStatus status) {
            this.status = status;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public ShoppingCartBO build() {
            return new ShoppingCartBO(this);
        }
    }

    public ShoppingCartBO recalculate(List<ItemBO> newItems){
        BigDecimal recalculatedAmount = calculateTotalAmount(newItems);
        EnumShoppingStatus newStatus = determineCartStatus(newItems);

        return createUpdatedCart(newItems, recalculatedAmount, newStatus, LocalDateTime.now());

    }
    
    public ShoppingCartBO addItemCart(List<ItemBO> newItems) {
        List<ItemBO> processedItems = processAddItems(newItems);
        BigDecimal recalculatedAmount = calculateTotalAmount(processedItems);
        EnumShoppingStatus newStatus = determineCartStatus(processedItems);

        return createUpdatedCart(processedItems, recalculatedAmount, newStatus, LocalDateTime.now());
    }

    public ShoppingCartBO removeItemCart(List<ItemBO> newItems) {
        List<ItemBO> processedItems = processRemoveItems(newItems);
        BigDecimal recalculatedAmount = calculateTotalAmount(processedItems);
        EnumShoppingStatus newStatus = determineCartStatus(processedItems);

        return createUpdatedCart(processedItems, recalculatedAmount, newStatus, LocalDateTime.now());
    }

    private List<ItemBO> processRemoveItems(List<ItemBO> itemsToRemove) {
        if (isCurrentCartEmpty()) {
            return new ArrayList<>();
        }

        if (itemsToRemove == null || itemsToRemove.isEmpty()) {
            return new ArrayList<>(this.items);
        }

        Set<UUID> idsToRemove = itemsToRemove.stream()
                .map(this::extractProductId)
                .collect(Collectors.toSet());

        return this.items.stream()
                .filter(item -> !idsToRemove.contains(extractProductId(item)))
                .collect(Collectors.toList());
    }

    private List<ItemBO> processAddItems(List<ItemBO> newItems) {
        if (isCurrentCartEmpty()) {
            return new ArrayList<>(newItems);
        }

        List<ItemBO> duplicates = findDuplicateItems(newItems);
        if (!duplicates.isEmpty()) {
            String duplicateNames = duplicates.stream()
                    .map(item -> item.getProduct().getDescription())
                    .collect(Collectors.joining(", "));

            throw new AlticeException(EnumErrorCode.ITEM_ALREADY_EXISTS, duplicateNames);
        }

        List<ItemBO> updatedItems = new ArrayList<>(this.items);
        updatedItems.addAll(newItems);

        return updatedItems;
    }

    private List<ItemBO> findDuplicateItems(List<ItemBO> newItems) {
        if (isEitherListEmpty(newItems)) {
            return new ArrayList<>();
        }

        Set<UUID> currentIds = getCurrentProductIds();

        return newItems.stream()
                .filter(item -> currentIds.contains(extractProductId(item)))
                .collect(Collectors.toList());
    }

    private boolean isEitherListEmpty(List<ItemBO> newItems) {
        return (this.items == null || this.items.isEmpty()) ||
                (newItems == null || newItems.isEmpty());
    }

    private Set<UUID> getCurrentProductIds() {
        if (this.items == null || this.items.isEmpty()) {
            return new HashSet<>();
        }

        return this.items.stream()
                .map(this::extractProductId)
                .collect(Collectors.toSet());
    }

    private UUID extractProductId(ItemBO item) {
        return item.getProduct().getId().getValue();
    }

    private boolean isCurrentCartEmpty() {
        return this.items == null || this.items.isEmpty();
    }

    private BigDecimal calculateTotalAmount(List<ItemBO> items) {
        if (items == null || items.isEmpty()) {
            return BigDecimal.ZERO;
        }

        return items.stream()
                .map(this::calculateItemSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateItemSubtotal(ItemBO item) {
        BigDecimal price = item.getProduct().getPrice();
        BigDecimal quantity = BigDecimal.valueOf(item.getQuantity());
        return price.multiply(quantity);
    }

    private EnumShoppingStatus determineCartStatus(List<ItemBO> items) {
        if (items == null || items.isEmpty()) {
            return EnumShoppingStatus.EMPTY;
        }
        return EnumShoppingStatus.ACTIVE;
    }

    private ShoppingCartBO createUpdatedCart(List<ItemBO> items, BigDecimal amount,
            EnumShoppingStatus status, LocalDateTime updateTime) {

        return ShoppingCartBO.builder()
                .id(this.id)
                .user(this.user)
                .items(items)
                .status(status)
                .createdAt(this.createdAt)
                .updatedAt(updateTime)
                .amount(amount)
                .build();
    }
}