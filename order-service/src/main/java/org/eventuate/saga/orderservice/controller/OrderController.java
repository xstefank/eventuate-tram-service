package org.eventuate.saga.orderservice.controller;

import org.learn.eventuate.coreapi.ProductInfo;
import org.learn.eventuate.util.Util;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class OrderController {

    @PostMapping
    public String createOrder(@RequestBody ProductInfo productInfo) {
        String orderId = Util.generateId();

        //TODO orderservice
//        orderService.save(orderId, productInfo);

        return "Order is being processed - " + orderId;
    }

}
