package org.myboosttest.purchaseorders.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.myboosttest.purchaseorders.apiresponse.ApiResponse;
import org.myboosttest.purchaseorders.dto.request.POHeaderRequest;
import org.myboosttest.purchaseorders.dto.response.POHeaderResponse;
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
        ApiResponse apiResponse = new ApiResponse("success", "Purchase order successfully created");
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<POHeaderResponse>> findAll() {
        return ResponseEntity.ok(poHeaderService.findAll());
    }

    @GetMapping("/{poh-id}")
    public ResponseEntity<POHeaderResponse> findById(
            @PathVariable("poh-id") Integer pohId
    ) {
        return ResponseEntity.ok(poHeaderService.findById(pohId));
    }

    @PutMapping
    public ResponseEntity<ApiResponse> updatePO(
            @RequestBody @Valid POHeaderRequest poHeaderRequest
    ) {
        poHeaderService.updatePO(poHeaderRequest);
        ApiResponse apiResponse = new ApiResponse("success", "Purchase order successfully updated");
        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{poh-id}")
    public ResponseEntity<String> deletePOHeader(
            @PathVariable("poh-id") Integer pohId
    ) {
        poHeaderService.deletePOHeader(pohId);
        return ResponseEntity.ok().body("Purchase order header with ID: " + pohId + " successfully deleted");

    }

    @DeleteMapping("/detail/{id}")
    public ResponseEntity<String> deletePODetail(
            @PathVariable("id") Integer detailId
    ) {
        poHeaderService.deletePODetail(detailId);
        return ResponseEntity.ok().body("Purchase order detail with ID: " + detailId + " successfully deleted");
    }
}
