package com.mvnnixbuyapi.paymentservice.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderDto {
    private Long userId;
    private List<ItemCartDto> itemCartDtoList;

}
