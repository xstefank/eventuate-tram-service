package org.eventuate.saga.orderservice.service;

import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;
import org.eventuate.saga.orderservice.command.CompleteOrderCommand;
import org.eventuate.saga.orderservice.command.RejectOrderCommand;
import org.eventuate.saga.orderservice.model.Order;
import org.eventuate.saga.orderservice.model.OrderRepository;
import org.learn.eventuate.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;

@Component
public class OrderCommandHandler {

    private static final Logger log = LoggerFactory.getLogger(OrderCommandHandler.class);

    @Autowired
    private OrderRepository orderRepository;

    public CommandHandlers commandHandlers() {
        return SagaCommandHandlersBuilder
                .fromChannel(Constants.ORDER_SERVICE)
                .onMessage(RejectOrderCommand.class, this::rejectOrder)
                .onMessage(CompleteOrderCommand.class, this::completeOrder)
                .build();
    }

    public Message rejectOrder(CommandMessage<RejectOrderCommand> commandMessage) {
        log.info("received RejectOrderCommand");
        RejectOrderCommand command = commandMessage.getCommand();

        orderRepository.delete(command.getOrderSagaData().getOrderId());

        log.info(String.format("order %s successfully rejected", command.getOrderSagaData().getOrderId()));
        return withSuccess();
    }

    public Message completeOrder(CommandMessage<CompleteOrderCommand> commandMessage) {
        log.info("received CompleteOrderCommand");
        CompleteOrderCommand command = commandMessage.getCommand();

        Order order = orderRepository.findOne(command.getOrderId());
        order.setCompleted(true);
        orderRepository.save(order);

        log.info(String.format("Order %s fully completed", order));
        return withSuccess();
    }

}
