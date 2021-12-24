package com.sachinrana.nagp.ebroker.eBroker.service

import com.sachinrana.nagp.ebroker.eBroker.dto.request.BuyEquityRequest
import com.sachinrana.nagp.ebroker.eBroker.dto.request.SellEquityRequest


interface TradingService {
    fun buyEquity(buyEquityRequest: BuyEquityRequest)
    fun sellEquity(sellEquityRequest: SellEquityRequest)
}