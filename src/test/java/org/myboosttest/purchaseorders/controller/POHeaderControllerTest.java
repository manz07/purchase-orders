package org.myboosttest.purchaseorders.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.myboosttest.purchaseorders.dto.request.POHeaderRequest;
import org.myboosttest.purchaseorders.dto.response.POHeaderResponse;
import org.myboosttest.purchaseorders.dto.request.PODetailRequest;
import org.myboosttest.purchaseorders.dto.response.PODetailResponse;
import org.myboosttest.purchaseorders.service.POHeaderService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class POHeaderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private POHeaderService poHeaderService;

    @InjectMocks
    private POHeaderController poHeaderController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(poHeaderController).build();
    }

    // Test for creating a PO Header with PODetails
    @Test
    void testCreatePO() throws Exception {
        PODetailRequest podetailRequest = new PODetailRequest(1, 101, 10, 200, 300);
        List<PODetailRequest> poDetails = Arrays.asList(podetailRequest);

        POHeaderRequest poHeaderRequest = POHeaderRequest.builder()
                .id(1)
                .dateTime(LocalDateTime.now())
                .description("PO for Item1")
                .totalPrice(3000)
                .totalCost(1500)
                .createdBy("Admin")
                .updatedBy("Admin")
                .poDetails(poDetails)
                .build();

        doNothing().when(poHeaderService).createPO(any(POHeaderRequest.class));

        mockMvc.perform(post("/api/v1/po")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(poHeaderRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Purchase order created successfully"));

        verify(poHeaderService, times(1)).createPO(any(POHeaderRequest.class));
    }

    // Test for finding all PO Headers
    @Test
    void testFindAllPOHeaders() throws Exception {
        PODetailResponse podetailResponse = new PODetailResponse(101, 10, 200, 300);
        POHeaderResponse poHeaderResponse1 = new POHeaderResponse(1, LocalDateTime.now(), "PO001", 1000, 500, Arrays.asList(podetailResponse));
        POHeaderResponse poHeaderResponse2 = new POHeaderResponse(2, LocalDateTime.now(), "PO002", 2000, 1200, Arrays.asList(podetailResponse));
        List<POHeaderResponse> poHeaderResponses = Arrays.asList(poHeaderResponse1, poHeaderResponse2);

        when(poHeaderService.findAll()).thenReturn(poHeaderResponses);

        mockMvc.perform(get("/api/v1/po")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].description").value("PO001"))
                .andExpect(jsonPath("$[0].poDetails[0].itemId").value(101))
                .andExpect(jsonPath("$[0].poDetails[0].itemQty").value(10))
                .andExpect(jsonPath("$[0].poDetails[0].itemCost").value(200))
                .andExpect(jsonPath("$[0].poDetails[0].itemPrice").value(300))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].description").value("PO002"));

        verify(poHeaderService, times(1)).findAll();
    }

    // Test for finding a PO Header by ID
    @Test
    void testFindPOHeaderById() throws Exception {
        PODetailResponse podetailResponse = new PODetailResponse(101, 10, 200, 300);
        POHeaderResponse poHeaderResponse = new POHeaderResponse(1, LocalDateTime.now(), "PO001", 1000, 500, Arrays.asList(podetailResponse));
        when(poHeaderService.findById(1)).thenReturn(poHeaderResponse);

        mockMvc.perform(get("/api/v1/po/{poh-id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.description").value("PO001"))
                .andExpect(jsonPath("$.poDetails[0].itemId").value(101))
                .andExpect(jsonPath("$.poDetails[0].itemQty").value(10))
                .andExpect(jsonPath("$.poDetails[0].itemCost").value(200))
                .andExpect(jsonPath("$.poDetails[0].itemPrice").value(300));

        verify(poHeaderService, times(1)).findById(1);
    }

    // Test for updating a PO Header
    @Test
    void testUpdatePO() throws Exception {
        PODetailRequest podetailRequest = new PODetailRequest(1, 101, 10, 200, 300);
        List<PODetailRequest> poDetails = Arrays.asList(podetailRequest);

        POHeaderRequest poHeaderRequest = POHeaderRequest.builder()
                .id(1)
                .dateTime(LocalDateTime.now())
                .description("PO for Item1")
                .totalPrice(1200)
                .totalCost(600)
                .createdBy("Admin")
                .updatedBy("Admin")
                .poDetails(poDetails)
                .build();

        doNothing().when(poHeaderService).updatePO(any(POHeaderRequest.class));

        mockMvc.perform(put("/api/v1/po")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(poHeaderRequest)))
                .andExpect(status().isAccepted());

        verify(poHeaderService, times(1)).updatePO(any(POHeaderRequest.class));
    }

    // Test for deleting a PO Header
    @Test
    void testDeletePOHeader() throws Exception {
        doNothing().when(poHeaderService).deletePOHeader(1);

        mockMvc.perform(delete("/api/v1/po/{poh-id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(poHeaderService, times(1)).deletePOHeader(1);
    }

    // Test for deleting PO Detail
    @Test
    void testDeletePODetail() throws Exception {
        doNothing().when(poHeaderService).deletePODetail(1);

        mockMvc.perform(delete("/api/v1/po/detail/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(poHeaderService, times(1)).deletePODetail(1);
    }
}
