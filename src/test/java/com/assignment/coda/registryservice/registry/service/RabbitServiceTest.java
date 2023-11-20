package com.assignment.coda.registryservice.registry.service;

import com.assignment.coda.registryservice.registry.dto.Instance;
import com.assignment.coda.registryservice.registry.dto.RegistryEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class RabbitServiceTest {

    @Mock
    private RabbitTemplate rabbitTemplateMock;

    @InjectMocks
    private RabbitServiceImpl rabbitService;

    @Test
    public void sent_passEventToRabbitQueue() {
        RegistryEvent event = new RegistryEvent(
                "action",
                new Instance("name", "1234"));

        rabbitService.sent(event);

        verify(rabbitTemplateMock, times(1)).convertAndSend(any(), eq(event));
    }
}