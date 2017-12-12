package org.eventuate.saga.invoiceservice.controller;

import org.eventuate.saga.invoiceservice.model.Invoice;
import org.eventuate.saga.invoiceservice.model.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InvoiceController {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @GetMapping
    public String test() {
        Invoice invoice = invoiceRepository.save(new Invoice("testOrderId", "this is not the invoice"));
        return invoice.getId();
    }
}
