package org.eventuate.saga.orderservice.saga;

import io.eventuate.tram.commands.consumer.CommandWithDestination;
import io.eventuate.tram.sagas.orchestration.SagaDefinition;
import io.eventuate.tram.sagas.simpledsl.SimpleSaga;
import org.eventuate.saga.orderservice.command.CompleteOrderCommand;
import org.eventuate.saga.orderservice.command.RejectOrderCommand;
import org.eventuate.saga.orderservice.model.Order;
import org.eventuate.saga.orderservice.model.OrderRepository;
import org.learn.eventuate.Constants;
import org.learn.eventuate.coreapi.CompensateInvoiceCommand;
import org.learn.eventuate.coreapi.CompensateShipmentCommand;
import org.learn.eventuate.coreapi.InvoiceInfo;
import org.learn.eventuate.coreapi.ParticipantFailureInfo;
import org.learn.eventuate.coreapi.RequestInvoiceCommand;
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
                .onReply(ParticipantFailureInfo.class, this::shipmentFailure)
              .withCompensation(this::shipmentCompensate)
            .step()
              .invokeParticipant(this::requestInvoice)
                .onReply(InvoiceInfo.class, this::invoiceReply)
                .onReply(ParticipantFailureInfo.class, this::invoiceFailure)
              .withCompensation(this::invoiceCompensate)
            .step()
              .invokeParticipant(this::finishOrder)
            .build();

    @Override
    public SagaDefinition<OrderSagaData> getSagaDefinition() {
        return sagaDefinition;
    }

    private CommandWithDestination rejectSaga(OrderSagaData orderSagaData) {
        log.info("rejectSaga()");

        return send(new RejectOrderCommand(orderSagaData))
                .to(Constants.ORDER_SERVICE)
                .build();
    }

    private CommandWithDestination requestShipment(OrderSagaData orderSagaData) {
        log.info("requestShipment()");

        return send(new RequestShipmentCommand(orderSagaData.getOrderId(), orderSagaData.getProductInfo()))
                .to(Constants.SHIPMENT_SERVICE)
                .build();
    }

    //cannot send command
    private void shipmentReply(OrderSagaData orderSagaData, ShipmentInfo shipmentInfo) {
        log.info("shipmentReply()");

        Order order = orderRepository.findOne(orderSagaData.getOrderId());
        order.setShipmentId(shipmentInfo.getShipmentId());
        orderRepository.save(order);
        log.info("order updated with shipment - " + order);
    }

    private CommandWithDestination shipmentCompensate(OrderSagaData orderSagaData) {
        log.info("shipmentCompensate()");

        return send(new CompensateShipmentCommand(orderSagaData.getOrderId()))
                .to(Constants.SHIPMENT_SERVICE)
                .build();
    }

    private void shipmentFailure(OrderSagaData orderSagaData, ParticipantFailureInfo failureInfo) {
        log.info(String.format("shipmentFailure() for %s with cause '%s'", orderSagaData.getOrderId(), failureInfo.getCause()));
    }

    private CommandWithDestination requestInvoice(OrderSagaData orderSagaData) {
        log.info("requestInvoice()");

        return send(new RequestInvoiceCommand(orderSagaData.getOrderId(), orderSagaData.getProductInfo()))
                .to(Constants.INVOCE_SERVICE)
                .build();
    }

    private void invoiceReply(OrderSagaData orderSagaData, InvoiceInfo invoiceInfo) {
        log.info("invoiceReply()");

        Order order = orderRepository.findOne(orderSagaData.getOrderId());
        order.setInvoiceId(invoiceInfo.getInvoiceId());
        orderRepository.save(order);
        log.info("order updated with invoice - " + order);
    }

    private CommandWithDestination invoiceCompensate(OrderSagaData orderSagaData) {
        log.info("invoiceCompensate()");

        return send(new CompensateInvoiceCommand(orderSagaData.getOrderId()))
                .to(Constants.INVOCE_SERVICE)
                .build();
    }

    private void invoiceFailure(OrderSagaData orderSagaData, ParticipantFailureInfo failureInfo) {
        log.info(String.format("invoiceFailure() for %s with cause '%s'", orderSagaData.getOrderId(), failureInfo.getCause()));
    }

    private CommandWithDestination finishOrder(OrderSagaData orderSagaData) {
        log.info("finishOrder()");
        return send(new CompleteOrderCommand(orderSagaData.getOrderId()))
               .to(Constants.ORDER_SERVICE)
               .build();
    }
}
