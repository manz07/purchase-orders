package org.myboosttest.purchaseorders.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.myboosttest.purchaseorders.apiresponse.ApiResponse;
import org.myboosttest.purchaseorders.dto.request.ItemRequest;
import org.myboosttest.purchaseorders.dto.response.ItemResponse;
import org.myboosttest.purchaseorders.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<ApiResponse> createItem (
            @RequestBody @Valid ItemRequest itemRequest
    ) {
        Integer itemId = itemService.createItem(itemRequest);
        ApiResponse apiResponse = new ApiResponse("success", "Item created successfully with ID: " + itemId);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ItemResponse>> findAll() {
        return ResponseEntity.ok(itemService.findAllItem());
    }

    @GetMapping("/{item-id}")
    public ResponseEntity<ItemResponse> findById(
            @PathVariable("item-id") Integer itemId
    ) {
        return ResponseEntity.ok(itemService.findById(itemId));
    }

    @PutMapping
    public ResponseEntity<ApiResponse> updateItem(
            @RequestBody @Valid ItemRequest itemRequest
    ) {
        itemService.updateItem(itemRequest);
        ApiResponse apiResponse = new ApiResponse("success", "Item data successfully updated");
        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{item-id}")
    public ResponseEntity<String> deleteItem(
            @PathVariable("item-id") Integer itemId
    ) {
        itemService.deleteItem(itemId);
        return ResponseEntity.ok("Item with ID:: " + itemId + " has been deleted");
    }
}
