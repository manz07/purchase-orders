package org.myboosttest.purchaseorders.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.myboosttest.purchaseorders.apiresponse.ApiResponse;
import org.myboosttest.purchaseorders.dto.POHeaderRequest;
import org.myboosttest.purchaseorders.dto.POHeaderResponse;
import org.myboosttest.purchaseorders.service.POHeaderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/po")
@RequiredArgsConstructor
public class POHeaderController {

    private final POHeaderService poHeaderService;

    @PostMapping
    public ResponseEntity<ApiResponse> createPO(
            @RequestBody @Valid POHeaderRequest poHeaderRequest
    ) {
        poHeaderService.createPO(poHeaderRequest);
        ApiResponse apiResponse = new ApiResponse("success", "Purchase order created successfully");
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<POHeaderResponse>> findAll() {
        return ResponseEntity.ok(poHeaderService.findAll());
    }
}
