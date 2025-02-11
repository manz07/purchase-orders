package org.myboosttest.purchaseorders.dto.request;

import lombok.Builder;
import org.myboosttest.purchaseorders.dto.request.PODetailRequest;

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
