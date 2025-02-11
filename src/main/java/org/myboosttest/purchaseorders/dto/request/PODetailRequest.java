package org.myboosttest.purchaseorders.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.myboosttest.purchaseorders.entity.Item;

@Builder
public record PODetailRequest(
        Integer id,
        @NotNull(message = "itemId is required")
        @NotEmpty(message = "itemId is not empty")
        @NotBlank(message = "itemId is not blank")
        Integer itemId,
        @NotNull(message = "itemQty is required")
        Integer itemQty,
        @NotNull(message = "itemCost is required")
        Integer itemCost,
        @NotNull(message = "itemPrice is required")
        Integer itemPrice
) {
}
