package org.eventuate.saga.orderservice.service;

import io.eventuate.tram.sagas.orchestration.SagaManager;
import org.eventuate.saga.orderservice.model.Order;
import org.eventuate.saga.orderservice.model.OrderRepository;
import org.eventuate.saga.orderservice.saga.OrderSagaData;
import org.learn.eventuate.coreapi.ProductInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SagaManager<OrderSagaData> orderSagaManager;

    @Transactional
    public Order createOrder(ProductInfo productInfo) {
        log.info("processing order for " + productInfo);

        Order order = orderRepository.save(new Order(productInfo));

        OrderSagaData orderSagaData = new OrderSagaData(order.getId(), order.getProductInfo());
        orderSagaManager.create(orderSagaData, Order.class, order.getId());

        return order;
    }

    public List<Order> findAllCompletedOrders() {
        return orderRepository.findByCompletedIsTrue();
    }

    public Order findOne(String id) {
        return orderRepository.findOne(id);
    }
}
