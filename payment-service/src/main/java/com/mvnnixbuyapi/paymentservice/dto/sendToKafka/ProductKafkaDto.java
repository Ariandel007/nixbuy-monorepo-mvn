package com.mvnnixbuyapi.paymentservice.dto.sendToKafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductKafkaDto {
    private Long id;
    private Integer quantity;

}
