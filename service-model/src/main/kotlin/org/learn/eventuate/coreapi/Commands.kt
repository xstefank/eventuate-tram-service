package org.learn.eventuate.coreapi

import io.eventuate.tram.commands.common.Command

data class RequestShipmentCommand(val orderId: String = "", val productInfo: ProductInfo? = null) : Command
data class RequestInvoiceCommand(val orderId: String = "", val productInfo: ProductInfo? = null) : Command

data class CompensateShipmentCommand(val orderId: String = "") : Command
data class CompensateInvoiceCommand(val orderId: String = "") : Command

