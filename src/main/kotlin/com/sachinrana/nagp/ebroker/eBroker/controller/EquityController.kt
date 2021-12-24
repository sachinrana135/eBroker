package com.sachinrana.nagp.ebroker.eBroker.controller

import com.sachinrana.nagp.ebroker.eBroker.dto.EquityDto
import com.sachinrana.nagp.ebroker.eBroker.service.EquityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/equity")
class EquityController @Autowired constructor(
    private val equityService: EquityService
) {
    @PostMapping
    fun saveEquity(
        @Valid @RequestBody equityDto: EquityDto
    ): ResponseEntity<Long> {
        return ResponseEntity.ok(equityService.addEquity(equityDto))
    }

    @GetMapping("/{id}")
    fun getEquity(@PathVariable id: Long): ResponseEntity<EquityDto> {
        return ResponseEntity.ok(equityService.getEquity(id))
    }

    @GetMapping
    fun getAllEquity(): ResponseEntity<List<EquityDto>> {
        return ResponseEntity.ok(equityService.getAllEquity())
    }

}