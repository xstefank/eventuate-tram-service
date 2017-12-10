package org.eventuate.saga.orderservice.saga;

import io.eventuate.tram.commands.consumer.CommandWithDestination;
import io.eventuate.tram.sagas.orchestration.SagaDefinition;
import io.eventuate.tram.sagas.simpledsl.SimpleSaga;

public class OrderSaga implements SimpleSaga<OrderSagaData> {

    private SagaDefinition<OrderSagaData> sagaDefinition =
            step().withCompensation(this::rejectSaga)
            .step().invokeParticipant(this::requestShipment)
            .step().invokeParticipant(this::requestInvoice)
            .step().invokeParticipant(this::finishOrder)
            .build();

    @Override
    public SagaDefinition<OrderSagaData> getSagaDefinition() {
        return sagaDefinition;
    }

    private CommandWithDestination rejectSaga(OrderSagaData orderSagaData) {
        //TODO
        return null;
    }

    private CommandWithDestination requestShipment(OrderSagaData orderSagaData) {
        //TODO
        return null;
    }

    private CommandWithDestination requestInvoice(OrderSagaData orderSagaData) {
        //TODO
        return null;
    }

    private CommandWithDestination finishOrder(OrderSagaData orderSagaData) {
        //TODO
        return null;
    }
}
