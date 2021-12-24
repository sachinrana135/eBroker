package com.sachinrana.nagp.ebroker.eBroker.unit

import com.sachinrana.nagp.ebroker.eBroker.dto.TraderDto
import com.sachinrana.nagp.ebroker.eBroker.entity.Trader
import com.sachinrana.nagp.ebroker.eBroker.repository.FundTransactionRepository
import com.sachinrana.nagp.ebroker.eBroker.repository.TraderRepository
import com.sachinrana.nagp.ebroker.eBroker.service.impl.TraderServiceImpl
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*
import javax.persistence.EntityNotFoundException


internal class TraderServiceImplTest {

    @MockK
    lateinit var traderRepository: TraderRepository

    @MockK
    lateinit var fundTransactionRepository: FundTransactionRepository


    @InjectMockKs
    lateinit var traderService: TraderServiceImpl

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `Should add Trader`() {

        val dto = TraderDto(
            name = "Equity",
            email = "sachin@gmail.com",
            mobile = "324324"
        )

        val mockEntity = Trader(
            id = 1,
            name = "Equity",
            email = "sachin@gmail.com",
            mobile = "324324"
        )
        every { traderRepository.saveAndFlush(any()) } returns mockEntity
        traderService.addTrader(dto)
        assertEquals(1, mockEntity.id)
        assertEquals(dto.name, mockEntity.name)
        assertEquals(dto.mobile, mockEntity.mobile)
        assertEquals(dto.email, mockEntity.email)
    }

    @Test
    fun `Should fail get Trader when trader not exit`() {
        every { traderRepository.findById(1) } throws EntityNotFoundException()
        val ex = assertThrows(EntityNotFoundException::class.java) { traderService.getTrader(1) }

    }

    @Test
    fun `Should get Trader`() {
        val mockEntity = Trader(
            id = 1,
            name = "Equity",
            email = "sachin@gmail.com",
            mobile = "324324"
        )
        val returnedDto = TraderDto(
            id = 1,
            name = "Equity",
            email = "sachin@gmail.com",
            mobile = "324324"
        )
        every { traderRepository.findById(1) } returns Optional.of(mockEntity)
        traderService.getTrader(1)
        assertEquals(1, returnedDto.id)
        assertEquals(mockEntity.name, returnedDto.name)
        assertEquals(mockEntity.email, returnedDto.email)
        assertEquals(mockEntity.mobile, returnedDto.mobile)
    }

}