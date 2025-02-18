package com.mvnnixbuyapi.paymentservice.services;


import com.mvnnixbuyapi.commons.monads.ResultMonad;
import com.mvnnixbuyapi.paymentservice.dto.request.CreateOrderDto;
import com.mvnnixbuyapi.paymentservice.models.Order;

public interface OrderService {
    boolean checkIfOrderIsForUser(Long orderId, Long userId);
    ResultMonad<Order> createOrder(CreateOrderDto createOrderDto);
    ResultMonad<Order> updateOrderStatusById(Long orderId, String orderStatus);
    ResultMonad<Order> findOrderById(Long orderId);

}
