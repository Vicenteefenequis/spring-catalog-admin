package com.fullcycle.admin.catalog.infrastructure.services.impl;

import com.fullcycle.admin.catalog.AmqpTest;
import com.fullcycle.admin.catalog.domain.video.VideoMediaCreated;
import com.fullcycle.admin.catalog.infrastructure.configuration.annotations.VideoCreatedQueue;
import com.fullcycle.admin.catalog.infrastructure.configuration.json.Json;
import com.fullcycle.admin.catalog.infrastructure.services.EventService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.test.RabbitListenerTestHarness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@AmqpTest
public class RabbitEventServiceTest {

    private final static String LISTENER = "video.created";

    @Autowired
    @VideoCreatedQueue
    private EventService publisher;

    @Autowired
    private RabbitListenerTestHarness harness;

    @Test
    public void shouldSendMessage() throws InterruptedException {
        final var notification = new VideoMediaCreated("resource", "filepath");

        final var expectedMessage = Json.writeValueAsString(notification);

        this.publisher.send(notification);

        //then
        final var invocationData = harness.getNextInvocationDataFor(LISTENER, 1, TimeUnit.SECONDS);
        Assertions.assertNotNull(invocationData);
        Assertions.assertNotNull(invocationData.getArguments());

        final var actualMessage = (String) invocationData.getArguments()[0];

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Component
    static class VideoCreatedNewsListener {
        @RabbitListener(id = LISTENER, queues = "${amqp.queues.video-created.routing-key}")
        void onVideoCreated(@Payload String message) {

        }
    }
}
