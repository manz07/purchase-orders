package org.myboosttest.purchaseorders.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;


@Service
@RequiredArgsConstructor
public class POHeaderService {

    private final POHeaderRepository poHeaderRepository;
    private final PODetailRepository poDetailRepository;
    private final POHeaderMapper poHeaderMapper;
    private final PODetailMapper poDetailMapper;
    private final ItemRepository itemRepository;


    @Transactional
    public void createPO(POHeaderRequest poHeaderRequest) {
        POHeader poHeader = poHeaderMapper.toPOHeader(poHeaderRequest);

        List<PODetail> details = poHeaderRequest.poDetails()
                .stream()
                .map(poDetailRequest -> {
                    PODetail poDetail = poDetailMapper.toPODetail(poDetailRequest, poHeader);

                    Integer itemId = poDetailRequest.itemId();

                    Optional<Item> itemOpt = itemRepository.findById(itemId);

                    if (itemOpt.isPresent()) {
                        poDetail.setItem(itemOpt.get());
                    } else {
                        throw new ItemNotFoundException("Item with ID " + itemId + " not found.");
                    }

                    return poDetail;
                })
                .toList();

        poHeader.setPoDetails(details);

        poHeaderRepository.save(poHeader);

    }

    public List<POHeaderResponse> findAll() {
        return poHeaderRepository.findAll()
                .stream()
                .map(poHeaderMapper::fromPOHeader)
                .collect(Collectors.toList());
    }

    public POHeaderResponse findById(Integer pohId) {
        return poHeaderRepository.findById(pohId)
                .map(poHeaderMapper::fromPOHeader)
                .orElseThrow(() -> new PONotFoundException(format("No purchase order found with the provided ID:: %s", pohId)));
    }

    @Transactional
    public void updatePO(POHeaderRequest poHeaderRequest) {
        var po = poHeaderRepository.findById(poHeaderRequest.id())
                .orElseThrow(() -> new PONotFoundException(format("Cannot update user:: No user found with provided ID:: %s", poHeaderRequest.id())));
        mergePO(po, poHeaderRequest);
        poHeaderRepository.save(po);
    }

    private void mergePO(POHeader po, POHeaderRequest poHeaderRequest) {
        if (StringUtils.isNotBlank(poHeaderRequest.description())) {
            po.setDescription(poHeaderRequest.description());
        }
        if (StringUtils.isNotBlank(String.valueOf(poHeaderRequest.totalCost()))) {
            po.setTotalCost(poHeaderRequest.totalCost());
        }
        if (StringUtils.isNotBlank(String.valueOf(poHeaderRequest.totalPrice()))) {
            po.setTotalPrice(poHeaderRequest.totalPrice());
        }
        if (StringUtils.isNotBlank(poHeaderRequest.updatedBy())) {
            po.setUpdatedBy(poHeaderRequest.updatedBy());
        }
        // Update PO Details jika ada data baru
        if (poHeaderRequest.poDetails() != null && !poHeaderRequest.poDetails().isEmpty()) {
            // Loop untuk memperbarui setiap PODetail berdasarkan ID yang sudah ada
            List<PODetail> updatedPoDetails = new ArrayList<>();

            for (PODetailRequest podetailRequest : poHeaderRequest.poDetails()) {
                if (podetailRequest.id() != null) {
                    // Cari PODetail yang ada berdasarkan ID
                    PODetail existingPODetail = po.getPoDetails().stream()
                            .filter(poDetail -> poDetail.getId().equals(podetailRequest.id()))
                            .findFirst()
                            .orElse(null);

                    if (existingPODetail != null) {
                        // Kalau ada, update detailnya
                        existingPODetail.setItemQty(podetailRequest.itemQty());
                        existingPODetail.setItemCost(podetailRequest.itemCost());
                        existingPODetail.setItemPrice(podetailRequest.itemPrice());
                        updatedPoDetails.add(existingPODetail);
                    } else {
                        // Kalau tidak ada, buat yang baru
                        updatedPoDetails.add(poDetailMapper.toPODetail(podetailRequest, po));
                    }
                }
            }

            // Update daftar PO Details
            po.setPoDetails(updatedPoDetails);
        }
    }

    public void deletePOHeader(Integer pohId) {
        POHeader po = poHeaderRepository.findById(pohId)
                        .orElseThrow(() -> new PONotFoundException(format("PO Header not found with ID: " + pohId)));

        poHeaderRepository.delete(po);
    }

    public void deletePODetail(Integer detailId) {
        PODetail pd = poDetailRepository.findById(detailId)
                .orElseThrow(() -> new PONotFoundException(format("PO Detail not found with ID: " + detailId)));
        poDetailRepository.delete(pd);
    }
}
