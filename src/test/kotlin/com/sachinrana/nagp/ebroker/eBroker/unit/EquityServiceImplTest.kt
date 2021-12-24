package com.sachinrana.nagp.ebroker.eBroker.unit

import com.sachinrana.nagp.ebroker.eBroker.dto.EquityDto
import com.sachinrana.nagp.ebroker.eBroker.entity.Equity
import com.sachinrana.nagp.ebroker.eBroker.repository.EquityRepository
import com.sachinrana.nagp.ebroker.eBroker.service.impl.EquityServiceImpl
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*


internal class EquityServiceImplTest {

    @MockK
    lateinit var repository: EquityRepository

    @InjectMockKs
    lateinit var equityService: EquityServiceImpl

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `Should add Equity`() {

        val dto = EquityDto(
            name = "Equity",
            price = 20.0,
            quantity = 10
        )

        val returnedEntity = Equity(
            id = 1,
            name = "Equity",
            price = 20.0,
            quantity = 10
        )
        every { repository.saveAndFlush(any()) } returns returnedEntity
        equityService.addEquity(dto)
        assertEquals(1, returnedEntity.id)
        assertEquals(dto.name, returnedEntity.name)
        assertEquals(dto.price, returnedEntity.price)
        assertEquals(dto.quantity, returnedEntity.quantity)
    }

    @Test
    fun `Should get Equity`() {


        val mockEntity = Equity(
            id = 1,
            name = "Equity",
            price = 20.0,
            quantity = 10
        )
        val returnedDto = EquityDto(
            id = 1,
            name = "Equity",
            price = 20.0,
            quantity = 10
        )
        every { repository.findById(1) } returns Optional.of(mockEntity)
        equityService.getEquity(1)
        assertEquals(1, returnedDto.id)
        assertEquals(mockEntity.name, returnedDto.name)
        assertEquals(mockEntity.price, returnedDto.price)
        assertEquals(mockEntity.quantity, returnedDto.quantity)
    }

}