package com.mvnnixbuyapi.order.service;

import com.mvnnixbuyapi.commons.enums.OrderStates;
import com.mvnnixbuyapi.order.model.dto.OrderReceivedDto;
import com.mvnnixbuyapi.order.model.entity.Order;
import com.mvnnixbuyapi.order.port.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CancelingStatusOfOrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public CancelingStatusOfOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Order execute(OrderReceivedDto orderReceivedDto) {
        orderReceivedDto.setStatus(OrderStates.CANCELED.name());
        var orderToPersist = new Order().requestToCreate(orderReceivedDto);
        return this.orderRepository.updateOrder(orderToPersist);
    }
}
