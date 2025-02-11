package org.myboosttest.purchaseorders.service.mapper;

import org.myboosttest.purchaseorders.dto.response.PODetailResponse;
import org.myboosttest.purchaseorders.dto.request.POHeaderRequest;
import org.myboosttest.purchaseorders.dto.response.POHeaderResponse;
import org.myboosttest.purchaseorders.entity.POHeader;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class POHeaderMapper {

    public POHeader toPOHeader(POHeaderRequest poHeaderRequest) {
        return POHeader.builder()
                .id(poHeaderRequest.id())
                .description(poHeaderRequest.description())
                .totalPrice(poHeaderRequest.totalPrice())
                .totalCost(poHeaderRequest.totalCost())
                .createdBy(poHeaderRequest.createdBy())
                .updatedBy(poHeaderRequest.updatedBy())
                .build();
    }


    public POHeaderResponse fromPOHeader(POHeader poHeader) {
        List<PODetailResponse> poDetailResponses = poHeader.getPoDetails().stream()
                .map(PODetailMapper::fromPODetail)
                .toList();

        return new POHeaderResponse(
                poHeader.getId(),
                poHeader.getDateTime(),
                poHeader.getDescription(),
                poHeader.getTotalPrice(),
                poHeader.getTotalCost(),
                poDetailResponses
        );
    }
}
