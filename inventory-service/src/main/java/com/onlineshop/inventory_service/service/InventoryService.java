package com.onlineshop.inventory_service.service;

import com.onlineshop.inventory_service.dto.InventoryResponse;
import com.onlineshop.inventory_service.repo.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    @SneakyThrows
    public List<InventoryResponse> isInStock(List<String> skuCode) {

        log.info("Wait started");

        Thread.sleep(10000);

       /* try {
            Thread.sleep(10000);

        }catch (Exception e){
           log.info("Exception occurs in inventory thread");
        }*/


        log.info("Wait ended");

     return inventoryRepository.findBySkuCodeIn(skuCode).stream()
             .map(inventory ->
                 InventoryResponse.builder()
                         .skuCode(inventory.getSkuCode())
                         .isInStock(inventory.getQuantity() > 0)
                         .build()

             ).toList();
    }
}
