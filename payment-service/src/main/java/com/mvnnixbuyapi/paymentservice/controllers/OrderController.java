package com.mvnnixbuyapi.paymentservice.controllers;

import com.mvnnixbuyapi.commons.dtos.response.GenericResponseForBody;
import com.mvnnixbuyapi.commons.monads.ResultMonad;
import com.mvnnixbuyapi.commons.utils.ResponseUtils;
import com.mvnnixbuyapi.paymentservice.dto.request.CreateOrderDto;
import com.mvnnixbuyapi.paymentservice.dto.request.ItemCartDto;
import com.mvnnixbuyapi.paymentservice.models.Order;
import com.mvnnixbuyapi.paymentservice.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @PostMapping(value = "/v1/create-order/{userId}")
    public ResponseEntity<GenericResponseForBody<Order>> listResponseEntityShowItems(
            @PathVariable Long userId, @RequestBody CreateOrderDto createOrderDto
            ) {
        createOrderDto.setUserId(userId);
        ResultMonad<Order> orderResultMonad = this.orderService.createOrder(createOrderDto);
        return ResponseUtils.buildSuccessResponse(orderResultMonad.getValue());
    }

}
