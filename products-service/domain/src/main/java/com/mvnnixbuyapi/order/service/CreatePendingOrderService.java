package com.mvnnixbuyapi.order.service;

import com.mvnnixbuyapi.keyProduct.port.repository.KeyRepository;
import com.mvnnixbuyapi.order.model.dto.OrderReceivedDto;
import com.mvnnixbuyapi.order.model.entity.Order;
import com.mvnnixbuyapi.order.port.repository.OrderRepository;
import com.mvnnixbuyapi.product.model.dto.ProductReceivedDto;
import com.mvnnixbuyapi.product.port.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

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
        var keyList = this.keyRepository.findByProductsId(orderReceivedDto.getProductList()));
        orderToPersist.setKeyProducts(keyList);
        return null;
    }
}
