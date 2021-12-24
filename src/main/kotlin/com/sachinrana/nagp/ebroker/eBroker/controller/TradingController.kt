package com.sachinrana.nagp.ebroker.eBroker.controller

import com.sachinrana.nagp.ebroker.eBroker.dto.request.BuyEquityRequest
import com.sachinrana.nagp.ebroker.eBroker.dto.request.SellEquityRequest
import com.sachinrana.nagp.ebroker.eBroker.service.TradingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/trading")
class TradingController @Autowired constructor(
    private val tradingService: TradingService
) {
    @PostMapping("/buy")
    fun buyEquity(
        @Valid @RequestBody buyEquityRequest: BuyEquityRequest
    ): ResponseEntity<Unit> {
        return ResponseEntity.ok(tradingService.buyEquity(buyEquityRequest))
    }

    @PostMapping("/sell")
    fun sellEquity(
        @Valid @RequestBody sellEquityRequest: SellEquityRequest
    ): ResponseEntity<Unit> {
        return ResponseEntity.ok(tradingService.sellEquity(sellEquityRequest))
    }


}