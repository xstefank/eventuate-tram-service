package org.eventuate.saga.shipmentservice.model;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends MongoRepository<Shipment, String> {

    Shipment findByOrderId(String orderId);
}
