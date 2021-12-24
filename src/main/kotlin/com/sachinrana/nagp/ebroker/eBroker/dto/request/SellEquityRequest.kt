package com.sachinrana.nagp.ebroker.eBroker.dto.request

data class SellEquityRequest(
    val traderId: Long,
    val equityId: Long,
    val quantity: Int
)
