package com.mvnnixbuyapi.order.service;

import com.mvnnixbuyapi.keyProduct.port.repository.KeyRepository;
import com.mvnnixbuyapi.order.model.dto.OrderReceivedDto;
import com.mvnnixbuyapi.order.model.entity.Order;
import com.mvnnixbuyapi.order.port.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public Order execute(OrderReceivedDto orderReceivedDto) {
        var orderToPersist = new Order().requestToCreate(orderReceivedDto);
        var productList = orderReceivedDto.getProductList();
        var keyList = this.keyRepository.findByProductsId(productList);
        orderToPersist.addProducts(keyList);
        // TODO: ADD ORDER
        return this.orderRepository.create(orderToPersist);
    }
}
