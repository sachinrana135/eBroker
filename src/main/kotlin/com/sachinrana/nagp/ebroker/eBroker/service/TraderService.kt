package com.sachinrana.nagp.ebroker.eBroker.service

import com.sachinrana.nagp.ebroker.eBroker.dto.TraderDto
import com.sachinrana.nagp.ebroker.eBroker.dto.TraderEquityDto
import com.sachinrana.nagp.ebroker.eBroker.dto.request.AddFundRequest


interface TraderService {

    fun addTrader(trader: TraderDto): Long?

    fun getTrader(traderId: Long): TraderDto

    fun getAllTrader(): List<TraderDto>

    fun addFund(addFundRequest: AddFundRequest)

    fun getAllEquity(traderId: Long): List<TraderEquityDto>?

}