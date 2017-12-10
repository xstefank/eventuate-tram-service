package org.eventuate.saga.orderservice.service;

import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;
import org.eventuate.saga.orderservice.command.TestCommand;
import org.eventuate.saga.orderservice.model.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;

public class OrderCommandHandler {

    private static final String CHANNEL_NAME = "orderservice";
    private static final Logger log = LoggerFactory.getLogger(OrderCommandHandler.class);

    @Autowired
    private OrderRepository orderRepository;

    public CommandHandlers commandHandlers() {
        return SagaCommandHandlersBuilder
                .fromChannel(CHANNEL_NAME)
                .onMessage(TestCommand.class, this::test)
                .build();
    }

    private Message test(CommandMessage<TestCommand> commandMessage) {
        log.info("testing command received - " + commandMessage.getCommand().getTestString());
        return withSuccess();
    }
}
