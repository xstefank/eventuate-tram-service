package org.eventuate.saga.orderservice.controller;

import org.eventuate.saga.orderservice.model.Order;
import org.eventuate.saga.orderservice.service.OrderService;
import org.learn.eventuate.coreapi.ProductInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order")
    public String createOrder(@RequestBody ProductInfo productInfo) {
        Order order = orderService.createOrder(productInfo);

        return "Order is being processed - " + order.getId();
    }

    @GetMapping("/orders")
    public List<Order> getAllCompletedOrders() {
        return orderService.findAllCompletedOrders();
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Order> findOne(@PathVariable String orderId) {
        Order result = orderService.findOne(orderId);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
