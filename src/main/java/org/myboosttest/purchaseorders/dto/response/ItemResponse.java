package org.myboosttest.purchaseorders.dto.response;

import jakarta.validation.constraints.NotNull;

public record ItemResponse(
        Integer id,
        String name,
        String description,
        Integer price,
        Integer cost
) {
}
