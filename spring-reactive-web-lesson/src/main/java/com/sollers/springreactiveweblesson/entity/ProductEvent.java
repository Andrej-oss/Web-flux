package com.sollers.springreactiveweblesson.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ProductEvent {

    private Long eventId;
    private String eventType;

    public ProductEvent() {
    }

    public ProductEvent(Long eventId, String eventType) {
        this.eventId = eventId;
        this.eventType = eventType;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return "ProductEvent{" +
                "eventId=" + eventId +
                ", eventType='" + eventType + '\'' +
                '}';
    }
}
