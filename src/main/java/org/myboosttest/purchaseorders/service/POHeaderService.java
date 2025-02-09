package org.myboosttest.purchaseorders.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.myboosttest.purchaseorders.dto.POHeaderRequest;
import org.myboosttest.purchaseorders.dto.POHeaderResponse;
import org.myboosttest.purchaseorders.entity.Item;
import org.myboosttest.purchaseorders.entity.PODetail;
import org.myboosttest.purchaseorders.entity.POHeader;
import org.myboosttest.purchaseorders.exception.ItemNotFoundException;
import org.myboosttest.purchaseorders.repository.ItemRepository;
import org.myboosttest.purchaseorders.repository.POHeaderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class POHeaderService {

    private final POHeaderRepository poHeaderRepository;
    private final POHeaderMapper poHeaderMapper;
    private final PODetailMapper poDetailMapper;
    private final ItemRepository itemRepository;


    @Transactional
    public POHeader createPO(POHeaderRequest poHeaderRequest) {
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
//                        Item newItem = new Item();
//                        newItem.setId(itemId);
//                        poDetail.setItem(newItem);
                    }

                    return poDetail;
                })
                .toList();

        poHeader.setPoDetails(details);

        return poHeaderRepository.save(poHeader);

    }

    public List<POHeaderResponse> findAll() {
        return poHeaderRepository.findAll()
                .stream()
                .map(poHeaderMapper::fromPOHeader)
                .collect(Collectors.toList());
    }
}
