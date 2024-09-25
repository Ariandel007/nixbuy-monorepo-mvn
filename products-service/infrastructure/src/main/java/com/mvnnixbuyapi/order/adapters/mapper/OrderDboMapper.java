package com.mvnnixbuyapi.order.adapters.mapper;

import com.mvnnixbuyapi.order.adapters.entity.OrderEntity;
import com.mvnnixbuyapi.order.model.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderDboMapper {
    public OrderDboMapper() {
    }

    public OrderEntity domainToEntity(Order order) {
        if (order == null) {
            return null;
        }
        return OrderEntity
                .builder()
                .id(order.getId())
                .creationDate(order.getCreationDate())
                .currencyCode(order.getCurrencyCode())
                .expirationDate(order.getExpirationDate())
                .taxesPercentage(order.getTaxesPercentage())
//                .userId()
                .totalPriceWithoutTaxes(order.getTotalPriceWithoutTaxes())
                .status(order.getStatus())
                .build();
    }

    public Order entityToDomain(OrderEntity orderEntity) {
        if (orderEntity == null) {
            return null;
        }
        return new Order(
                orderEntity.getId(),
                orderEntity.getTotalPriceWithoutTaxes(),
                orderEntity.getTaxesPercentage(),
                orderEntity.getCurrencyCode(),
                orderEntity.getExpirationDate(),
                orderEntity.getCreationDate(),
                orderEntity.getStatus()
        );
    }
}
