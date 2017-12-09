package org.eventuate.saga.orderservice.model;

import lombok.ToString;
import org.learn.eventuate.coreapi.ProductInfo;
import org.springframework.data.annotation.Id;

@ToString
public class Order {

    @Id
    private String id;

    private ProductInfo productInfo;
    private boolean completed;

    public String getId() {
        return id;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
