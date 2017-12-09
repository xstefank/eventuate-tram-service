package org.eventuate.saga.orderservice.model;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.learn.eventuate.coreapi.ProductInfo;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Entity
@Table(name = "OrderEntity")
@NoArgsConstructor
@ToString
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
