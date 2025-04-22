package com.onlineshop.order_service.service;

import com.onlineshop.order_service.dto.OrderLineItemsDTO;
import com.onlineshop.order_service.dto.OrderRequest;
import com.onlineshop.order_service.model.Order;
import com.onlineshop.order_service.model.OrderLineItems;
import com.onlineshop.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    @Autowired
    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest){

        Order order = new Order();

        order.setOrderNo(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsDTOList()
                .stream()
                .map(this::mapToDTO)
                .toList();

        order.setOrderLineItems(orderLineItemsList);

        orderRepository.save(order);
    }

    private OrderLineItems mapToDTO(OrderLineItemsDTO orderLineItemsDTO) {

        OrderLineItems orderLineItems = new OrderLineItems();

        orderLineItems.setPrice(orderLineItemsDTO.getPrice());
        orderLineItems.setSkuCode(orderLineItemsDTO.getSkuCode());
        orderLineItems.setQuantity(orderLineItems.getQuantity());

        return orderLineItems;
    }
}
