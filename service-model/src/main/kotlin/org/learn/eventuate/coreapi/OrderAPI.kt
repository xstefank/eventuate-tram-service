package org.learn.eventuate.coreapi


//domain
data class ProductInfo(val productId: String = "", val comment: String = "", val price: Int = 0)

data class ShipmentInfo(val orderId: String = "", val shipmentId: String = "", val shipmentPrice: Int = 0)
data class InvoiceInfo(val orderId: String = "", val invoiceId: String = "", val invoice: String = "")

data class ParticipantFailureInfo(val orderId: String = "", val id: String = "", val cause: String = "")
