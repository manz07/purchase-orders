package org.myboosttest.purchaseorders.dto.response;

import lombok.Builder;

@Builder
public record PODetailResponse(
        Integer itemId,
        Integer itemQty,
        Integer itemCost,
        Integer itemPrice

) {
}
