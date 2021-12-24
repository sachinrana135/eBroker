package com.sachinrana.nagp.ebroker.eBroker.dto.request

data class BuyEquityRequest(
    val traderId: Long,
    val equityId: Long,
    val quantity: Int
)
