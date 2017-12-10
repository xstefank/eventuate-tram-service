package org.eventuate.saga.shipmentservice.model;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@ToString
@NoArgsConstructor
public class Shipment {

    @Id
    private String id;

    private String orderId;

    private int price;

    public Shipment(String orderId, int price) {
        this.orderId = orderId;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getOrderId() {
        return orderId;
    }

    public int getPrice() {
        return price;
    }
}
