package org.myboosttest.purchaseorders.dto;

import jakarta.validation.constraints.NotNull;

public record ItemResponse(
        Integer id,
        String name,
        String description,
        Integer price,
        Integer cost
) {
}
