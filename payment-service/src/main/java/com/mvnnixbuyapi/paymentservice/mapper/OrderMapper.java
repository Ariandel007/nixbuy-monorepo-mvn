package com.mvnnixbuyapi.paymentservice.mapper;

import com.mvnnixbuyapi.paymentservice.dto.sendToKafka.OrderKafkaDto;
import com.mvnnixbuyapi.paymentservice.dto.sendToKafka.OrderStatusUpdateKafkaDto;
import com.mvnnixbuyapi.paymentservice.models.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderKafkaDto toDtoCreate(Order domain);
    OrderStatusUpdateKafkaDto toDtoStatusOrder(Order domain);

}
