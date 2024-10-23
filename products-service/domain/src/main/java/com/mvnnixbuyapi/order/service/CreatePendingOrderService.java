package com.mvnnixbuyapi.order.service;

import com.mvnnixbuyapi.commons.enums.OrderStates;
import com.mvnnixbuyapi.keyProduct.port.repository.KeyRepository;
import com.mvnnixbuyapi.order.model.dto.OrderReceivedDto;
import com.mvnnixbuyapi.order.model.entity.Order;
import com.mvnnixbuyapi.order.port.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CreatePendingOrderService {
    private final OrderRepository orderRepository;
    private final KeyRepository keyRepository;

    @Autowired
    public CreatePendingOrderService(
            OrderRepository orderRepository,
            KeyRepository keyRepository
    ) {
        this.orderRepository = orderRepository;
        this.keyRepository = keyRepository;
    }

    @Transactional
    public Order execute(OrderReceivedDto orderReceivedDto) {
        orderReceivedDto.setStatus(OrderStates.PENDING_CONFIRMED.name());
        var orderToPersist = new Order().requestToCreate(orderReceivedDto);
        var orderSaved = this.orderRepository.create(orderToPersist);
        var productList = orderReceivedDto.getProductList();
        var keyList = this.keyRepository.findByProductsId(productList);
        orderSaved.addProducts(keyList);
        this.keyRepository.setOrderIds(orderSaved.getKeyProducts().keyProductList());
        return orderSaved;
    }
}
