package com.sachinrana.nagp.ebroker.eBroker.service.impl

import com.sachinrana.nagp.ebroker.eBroker.dto.TraderDto
import com.sachinrana.nagp.ebroker.eBroker.dto.TraderEquityDto
import com.sachinrana.nagp.ebroker.eBroker.dto.request.AddFundRequest
import com.sachinrana.nagp.ebroker.eBroker.entity.FundTransaction
import com.sachinrana.nagp.ebroker.eBroker.entity.Trader
import com.sachinrana.nagp.ebroker.eBroker.repository.FundTransactionRepository
import com.sachinrana.nagp.ebroker.eBroker.repository.TraderRepository
import com.sachinrana.nagp.ebroker.eBroker.service.TraderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityNotFoundException

@Service
class TraderServiceImpl @Autowired constructor(
    val traderRepository: TraderRepository,
    val fundTransactionRepository: FundTransactionRepository
) : TraderService {

    override fun addTrader(trader: TraderDto): Long? {
        val entity = Trader(name = trader.name, email = trader.email, mobile = trader.mobile)
        return traderRepository.saveAndFlush(entity).id
    }

    override fun getTrader(traderId: Long): TraderDto {
        val entity = traderRepository.findById(traderId).orElseThrow { EntityNotFoundException() }
        return TraderDto(
            id = entity.id,
            name = entity.name,
            email = entity.email,
            mobile = entity.mobile,
            fund = entity.funds
        )
    }

    override fun getAllTrader(): List<TraderDto> {
        return traderRepository.findAll().map {
            TraderDto(
                id = it.id,
                name = it.name,
                email = it.email,
                mobile = it.mobile,
                fund = it.funds
            )
        }
    }

    @Transactional
    override fun addFund(addFundRequest: AddFundRequest) {
        val entity = traderRepository.findById(addFundRequest.traderId).orElseThrow { EntityNotFoundException() }
        entity.funds += addFundRequest.fund
        traderRepository.saveAndFlush(entity)

        val fundTransaction = FundTransaction(
            traderId = addFundRequest.traderId,
            amount = addFundRequest.fund,
            fundSource = addFundRequest.fundSource
        )
        fundTransactionRepository.save(fundTransaction)
    }

    override fun getAllEquity(traderId: Long): List<TraderEquityDto>? {
        val entity = traderRepository.findById(traderId).orElseThrow { EntityNotFoundException() }
        return entity.traderEquities?.map {
            TraderEquityDto(
                traderId = it.traderId,
                equityId = it.equityId,
                quantity = it.quantity
            )
        }
    }
}