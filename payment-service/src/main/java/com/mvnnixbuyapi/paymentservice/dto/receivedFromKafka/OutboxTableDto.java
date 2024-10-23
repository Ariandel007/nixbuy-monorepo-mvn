package com.mvnnixbuyapi.paymentservice.dto.receivedFromKafka;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;

@Data
public class OutboxTableDto {
    @JsonProperty("event_id")
    private String eventId;
    @JsonProperty("event_type")
    private String eventType;
    private Instant timestamp;
    private String data;
    @JsonProperty("aggregate_id")
    private String aggregateId;
    @JsonProperty("aggregate_type")
    private String aggregateType;

}
