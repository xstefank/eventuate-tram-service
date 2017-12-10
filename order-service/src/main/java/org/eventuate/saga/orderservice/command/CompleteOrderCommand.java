package org.eventuate.saga.orderservice.command;

import io.eventuate.tram.commands.common.Command;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CompleteOrderCommand implements Command {

    private String orderId;

    public CompleteOrderCommand(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}
