package com.sachinrana.nagp.ebroker.eBroker.unit

import com.sachinrana.nagp.ebroker.eBroker.dto.request.BuyEquityRequest
import com.sachinrana.nagp.ebroker.eBroker.dto.request.SellEquityRequest
import com.sachinrana.nagp.ebroker.eBroker.entity.Equity
import com.sachinrana.nagp.ebroker.eBroker.entity.EquityTransaction
import com.sachinrana.nagp.ebroker.eBroker.entity.Trader
import com.sachinrana.nagp.ebroker.eBroker.entity.TraderEquity
import com.sachinrana.nagp.ebroker.eBroker.repository.EquityRepository
import com.sachinrana.nagp.ebroker.eBroker.repository.EquityTransactionRepository
import com.sachinrana.nagp.ebroker.eBroker.repository.TraderEquityRepository
import com.sachinrana.nagp.ebroker.eBroker.repository.TraderRepository
import com.sachinrana.nagp.ebroker.eBroker.service.impl.TradingServiceImpl
import com.sachinrana.nagp.ebroker.eBroker.util.TransactionType
import com.sachinrana.nagp.ebroker.eBroker.util.Util
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*


internal class TradingServiceImplTest {

    @MockK
    lateinit var traderRepository: TraderRepository

    @MockK
    lateinit var equityRepository: EquityRepository

    @MockK
    lateinit var traderEquityRepository: TraderEquityRepository

    @MockK
    lateinit var equityTransactionRepository: EquityTransactionRepository

