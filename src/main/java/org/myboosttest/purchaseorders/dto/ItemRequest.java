package org.myboosttest.purchaseorders.dto;


import jakarta.validation.constraints.NotNull;

public record ItemRequest(
        Integer id,
        @NotNull(message = "Name is required")
        String name,
        @NotNull(message = "Description is required")
        String description,
        @NotNull(message = "Price is required")
        Integer price,
        @NotNull(message = "Cost is required")
        Integer cost,
        String createdBy,
        String updatedBy
) {
}
