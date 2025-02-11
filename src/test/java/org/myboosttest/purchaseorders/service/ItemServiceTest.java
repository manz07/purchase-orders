package org.myboosttest.purchaseorders.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.myboosttest.purchaseorders.dto.request.ItemRequest;
import org.myboosttest.purchaseorders.dto.response.ItemResponse;
import org.myboosttest.purchaseorders.entity.Item;
import org.myboosttest.purchaseorders.exception.ItemNotFoundException;
import org.myboosttest.purchaseorders.repository.ItemRepository;
import org.myboosttest.purchaseorders.service.mapper.ItemMapper;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemMapper itemMapper;

    @InjectMocks
    private ItemService itemService;

    private ItemRequest itemRequest;
    private Item item;
    private ItemResponse itemResponse;

    @BeforeEach
    void setUp() {
        // Set up data for the tests
        itemRequest = new ItemRequest(1, "Item Name", "Item Description", 100, 50, "Admin", "Admin");
        item = new Item(1, "Item Name", "Item Description", 100, 50, "admin", "admin", null,null);
        itemResponse = new ItemResponse(1, "Item Name", "Item Description", 100, 50);
    }

    @Test
    void testCreateItem() {
        // Mock behavior
        when(itemMapper.toItem(itemRequest)).thenReturn(item);
        when(itemRepository.save(item)).thenReturn(item);

        // Call service method to create item
        Integer itemId = itemService.createItem(itemRequest);

        // Verify behavior
        verify(itemRepository, times(1)).save(item);
        assertNotNull(itemId, "Item ID should not be null");
        assertEquals(1, itemId, "Item ID should be 1");
    }

    @Test
    void testFindAllItem() {
        // Mock behavior
        when(itemRepository.findAll()).thenReturn(List.of(item));
        when(itemMapper.fromItem(item)).thenReturn(itemResponse);

        // Call service method to find all items
        var items = itemService.findAllItem();

        // Verify behavior
        verify(itemRepository, times(1)).findAll();  // Ensure findAll was called once
        assertEquals(1, items.size(), "There should be one item");
        assertEquals(itemResponse.name(), items.getFirst().name(), "Item name should match");
    }

    @Test
    void testFindById() {
        // Mock behavior
        when(itemRepository.findById(1)).thenReturn(Optional.of(item));
        when(itemMapper.fromItem(item)).thenReturn(itemResponse);

        // Call service method to find item by ID
        ItemResponse foundItem = itemService.findById(1);

        // Verify behavior
        verify(itemRepository, times(1)).findById(1);  // Ensure findById was called once
        assertNotNull(foundItem, "Item should not be null");
        assertEquals(itemResponse.name(), foundItem.name(), "Item name should match");
    }

    @Test
    void testFindByIdItemNotFound() {
        // Mock behavior: No item found
        when(itemRepository.findById(1)).thenReturn(Optional.empty());

        // Call service method and assert exception
        assertThrows(ItemNotFoundException.class, () -> itemService.findById(1));
    }

    @Test
    void testUpdateItem() {
        // Mock behavior: Item is found
        when(itemRepository.findById(1)).thenReturn(Optional.of(item));

        // Call service method to update item
        itemService.updateItem(itemRequest);

        // Verify behavior
        verify(itemRepository, times(1)).save(item);
        assertEquals("Item Name", item.getName(), "Item name should be updated");
    }

    @Test
    void testUpdateItemNotFound() {
        // Mock behavior: Item not found
        when(itemRepository.findById(1)).thenReturn(Optional.empty());

        // Call service method and assert exception
        assertThrows(ItemNotFoundException.class, () -> itemService.updateItem(itemRequest));
    }

    @Test
    void testDeleteItem() {
        // Mock behavior: Item is found
        when(itemRepository.findById(1)).thenReturn(Optional.of(item));

        // Call service method to delete item
        itemService.deleteItem(1);

        // Verify behavior
        verify(itemRepository, times(1)).deleteById(1);
    }
}
