package com.onlineshop.order_service.controller;

import com.onlineshop.order_service.dto.OrderRequest;
import com.onlineshop.order_service.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/api/order")
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest){

         return CompletableFuture.supplyAsync(() -> orderService.placeOrder(orderRequest)) ;
    }

    public CompletableFuture<String> fallbackMethod(OrderRequest orderRequest, RuntimeException runtimeException){

        return CompletableFuture.supplyAsync(() -> "Oops!, Something wents wrong, Please order after sometime!");
    }
}
