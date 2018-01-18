package org.eventuate.saga.shipmentservice.controller;

import org.eventuate.saga.shipmentservice.model.Shipment;
import org.eventuate.saga.shipmentservice.model.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ShipmentController {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @GetMapping("/shipments")
    public List<Shipment> getAll() {
        return shipmentRepository.findAll();
    }

    @GetMapping("/shipment/{shipementId}")
    public ResponseEntity<Shipment> getOne(@PathVariable String shipmentId) {
        Shipment result = shipmentRepository.findOne(shipmentId);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
