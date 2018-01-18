package org.eventuate.saga.shipmentservice.service;

import org.eventuate.saga.shipmentservice.model.Shipment;
import org.eventuate.saga.shipmentservice.model.ShipmentRepository;
import org.learn.eventuate.coreapi.ProductInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShipmentService {

    private static final Logger log = LoggerFactory.getLogger(ShipmentService.class);

    @Autowired
    private ShipmentRepository shipmentRepository;

    public Shipment computeShipment(String orderId, ProductInfo productInfo) {
        log.info("computing shipment for order - " + orderId);

        int shippingPrice = computeShipmentForProduct(productInfo);
        Shipment shipment = shipmentRepository.save(new Shipment(orderId, shippingPrice));

        return shipment;
    }

    private int computeShipmentForProduct(ProductInfo productInfo) {
        //return testing stub for now
        return 42;
    }

    public void deleteShipment(String orderId) {
        log.info("deleting shipment for order - " + orderId);

        Shipment shipment = shipmentRepository.findByOrderId(orderId);
        shipmentRepository.delete(shipment);
        log.info(String.format("Shipment %s deleted", shipment));
    }
}
