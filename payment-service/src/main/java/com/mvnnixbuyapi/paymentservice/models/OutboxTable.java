package com.mvnnixbuyapi.paymentservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "outbox_table")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OutboxTable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "event_id")
    private String eventId;
    @Column(name = "event_type", length = 255)
    private String eventType;
    @Column(name = "timestamp")
    private Instant timestamp;

    //Esto seria un bytea en postgres:
    @Lob
    private byte[] data;

    @Column(name = "aggregate_id")
    private String aggregateId;
    @Column(name = "aggregate_type")
    private String aggregateType;

}
