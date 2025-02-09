package org.myboosttest.purchaseorders.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record POHeaderRequest(
        Integer id,
        LocalDateTime dateTime,
        String description,
        Integer totalPrice,
        Integer totalCost,
        String createdBy,
        String updatedBy,

        List<PODetailRequest> poDetails
) {
}
