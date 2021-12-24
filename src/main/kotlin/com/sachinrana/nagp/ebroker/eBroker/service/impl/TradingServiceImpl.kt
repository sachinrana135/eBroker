package com.sachinrana.nagp.ebroker.eBroker.service.impl

import com.sachinrana.nagp.ebroker.eBroker.dto.request.BuyEquityRequest
import com.sachinrana.nagp.ebroker.eBroker.dto.request.SellEquityRequest
import com.sachinrana.nagp.ebroker.eBroker.entity.EquityTransaction
import com.sachinrana.nagp.ebroker.eBroker.entity.TraderEquity
import com.sachinrana.nagp.ebroker.eBroker.repository.EquityRepository
import com.sachinrana.nagp.ebroker.eBroker.repository.EquityTransactionRepository
import com.sachinrana.nagp.ebroker.eBroker.repository.TraderEquityRepository
import com.sachinrana.nagp.ebroker.eBroker.repository.TraderRepository
import com.sachinrana.nagp.ebroker.eBroker.service.TradingService
import com.sachinrana.nagp.ebroker.eBroker.util.TransactionType
import com.sachinrana.nagp.ebroker.eBroker.util.Util
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityNotFoundException

@Service
class TradingServiceImpl @Autowired constructor(
    private val traderEquityRepository: TraderEquityRepository,
    private val traderRepository: TraderRepository,
    private val equityRepository: EquityRepository,
    private val equityTransactionRepository: EquityTransactionRepository
) : TradingService {

    @Transactional
    override fun buyEquity(buyEquityRequest: BuyEquityRequest) {

        //Should be able to buy from 9 to 5 and Monday to Friday only
        if (!Util.isValidTradingTime()) throw Exception("Invalid trading day or time")

        val trader = traderRepository.findById(buyEquityRequest.traderId).orElseThrow { EntityNotFoundException() }
        val equity = equityRepository.findById(buyEquityRequest.equityId).orElseThrow { EntityNotFoundException() }

        //checking if equity quantity is available
        if (equity.quantity < buyEquityRequest.quantity) throw Exception("${buyEquityRequest.quantity} quantity not available, current available quantity is ${equity.quantity} ")

        //checking if trader has suitable funds to buy the equity
        if (trader.funds < equity.price * buyEquityRequest.quantity) throw Exception("Insufficient funds")

        var entity = traderEquityRepository.findByTraderIdAndEquityId(traderId = trader.id!!, equityId = equity.id!!)

        if (entity == null) {
            entity = TraderEquity(traderId = trader.id, equityId = equity.id, quantity = buyEquityRequest.quantity)
        } else {
            // Allocation new quantity to trader
            entity.quantity += buyEquityRequest.quantity
        }
        traderEquityRepository.saveAndFlush(entity)

        // Deducting the amount from trader funds
        trader.funds -= equity.price * buyEquityRequest.quantity
        traderRepository.saveAndFlush(trader)

        //reducing the equity quantity
        equity.quantity -= buyEquityRequest.quantity
        equityRepository.saveAndFlush(equity)

        //logging equity transaction
        val et = EquityTransaction(
            equityId = equity.id,
            quantity = buyEquityRequest.quantity,
            price = equity.price,
            transactionType = TransactionType.BUY
        )
        equityTransactionRepository.save(et)


    }

    override fun sellEquity(sellEquityRequest: SellEquityRequest) {

        //Should be able to buy from 9 to 5 and Monday to Friday only
        if (!Util.isValidTradingTime()) throw Exception("Invalid trading day or time")

        val trader = traderRepository.findById(sellEquityRequest.traderId).orElseThrow { EntityNotFoundException() }
        val equity = equityRepository.findById(sellEquityRequest.equityId).orElseThrow { EntityNotFoundException() }
        val traderEquity = traderEquityRepository.findByTraderIdAndEquityId(
            traderId = sellEquityRequest.traderId,
            equityId = sellEquityRequest.equityId
        ) ?: throw EntityNotFoundException()

        when {
            // if trader is selling  more quantity than he/she holds
            sellEquityRequest.quantity > traderEquity.quantity -> {
                throw Exception("You can not sell quantity more than you hold. ")
            }
            // if trader is selling less quantity than he/she holds
            sellEquityRequest.quantity < traderEquity.quantity -> {
                traderEquity.quantity -= sellEquityRequest.quantity
                traderEquityRepository.saveAndFlush(traderEquity)
            }
            // if trader is selling  equal quantity  he/she holds
            sellEquityRequest.quantity == traderEquity.quantity -> {
                traderEquityRepository.deleteById(traderEquity.id!!)
            }
        }

        //adding funds to trader
        trader.funds += sellEquityRequest.quantity * equity.price
        traderRepository.saveAndFlush(trader)

        //increasing equtity quantity
        equity.quantity += sellEquityRequest.quantity
        equityRepository.saveAndFlush(equity)

        // Logging the transaction
        val et = EquityTransaction(
            equityId = equity.id!!,
            quantity = sellEquityRequest.quantity,
            price = equity.price,
            transactionType = TransactionType.SELL
        )
        equityTransactionRepository.save(et)

    }
}