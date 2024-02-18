package com.mvnnixbuyapi.product.model.dto.command;

import lombok.Data;

import java.time.Instant;

@Data
public class OutboxTableDto {
    private String eventId;
    private String eventType;
    private Instant timestamp;
    private String data;
    private String aggregateId;
    private String aggregateType;

}
