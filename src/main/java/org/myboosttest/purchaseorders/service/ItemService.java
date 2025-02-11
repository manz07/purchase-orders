package org.myboosttest.purchaseorders.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.myboosttest.purchaseorders.dto.request.ItemRequest;
import org.myboosttest.purchaseorders.dto.response.ItemResponse;
import org.myboosttest.purchaseorders.entity.Item;
import org.myboosttest.purchaseorders.exception.ItemNotFoundException;
import org.myboosttest.purchaseorders.repository.ItemRepository;
import org.myboosttest.purchaseorders.service.mapper.ItemMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public Integer createItem(ItemRequest itemRequest) {
        var item = itemMapper.toItem(itemRequest);
        return itemRepository.save(item).getId();
    }

    public List<ItemResponse> findAllItem() {
        return itemRepository.findAll()
                .stream()
                .map(itemMapper::fromItem)
                .collect(Collectors.toList());
    }

    public ItemResponse findById(Integer itemId) {
        return itemRepository.findById(itemId)
                .map(itemMapper::fromItem)
                .orElseThrow(() -> new ItemNotFoundException(format("No item found with the provided ID:: %s", itemId)));
    }

    public void updateItem(ItemRequest itemRequest) {
        var item = itemRepository.findById(itemRequest.id())
                .orElseThrow(() -> new ItemNotFoundException(format("Cannot update item:: No item found with provided ID:: %s", itemRequest.id())));
        mergeItem(item, itemRequest);
        itemRepository.save(item);
    }

    private void mergeItem(Item item, ItemRequest itemRequest) {
        if (StringUtils.isNotBlank(itemRequest.name())) {
            item.setName(itemRequest.name());
        }
        if (StringUtils.isNotBlank(itemRequest.description())) {
            item.setDescription(itemRequest.description());
        }
        if (StringUtils.isNotBlank(String.valueOf(itemRequest.price()))) {
            item.setPrice(itemRequest.price());
        }
        if (StringUtils.isNotBlank(String.valueOf(itemRequest.cost()))) {
            item.setCost(itemRequest.cost());
        }
    }

    public void deleteItem(Integer itemId) {
        itemRepository.deleteById(itemId);
    }
}
