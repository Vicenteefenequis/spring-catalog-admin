package com.fullcycle.admin.catalog.domain.event;

@FunctionalInterface
public interface DomainEventPublisher<T extends DomainEvent> {
    void publishEvent(T event);
}
