package org.eventuate.saga.orderservice.model;

import lombok.ToString;
import org.learn.eventuate.coreapi.ProductInfo;
import org.springframework.data.annotation.Id;

@ToString
public class Order {

    @Id
    private String id;

    private ProductInfo productInfo;
    private String shipmentId;
    private String invoiceId;
    private boolean completed;

    public Order(ProductInfo productInfo) {
        this.productInfo = productInfo;
        this.completed = false;
    }

    public String getId() {
        return id;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
