package org.eventuate.saga.invoiceservice.service;

import org.eventuate.saga.invoiceservice.model.Invoice;
import org.eventuate.saga.invoiceservice.model.InvoiceRepository;
import org.learn.eventuate.coreapi.ProductInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InvoiceService {

    private static final Logger log = LoggerFactory.getLogger(InvoiceService.class);

    @Autowired
    private InvoiceRepository invoiceRepository;


    public Invoice createInvoice(String orderId, ProductInfo productInfo) {
        log.info("computing invoice for order - " + orderId);

        String invoiceString = createInvoiceString(productInfo);
        return invoiceRepository.save(new Invoice(orderId, invoiceString));
    }

    private String createInvoiceString(ProductInfo productInfo) {
        //return testing stub
        return "This is not the invoice you are looking for";
    }

    public void deleteInvoice(String orderId) {
        log.info("deleting invoice for order - " + orderId);

        Invoice invoice = invoiceRepository.findByOrderId(orderId);
        invoiceRepository.delete(invoice);

        log.info("invoice %s deleted", invoice);
    }
}
