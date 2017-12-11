package org.eventuate.saga.orderservice.command;

import io.eventuate.tram.commands.common.Command;
import lombok.NoArgsConstructor;
import org.learn.eventuate.coreapi.ShipmentInfo;

@NoArgsConstructor
public class ShipmentReplyCommand implements Command {

    private String orderId;
    private ShipmentInfo shipmentInfo;

    public ShipmentReplyCommand(String orderId, ShipmentInfo shipmentInfo) {
        this.orderId = orderId;
        this.shipmentInfo = shipmentInfo;
    }

    public String getOrderId() {
        return orderId;
    }

    public ShipmentInfo getShipmentInfo() {
        return shipmentInfo;
    }
}
