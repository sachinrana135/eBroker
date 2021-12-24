package com.sachinrana.nagp.ebroker.eBroker.controller

import com.sachinrana.nagp.ebroker.eBroker.dto.TraderDto
import com.sachinrana.nagp.ebroker.eBroker.dto.TraderEquityDto
import com.sachinrana.nagp.ebroker.eBroker.dto.request.AddFundRequest
import com.sachinrana.nagp.ebroker.eBroker.service.TraderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/trader")
class TraderController @Autowired constructor(
    private val traderService: TraderService
) {
    @PostMapping
    fun saveTrader(
        @Valid @RequestBody trader: TraderDto
    ): ResponseEntity<Long> {
        return ResponseEntity.ok(traderService.addTrader(trader))
    }

    @GetMapping("/{id}")
    fun getTrader(@PathVariable id: Long): ResponseEntity<TraderDto> {
        return ResponseEntity.ok(traderService.getTrader(id))
    }

    @GetMapping
    fun getAllTrader(): ResponseEntity<List<TraderDto>> {
        return ResponseEntity.ok(traderService.getAllTrader())
    }

    @PostMapping("/addFund")
    fun addFund(
        @Valid @RequestBody fundRequest: AddFundRequest
    ): ResponseEntity<Unit> {
        return ResponseEntity.ok(traderService.addFund(fundRequest))
    }

    @GetMapping("/{id}/equity")
    fun getAllEquity(@PathVariable id: Long): ResponseEntity<List<TraderEquityDto>> {
        return ResponseEntity.ok(traderService.getAllEquity(id))
    }

}