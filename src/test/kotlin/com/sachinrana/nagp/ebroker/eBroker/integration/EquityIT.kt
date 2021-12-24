package com.sachinrana.nagp.ebroker.eBroker.integration

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.sachinrana.nagp.ebroker.eBroker.dto.EquityDto
import junit.framework.Assert.assertEquals
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class EquityIT(@Autowired val restTemplate: TestRestTemplate) {

    companion object {
        var equityId: Long = 0
    }

    @Test
    @Order(1)
    fun `Should not add equity when name is blank`() {
        val dto = EquityDto(
            name = "",
            price = 20.0,
            quantity = 10
        )
        val requestBody = jacksonObjectMapper().writeValueAsString(dto)
        val headers = HttpHeaders()
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        val request = HttpEntity(requestBody, headers)
        val response = restTemplate.postForEntity("/equity", request, Long::class.java)
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }

    @Test
    @Order(10)
    fun `Should add equity when request data is valid`() {
        val dto = EquityDto(
            name = "Equity 1",
            price = 20.0,
            quantity = 10
        )
        val requestBody = jacksonObjectMapper().writeValueAsString(dto)
        val headers = HttpHeaders()
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        val request = HttpEntity(requestBody, headers)
        val response = restTemplate.postForEntity("/equity", request, Long::class.java)
        assertEquals(HttpStatus.OK, response.statusCode)
        equityId = response.body!!
    }

    @Test
    @Order(11)
    fun `Should get the equity and data should match`() {
        // fetch the added equity
        val dto = EquityDto(
            name = "Equity 1",
            price = 20.0,
            quantity = 10
        )
        val equityResponse = restTemplate.getForEntity("/equity/${equityId}", EquityDto::class.java)
        val equity = equityResponse.body
        assertEquals(HttpStatus.OK, equityResponse.statusCode)
        assertEquals(dto.name, equity?.name)
        assertEquals(dto.price, equity?.price)
        assertEquals(dto.quantity, equity?.quantity)
    }

    @Test
    @Order(12)
    fun `Should fetch the list of all equity`() {
        val equityResponse = restTemplate.getForEntity("/equity", List::class.java)
        val equity = equityResponse.body
        assertEquals(HttpStatus.OK, equityResponse.statusCode)
        assertEquals(1, equity?.size)
    }
}