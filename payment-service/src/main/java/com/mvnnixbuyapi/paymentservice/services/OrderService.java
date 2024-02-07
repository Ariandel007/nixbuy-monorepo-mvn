package com.mvnnixbuyapi.paymentservice.services;


import com.mvnnixbuyapi.paymentservice.dto.request.CreateOrderDto;
import com.mvnnixbuyapi.paymentservice.models.Order;

public interface OrderService {
    boolean checkIfOrderIsForUser(Long orderId, Long userId);
    Order createOrder(CreateOrderDto createOrderDto);

}
