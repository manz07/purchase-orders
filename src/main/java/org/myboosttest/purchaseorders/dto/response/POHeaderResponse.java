package org.myboosttest.purchaseorders.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record POHeaderResponse(
        Integer id,
        LocalDateTime dateTime,
        String description,
        Integer totalPrice,
        Integer totalCost,

        List<PODetailResponse> poDetails
) {
}
