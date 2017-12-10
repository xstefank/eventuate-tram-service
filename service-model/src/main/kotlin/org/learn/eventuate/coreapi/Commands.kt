package org.learn.eventuate.coreapi

import io.eventuate.tram.commands.common.Command

data class RequestShipmentCommand(val orderId: String = "", val productInfo: ProductInfo) : Command
