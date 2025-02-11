package org.myboosttest.purchaseorders.service.mapper;

import lombok.RequiredArgsConstructor;
import org.myboosttest.purchaseorders.dto.request.PODetailRequest;
import org.myboosttest.purchaseorders.dto.response.PODetailResponse;
import org.myboosttest.purchaseorders.entity.Item;
import org.myboosttest.purchaseorders.entity.PODetail;
import org.myboosttest.purchaseorders.entity.POHeader;
import org.myboosttest.purchaseorders.repository.ItemRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PODetailMapper {

    private final ItemRepository itemRepository;

    public PODetail toPODetail(PODetailRequest podetailRequest, POHeader poHeader) {
        Item item = itemRepository.findById(podetailRequest.itemId())
                .orElseThrow(() -> new IllegalArgumentException("Item with ID " + podetailRequest.itemId() + " not found"));
        return PODetail.builder()
                .id(podetailRequest.id())
                .item(item)
                .itemQty(podetailRequest.itemQty())
                .itemCost(podetailRequest.itemCost())
                .itemPrice(podetailRequest.itemPrice())
                .poHeader(poHeader)
                .build();
    }

    public static PODetailResponse fromPODetail(PODetail poDetail) {
        return new PODetailResponse(
                poDetail.getId(),
                poDetail.getItemQty(),
                poDetail.getItemCost(),
                poDetail.getItemPrice()
        );
    }
}
