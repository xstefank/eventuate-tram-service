package org.eventuate.saga.invoiceservice.controller;

import org.eventuate.saga.invoiceservice.model.Invoice;
import org.eventuate.saga.invoiceservice.model.InvoiceRepository;
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
public class InvoiceController {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @GetMapping("/invoices")
    public List<Invoice> getAll() {
        return invoiceRepository.findAll();
    }

    @GetMapping("/invoice/{invoiceId}")
    public ResponseEntity<Invoice> getOne(@PathVariable String invoiceId) {
        Invoice result = invoiceRepository.findOne(invoiceId);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
