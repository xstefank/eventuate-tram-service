package org.eventuate.saga.shipmentservice.service;

import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;
import org.eventuate.saga.shipmentservice.model.Shipment;
import org.learn.eventuate.Constans;
import org.learn.eventuate.coreapi.RequestShipmentCommand;
import org.learn.eventuate.coreapi.ShipmentInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;

@Component
public class ShipmentCommandHandler {

    private static final Logger log = LoggerFactory.getLogger(ShipmentCommandHandler.class);

    @Autowired
    private ShipmentService shipmentService;

    public CommandHandlers commandHandlers() {
        return SagaCommandHandlersBuilder
                .fromChannel(Constans.SHIPMENT_SERVICE)
                .onMessage(RequestShipmentCommand.class, this::requestShipment)
                .build();
    }

    public Message requestShipment(CommandMessage<RequestShipmentCommand> commandMessage) {
        log.info("received RequestShipmentCommand");
        RequestShipmentCommand command = commandMessage.getCommand();

        Shipment shipment = shipmentService.computeShipment(command.getOrderId(), command.getProductInfo());
        return withSuccess(new ShipmentInfo(shipment.getOrderId(), shipment.getId(), shipment.getPrice()));
    }


}