    @InjectMockKs
    lateinit var tradingService: TradingServiceImpl

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        mockkObject(Util)
    }

    @Test
    fun `Should not able to buy equity when fund is insufficient`() {
        val traderId = 1.toLong()
        val equityId = 1.toLong()
        every { Util.isValidTradingTime() } returns true
        every { traderRepository.findById(traderId) } returns Optional.of(
            Trader(
                id = traderId,
                name = "Equity",
                email = "sachin@gmail.com",
                mobile = "324324",
                funds = 50.0
            )
        )

        every { equityRepository.findById(equityId) } returns Optional.of(
            Equity(
                id = equityId,
                name = "Equity",
                price = 20.0,
                quantity = 10
            )
        )

        every {
            traderEquityRepository.findByTraderIdAndEquityId(
                traderId,
                equityId
            )
        } returns TraderEquity(traderId = traderId, equityId = equityId, quantity = 1)

        justRun { traderEquityRepository.saveAndFlush(any()) }
        justRun { traderRepository.saveAndFlush(any()) }
        justRun { equityRepository.saveAndFlush(any()) }
        justRun { equityTransactionRepository.saveAndFlush(any()) }

        val ex =
            assertThrows(Exception::class.java) { tradingService.buyEquity(BuyEquityRequest(traderId, equityId, 3)) }
        assertEquals("Insufficient funds", ex.message)
    }

    @Test
    fun `Should not able to buy equity quantity is not available`() {
        val traderId = 1.toLong()
        val equityId = 1.toLong()
        every { Util.isValidTradingTime() } returns true
        every { traderRepository.findById(traderId) } returns Optional.of(
            Trader(
                id = traderId,
                name = "Equity",
                email = "sachin@gmail.com",
                mobile = "324324",
                funds = 50.0
            )
        )

        every { equityRepository.findById(equityId) } returns Optional.of(
            Equity(
                id = equityId,
                name = "Equity",
                price = 20.0,
                quantity = 10
            )
        )

        every {
            traderEquityRepository.findByTraderIdAndEquityId(
                traderId,
                equityId
            )
        } returns TraderEquity(traderId = traderId, equityId = equityId, quantity = 1)

        justRun { traderEquityRepository.saveAndFlush(any()) }
        justRun { traderRepository.saveAndFlush(any()) }
        justRun { equityRepository.saveAndFlush(any()) }
        justRun { equityTransactionRepository.saveAndFlush(any()) }

        val ex =
            assertThrows(Exception::class.java) { tradingService.buyEquity(BuyEquityRequest(traderId, equityId, 11)) }
        assertEquals("11 quantity not available, current available quantity is 10 ", ex.message)
    }

    @Test
    fun `Should able to buy equity when quantity is available and trader has sufficient balance`() {
        val traderId = 1.toLong()
        val equityId = 1.toLong()
        every { Util.isValidTradingTime() } returns true
        every { traderRepository.findById(traderId) } returns Optional.of(
            Trader(
                id = traderId,
                name = "Equity",
                email = "sachin@gmail.com",
                mobile = "324324",
                funds = 50.0
            )
        )

        every { equityRepository.findById(equityId) } returns Optional.of(
            Equity(
                id = equityId,
                name = "Equity",
                price = 20.0,
                quantity = 10
            )
        )

        every {
            traderEquityRepository.findByTraderIdAndEquityId(
                traderId,
                equityId
            )
        } returns TraderEquity(traderId = traderId, equityId = equityId, quantity = 1)

        every { traderEquityRepository.saveAndFlush(any()) } returns TraderEquity(
            traderId = traderId,
            equityId = equityId,
            quantity = 2
        )
        every { traderRepository.saveAndFlush(any()) } returns Trader(
            id = traderId,
            name = "Equity",
            email = "sachin@gmail.com",
            mobile = "324324",
            funds = 30.0
        )
        every { equityRepository.saveAndFlush(any()) } returns Equity(
            id = equityId,
            name = "Equity",
            price = 20.0,
            quantity = 9
        )
        every { equityTransactionRepository.save(any()) } returns EquityTransaction(
            equityId = equityId,
            quantity = 1,
            price = 20.0,
            transactionType = TransactionType.BUY
        )

        tradingService.buyEquity(BuyEquityRequest(traderId, equityId, 1))
        verify(exactly = 1) { traderEquityRepository.saveAndFlush(any()) }
    }

    @Test
    fun `Should not able to sell equity when selling quantity is greater than trader hold`() {
        val traderId = 1.toLong()
        val equityId = 1.toLong()
        every { Util.isValidTradingTime() } returns true
        every { traderRepository.findById(traderId) } returns Optional.of(
            Trader(
                id = traderId,
                name = "Equity",
                email = "sachin@gmail.com",
                mobile = "324324",
                funds = 50.0
            )
        )

        every { equityRepository.findById(equityId) } returns Optional.of(
            Equity(
                id = equityId,
                name = "Equity",
                price = 20.0,
                quantity = 10
            )
        )
        every {
            traderEquityRepository.findByTraderIdAndEquityId(
                traderId,
                equityId
            )
        } returns TraderEquity(traderId = traderId, equityId = equityId, quantity = 1)


        val ex =
            assertThrows(Exception::class.java) { tradingService.sellEquity(SellEquityRequest(traderId, equityId, 2)) }
        assertEquals("You can not sell quantity more than you hold. ", ex.message)
    }

    @Test
    fun `Should able to selling equity when selling quantity is equal or less than trader hold`() {
        val traderId = 1.toLong()
        val equityId = 1.toLong()
        every { Util.isValidTradingTime() } returns true
        every { traderRepository.findById(traderId) } returns Optional.of(
            Trader(
                id = traderId,
                name = "Equity",
                email = "sachin@gmail.com",
                mobile = "324324",
                funds = 50.0
            )
        )

        every { equityRepository.findById(equityId) } returns Optional.of(
            Equity(
                id = equityId,
                name = "Equity",
                price = 20.0,
                quantity = 10
            )
        )

        every {
            traderEquityRepository.findByTraderIdAndEquityId(
                traderId,
                equityId
            )
        } returns TraderEquity(id = 1, traderId = traderId, equityId = equityId, quantity = 1)

        every { traderEquityRepository.deleteById(1) } returns Unit
        every { traderRepository.saveAndFlush(any()) } returns Trader(
            id = traderId,
            name = "Equity",
            email = "sachin@gmail.com",
            mobile = "324324",
            funds = 30.0
        )
        every { equityRepository.saveAndFlush(any()) } returns Equity(
            id = equityId,
            name = "Equity",
            price = 20.0,
            quantity = 9
        )
        every { equityTransactionRepository.save(any()) } returns EquityTransaction(
            equityId = equityId,
            quantity = 1,
            price = 20.0,
            transactionType = TransactionType.BUY
        )

        tradingService.sellEquity(SellEquityRequest(traderId, equityId, 1))
        verify(exactly = 1) { traderEquityRepository.findByTraderIdAndEquityId(any(), any()) }
        verify(exactly = 1) { traderEquityRepository.deleteById(any()) }
        verify(exactly = 1) { equityRepository.saveAndFlush(any()) }
        verify(exactly = 1) { equityTransactionRepository.save(any()) }
    }


}