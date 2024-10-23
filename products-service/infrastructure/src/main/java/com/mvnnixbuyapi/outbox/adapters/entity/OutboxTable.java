package com.mvnnixbuyapi.outbox.adapters.entity;

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
    @Column(columnDefinition = "bytea")
    private byte[] data;

    @Column(name = "aggregate_id")
    private String aggregateId;
    @Column(name = "aggregate_type")
    private String aggregateType;

}

/*
* The outbox pattern is a technical solution to ensure eventual consistency between the database and the messaging
* system (such as Kafka), ensuring that events are sent correctly when a business operation occurs. These types of
* mechanisms are part of the infrastructure because they are implementation details that enable the system to function
* correctly, but they do not affect or form part of the core logic of the application. So this entity is just going to
* interact with infrastructure layer and not domain or application.
* */
