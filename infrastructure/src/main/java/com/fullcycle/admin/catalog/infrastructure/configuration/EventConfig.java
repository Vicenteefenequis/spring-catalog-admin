package com.fullcycle.admin.catalog.infrastructure.configuration;

import com.fullcycle.admin.catalog.infrastructure.configuration.annotations.VideoCreatedQueue;
import com.fullcycle.admin.catalog.infrastructure.configuration.properties.amqp.QueueProperties;
import com.fullcycle.admin.catalog.infrastructure.services.EventService;
import com.fullcycle.admin.catalog.infrastructure.services.impl.RabbitEventService;
import com.fullcycle.admin.catalog.infrastructure.services.local.InMemoryEventService;
import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class EventConfig {

    @Bean
    @VideoCreatedQueue
    @Profile({"development"})
    EventService videoCreatedInMemoryEventService() {
        return new InMemoryEventService();
    }

    @Bean
    @VideoCreatedQueue
    @ConditionalOnMissingBean
    EventService videoCreatedEventService(
            @VideoCreatedQueue final QueueProperties properties,
            final RabbitOperations ops
    ) {
        return new RabbitEventService(properties.getExchange(), properties.getRoutingKey(), ops);
    }
}
