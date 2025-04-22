package com.onlineshop.product_service.service;

import com.onlineshop.product_service.dto.ProductRequest;
import com.onlineshop.product_service.dto.ProductResponse;
import com.onlineshop.product_service.model.Product;
import com.onlineshop.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest ){

        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);

        log.info("Product {} is saved",product.getId());
    }

    public List<ProductResponse> getAllProduct() {
        List<Product> findAll = productRepository.findAll();
        System.out.println("Products :" + productRepository.findAll());

       List<ProductResponse> productResponseStream = findAll.stream().map(this::mapToProductResponse).collect(Collectors.toList());

       log.info("Product {} fetched succesfully");

        return productResponseStream;
    }

     private ProductResponse mapToProductResponse(Product product){

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .build();
     }
}
