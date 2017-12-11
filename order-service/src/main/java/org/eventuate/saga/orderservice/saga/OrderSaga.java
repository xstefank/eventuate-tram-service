package org.eventuate.saga.orderservice.saga;

import io.eventuate.tram.commands.consumer.CommandWithDestination;
import io.eventuate.tram.sagas.orchestration.SagaDefinition;
import io.eventuate.tram.sagas.simpledsl.SimpleSaga;
import org.eventuate.saga.orderservice.command.CompleteOrderCommand;
import org.eventuate.saga.orderservice.command.RejectOrderSagaCommand;
import org.eventuate.saga.orderservice.command.ShipmentReplyCommand;
import org.eventuate.saga.orderservice.model.OrderRepository;
import org.learn.eventuate.Constants;
import org.learn.eventuate.coreapi.RequestShipmentCommand;
import org.learn.eventuate.coreapi.ShipmentInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static io.eventuate.tram.commands.consumer.CommandWithDestinationBuilder.send;

@Component
public class OrderSaga implements SimpleSaga<OrderSagaData> {

    private static final Logger log = LoggerFactory.getLogger(OrderSaga.class);

    @Autowired
    private OrderRepository orderRepository;

    private SagaDefinition<OrderSagaData> sagaDefinition =
            step()
                    .withCompensation(this::rejectSaga)
            .step()
                    .invokeParticipant(this::requestShipment)
                    .onReply(ShipmentInfo.class, this::shipmentReply)
//            .step().invokeParticipant(this::requestInvoice)
            .step()
                    .invokeParticipant(this::finishOrder)
            .build();

    @Override
    public SagaDefinition<OrderSagaData> getSagaDefinition() {
        return sagaDefinition;
    }

    private CommandWithDestination rejectSaga(OrderSagaData orderSagaData) {
        log.info("rejectSaga()");

        return send(new RejectOrderSagaCommand(orderSagaData))
                .to(Constants.ORDER_SERVICE)
                .build();
    }

    private CommandWithDestination requestShipment(OrderSagaData orderSagaData) {
        log.info("requestShipment()");

        return send(new RequestShipmentCommand(orderSagaData.getOrderId(), orderSagaData.getProductInfo()))
                .to(Constants.SHIPMENT_SERVICE)
                .build();
    }

    private CommandWithDestination shipmentReply(OrderSagaData orderSagaData, ShipmentInfo shipmentInfo) {
        log.info("shipmentReply()");

        return send(new ShipmentReplyCommand(orderSagaData.getOrderId(), shipmentInfo))
                .to(Constants.ORDER_SERVICE)
                .build();
    }

    private CommandWithDestination requestInvoice(OrderSagaData orderSagaData) {
        log.info("requestInvoice()");
        //TODO
        return null;
    }

    private CommandWithDestination finishOrder(OrderSagaData orderSagaData) {
        log.info("finishOrder()");
       return send(new CompleteOrderCommand(orderSagaData.getOrderId()))
               .to(Constants.ORDER_SERVICE)
               .build();
    }
}
