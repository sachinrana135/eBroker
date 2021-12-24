package com.sachinrana.nagp.ebroker.eBroker.dto.request

import com.sachinrana.nagp.ebroker.eBroker.util.FundSource

data class AddFundRequest(
    val traderId: Long,
    val fund: Double,
    val fundSource: FundSource
)
