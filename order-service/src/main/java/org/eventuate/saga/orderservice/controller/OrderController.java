package org.eventuate.saga.orderservice.controller;

import org.eventuate.saga.orderservice.model.Order;
import org.eventuate.saga.orderservice.service.OrderService;
import org.learn.eventuate.coreapi.ProductInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public String createOrder(@RequestBody ProductInfo productInfo) {
        Order order = orderService.createOrder(productInfo);

        return "Order is being processed - " + order.getId();
    }

}
