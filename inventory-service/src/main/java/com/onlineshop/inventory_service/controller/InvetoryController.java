package com.onlineshop.inventory_service.controller;

import com.onlineshop.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/inventory")
@RequiredArgsConstructor
public class InvetoryController {

    @Autowired
    private final InventoryService inventoryService;

    @GetMapping(value = "/sku_code")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable("sku_code") String skuCode){
        System.out.println("sdassdhhdhjajsdjjjjj"+skuCode);

        return inventoryService.isInStock(skuCode);
    }
}
