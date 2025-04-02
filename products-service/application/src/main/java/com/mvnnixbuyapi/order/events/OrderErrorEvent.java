package com.mvnnixbuyapi.order.events;

import com.mvnnixbuyapi.order.model.dto.OrderReceivedDto;

public class OrderErrorEvent {
    private String message;
    private OrderReceivedDto order;

    public OrderErrorEvent(String message, OrderReceivedDto order) {
        this.message = message;
        this.order = order;
    }

    public String getMessage() {
        return message;
    }
    public OrderReceivedDto getOrder() {
        return order;
    }

}
