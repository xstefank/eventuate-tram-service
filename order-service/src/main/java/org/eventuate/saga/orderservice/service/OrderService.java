package org.eventuate.saga.orderservice.service;

import org.eventuate.saga.orderservice.model.Order;
import org.eventuate.saga.orderservice.model.OrderRepository;
import org.learn.eventuate.coreapi.ProductInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(ProductInfo productInfo) {
        log.info("processing order for " + productInfo);

        Order order = orderRepository.save(new Order(productInfo));

        return order;
    }
}
