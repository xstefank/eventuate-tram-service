package org.eventuate.saga.invoiceservice.service;

import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;
import org.eventuate.saga.invoiceservice.model.Invoice;
import org.learn.eventuate.Constants;
import org.learn.eventuate.coreapi.CompensateInvoiceCommand;
import org.learn.eventuate.coreapi.InvoiceInfo;
import org.learn.eventuate.coreapi.ParticipantFailureInfo;
import org.learn.eventuate.coreapi.RequestInvoiceCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withFailure;
import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;

@Component
public class InvoiceCommandHandler {

    private static final Logger log = LoggerFactory.getLogger(InvoiceCommandHandler.class);

    @Autowired
    private InvoiceService invoiceService;

    public CommandHandlers commandHandlers() {
        return SagaCommandHandlersBuilder
                .fromChannel(Constants.INVOCE_SERVICE)
                .onMessage(RequestInvoiceCommand.class, this::invoiceRequest)
                .onMessage(CompensateInvoiceCommand.class, this::invoiceCompensate)
                .build();
    }

    private Message invoiceRequest(CommandMessage<RequestInvoiceCommand> commandMessage) {
        log.info("received RequestInvoiceCommand");
        RequestInvoiceCommand command = commandMessage.getCommand();

        if (command.getProductInfo().getProductId().equals("failInvoice")) {
            return withFailure(new ParticipantFailureInfo(command.getOrderId(), "N/A", "testing invoice failure"));
        }

        Invoice invoice = invoiceService.createInvoice(command.getOrderId(), command.getProductInfo());
        return withSuccess(new InvoiceInfo(invoice.getOrderId(), invoice.getId(), invoice.getInvoiceString()));
    }

    private Message invoiceCompensate(CommandMessage<CompensateInvoiceCommand> commandMessage) {
        log.info("received CompensateInvoiceCommand");
        CompensateInvoiceCommand command = commandMessage.getCommand();

        invoiceService.deleteInvoice(command.getOrderId());
        return withSuccess();
    }
}
