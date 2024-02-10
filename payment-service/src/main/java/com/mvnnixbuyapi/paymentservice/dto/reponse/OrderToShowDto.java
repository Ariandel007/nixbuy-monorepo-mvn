package com.mvnnixbuyapi.paymentservice.dto.reponse;

import com.mvnnixbuyapi.paymentservice.dto.request.ItemCartDto;
import lombok.Data;

import java.util.List;

@Data
public class OrderToShowDto {
    private List<ItemCartDto> itemCartDtoList;

}
