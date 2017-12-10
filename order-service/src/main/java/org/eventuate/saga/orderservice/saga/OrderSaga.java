package org.eventuate.saga.orderservice.saga;

import io.eventuate.tram.commands.consumer.CommandWithDestination;
import io.eventuate.tram.sagas.orchestration.SagaDefinition;
import io.eventuate.tram.sagas.simpledsl.SimpleSaga;
import org.eventuate.saga.orderservice.command.CompleteOrderCommand;
import org.eventuate.saga.orderservice.command.TestCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static io.eventuate.tram.commands.consumer.CommandWithDestinationBuilder.send;

@Component
public class OrderSaga implements SimpleSaga<OrderSagaData> {

    private static final Logger log = LoggerFactory.getLogger(OrderSaga.class);

    private SagaDefinition<OrderSagaData> sagaDefinition =
            step().withCompensation(this::rejectSaga)
                    .step().invokeParticipant(this::testCommand)
//            .step().invokeParticipant(this::requestShipment)
//            .step().invokeParticipant(this::requestInvoice)
            .step().invokeParticipant(this::finishOrder)
            .build();

    private CommandWithDestination testCommand(OrderSagaData orderSagaData) {
        return send(new TestCommand("test string 42"))
                .to("orderservice")
                .build();
    }

    @Override
    public SagaDefinition<OrderSagaData> getSagaDefinition() {
        return sagaDefinition;
    }

    private CommandWithDestination rejectSaga(OrderSagaData orderSagaData) {
        log.info("rejectSaga()");
        //TODO
        return null;
    }

    private CommandWithDestination requestShipment(OrderSagaData orderSagaData) {
        log.info("requestShipment()");
        //TODO
        return null;
    }

    private CommandWithDestination requestInvoice(OrderSagaData orderSagaData) {
        log.info("requestInvoice()");
        //TODO
        return null;
    }

    private CommandWithDestination finishOrder(OrderSagaData orderSagaData) {
        log.info("finishOrder()");
       return send(new CompleteOrderCommand(orderSagaData.getOrderId()))
               .to("orderservice")
               .build();
    }
}
