package org.myboosttest.purchaseorders.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.myboosttest.purchaseorders.dto.request.PODetailRequest;
import org.myboosttest.purchaseorders.dto.request.POHeaderRequest;
import org.myboosttest.purchaseorders.dto.response.POHeaderResponse;
import org.myboosttest.purchaseorders.entity.Item;
import org.myboosttest.purchaseorders.entity.PODetail;
import org.myboosttest.purchaseorders.entity.POHeader;
import org.myboosttest.purchaseorders.exception.ItemNotFoundException;
import org.myboosttest.purchaseorders.exception.PONotFoundException;
import org.myboosttest.purchaseorders.repository.ItemRepository;
import org.myboosttest.purchaseorders.repository.PODetailRepository;
import org.myboosttest.purchaseorders.repository.POHeaderRepository;
import org.myboosttest.purchaseorders.service.mapper.PODetailMapper;
import org.myboosttest.purchaseorders.service.mapper.POHeaderMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

@ExtendWith(SpringExtension.class)
public class POHeaderServiceTest {

    @Mock
    private POHeaderRepository poHeaderRepository;

    @Mock
    private PODetailRepository poDetailRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private POHeaderMapper poHeaderMapper;

    @Mock
    private PODetailMapper poDetailMapper;

    @InjectMocks
    private POHeaderService poHeaderService;

    private POHeader poHeader;
    private POHeaderRequest poHeaderRequest;
    private POHeaderResponse poHeaderResponse;
    private PODetail podetail;
    private PODetailRequest podetailRequest;

    @BeforeEach
    void setUp() {
        // Initialize mock objects
        poHeader = new POHeader(1, null, "Test Desc", 900, 50000, "creator", "updater", null, null, null);
        poHeaderRequest = new POHeaderRequest(1, null, "Test 2", 23000, 50000, "creator", "updater", List.of(podetailRequest));
        poHeaderResponse = new POHeaderResponse(1, null, "Test 2", 23000, 50000, null);

        Item item = new Item(1, "Test Item", "Description", 500, 4500, "creator", "updater", null, null);
        podetail = new PODetail();
        podetailRequest = new PODetailRequest(1, 1, 10, 50, 60);

        // Mock mapper behavior
        when(poHeaderMapper.toPOHeader(poHeaderRequest)).thenReturn(poHeader);
        when(poHeaderMapper.fromPOHeader(poHeader)).thenReturn(poHeaderResponse);
        when(poDetailMapper.toPODetail(podetailRequest, poHeader)).thenReturn(podetail);

        // Mock repository behavior
        when(poHeaderRepository.save(poHeader)).thenReturn(poHeader);
        when(poHeaderRepository.findById(1)).thenReturn(Optional.of(poHeader));
        when(poHeaderRepository.findAll()).thenReturn(List.of(poHeader));
        when(itemRepository.findById(1)).thenReturn(Optional.of(item));
        when(poDetailRepository.findById(1)).thenReturn(Optional.of(podetail));
    }

    @Test
    void testCreatePO() {
        // Act: Call service method
        poHeaderService.createPO(poHeaderRequest);

        // Assert: Verify that save method was called
        verify(poHeaderRepository, times(1)).save(poHeader);
    }

    @Test
    void testCreatePOItemNotFoundException() {
        // Arrange: Mock itemRepository to return empty
        when(itemRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert: Expect exception to be thrown
        assertThrows(ItemNotFoundException.class, () -> poHeaderService.createPO(poHeaderRequest));
    }

    @Test
    void testFindAll() {
        // Act: Call service method
        List<POHeaderResponse> responseList = poHeaderService.findAll();

        // Assert: Verify behavior and results
        verify(poHeaderRepository, times(1)).findAll();
        assertNotNull(responseList, "Response list should not be null");
        assertEquals(1, responseList.size(), "Response list size should be 1");
    }

    @Test
    void testFindById() {
        // Act: Call service method
        POHeaderResponse response = poHeaderService.findById(1);

        // Assert: Verify behavior and results
        verify(poHeaderRepository, times(1)).findById(1);
        assertNotNull(response, "Response should not be null");
        assertEquals(poHeaderResponse, response, "Response should match the expected POHeaderResponse");
    }

    @Test
    void testFindByIdThrowsPONotFoundException() {
        // Arrange: Mock repository behavior for non-existing POHeader
        when(poHeaderRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert: Expect exception to be thrown
        assertThrows(PONotFoundException.class, () -> poHeaderService.findById(1), "Should throw PONotFoundException if POHeader does not exist");
    }

    @Test
    void testUpdatePO() {
        // Arrange: Mock repository behavior for POHeader
        when(poHeaderRepository.findById(1)).thenReturn(Optional.of(poHeader));
        when(poHeaderRepository.save(poHeader)).thenReturn(poHeader);

        // Act: Call service method to update POHeader
        poHeaderService.updatePO(poHeaderRequest);

        // Assert: Verify behavior
        verify(poHeaderRepository, times(1)).findById(1);
        verify(poHeaderRepository, times(1)).save(poHeader);
    }

    @Test
    void testUpdatePOThrowsPONotFoundException() {
        // Arrange: Mock repository behavior for non-existing POHeader
        when(poHeaderRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert: Expect exception to be thrown
        assertThrows(PONotFoundException.class, () -> poHeaderService.updatePO(poHeaderRequest), "Should throw PONotFoundException if POHeader does not exist");
    }

    @Test
    void testDeletePOHeader() {
        // Act: Call service method to delete POHeader
        poHeaderService.deletePOHeader(1);

        // Assert: Verify behavior
        verify(poHeaderRepository, times(1)).delete(poHeader);
    }

    @Test
    void testDeletePODetail() {
        // Act: Call service method to delete PODetail
        poHeaderService.deletePODetail(1);

        // Assert: Verify behavior
        verify(poDetailRepository, times(1)).delete(podetail);
    }

    @Test
    void testDeletePODetailThrowsPONotFoundException() {
        // Arrange: Mock repository behavior for non-existing PODetail
        when(poDetailRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert: Expect exception to be thrown
        assertThrows(PONotFoundException.class, () -> poHeaderService.deletePODetail(1), "Should throw PONotFoundException if PODetail does not exist");
    }
}
