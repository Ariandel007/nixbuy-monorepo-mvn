package com.mvnnixbuyapi.order.service;

import com.mvnnixbuyapi.order.model.dto.OrderReceivedDto;
import com.mvnnixbuyapi.order.model.entity.Order;
import com.mvnnixbuyapi.order.port.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class UpdateStatusOfOrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public UpdateStatusOfOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Order execute(OrderReceivedDto orderReceivedDto) {
        var orderToPersist = new Order().requestToCreate(orderReceivedDto);
        return this.orderRepository.updateOrder(orderToPersist);
    }
}
