package org.myboosttest.purchaseorders.service.mapper;

import org.myboosttest.purchaseorders.dto.request.ItemRequest;
import org.myboosttest.purchaseorders.dto.response.ItemResponse;
import org.myboosttest.purchaseorders.entity.Item;
import org.springframework.stereotype.Service;

@Service
public class ItemMapper {

    public Item toItem(ItemRequest itemRequest) {
        if (itemRequest == null) {
            return null;
        }
        return Item.builder()
                .id(itemRequest.id())
                .name(itemRequest.name())
                .description(itemRequest.description())
                .price(itemRequest.price())
                .cost(itemRequest.cost())
                .createdBy(itemRequest.createdBy())
                .updatedBy(itemRequest.updatedBy())
                .build();
    }

    public ItemResponse fromItem(Item item) {
        return new ItemResponse(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getPrice(),
                item.getCost()
        );
    }
}
