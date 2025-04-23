package com.onlineshop.inventory_service;

import com.onlineshop.inventory_service.model.Inventory;
import com.onlineshop.inventory_service.repo.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

		@Bean
		public CommandLineRunner loadData(InventoryRepository inventoryRepository ){

		return args -> {
			System.out.println("=== CommandLineRunner is executing ===");

			Inventory inventory = new Inventory();
			inventory.setSkuCode("iphone-13");
			inventory.setQuantity(100);

			Inventory inventory1 = new Inventory();
			inventory1.setSkuCode("iphone-1-pro");
			inventory1.setQuantity(0);
			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory1);
		};

		}
}
