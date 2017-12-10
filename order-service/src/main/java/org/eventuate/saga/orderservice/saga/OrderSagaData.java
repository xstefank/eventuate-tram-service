package org.eventuate.saga.orderservice.saga;

import lombok.ToString;
import org.learn.eventuate.coreapi.ProductInfo;

@ToString
public class OrderSagaData {

    private String orderId;

    private ProductInfo productInfo;

    public OrderSagaData(String orderId, ProductInfo productInfo) {
        this.orderId = orderId;
        this.productInfo = productInfo;
    }

    public String getOrderId() {
        return orderId;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }
}
