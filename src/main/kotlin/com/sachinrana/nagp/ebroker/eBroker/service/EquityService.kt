package com.sachinrana.nagp.ebroker.eBroker.service

import com.sachinrana.nagp.ebroker.eBroker.dto.EquityDto

interface EquityService {

    fun addEquity(equity: EquityDto): Long?

    fun getEquity(equtyId: Long): EquityDto

    fun getAllEquity(): List<EquityDto>
}