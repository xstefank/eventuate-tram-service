package org.eventuate.saga.invoiceservice.model;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@ToString
@NoArgsConstructor
public class Invoice {

    @Id
    private String id;

    private String orderId;
    private String invoiceString;

    public Invoice(String orderId, String invoiceString) {
        this.orderId = orderId;
        this.invoiceString = invoiceString;
    }

    public String getId() {
        return id;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getInvoiceString() {
        return invoiceString;
    }
}
