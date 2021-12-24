package com.sachinrana.nagp.ebroker.eBroker.service.impl

import com.sachinrana.nagp.ebroker.eBroker.dto.EquityDto
import com.sachinrana.nagp.ebroker.eBroker.entity.Equity
import com.sachinrana.nagp.ebroker.eBroker.repository.EquityRepository
import com.sachinrana.nagp.ebroker.eBroker.service.EquityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class EquityServiceImpl @Autowired constructor(val repository: EquityRepository) : EquityService {
    override fun addEquity(equity: EquityDto): Long? {
        val equityEntity = Equity(
            name = equity.name,
            price = equity.price,
            quantity = equity.quantity
        )
        return repository.saveAndFlush(equityEntity).id
    }

    override fun getEquity(equtyId: Long): EquityDto {
        val entity = repository.findById(equtyId).orElseThrow { EntityNotFoundException() }
        return EquityDto(id = entity.id, name = entity.name, price = entity.price, quantity = entity.quantity)
    }

    override fun getAllEquity(): List<EquityDto> {
        return repository.findAll().map {
            EquityDto(
                id = it.id,
                name = it.name,
                price = it.price,
                quantity = it.quantity
            )
        }
    }
}