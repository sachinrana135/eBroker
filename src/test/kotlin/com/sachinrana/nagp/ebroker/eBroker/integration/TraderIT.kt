package com.sachinrana.nagp.ebroker.eBroker.integration

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.sachinrana.nagp.ebroker.eBroker.dto.TraderDto
import com.sachinrana.nagp.ebroker.eBroker.dto.request.AddFundRequest
import com.sachinrana.nagp.ebroker.eBroker.util.FundSource
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
class TraderIT(@Autowired val restTemplate: TestRestTemplate) {

    companion object {
        var traderId: Long = 0
    }

    @Test
    @Order(1)
    fun `Should not add trader when name is blank`() {
        val dto = TraderDto(
            name = "",
            email = "abc@example.com",
            mobile = "123456"
        )
        val requestBody = jacksonObjectMapper().writeValueAsString(dto)
        val headers = HttpHeaders()
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        val request = HttpEntity(requestBody, headers)
        val response = restTemplate.postForEntity("/trader", request, Long::class.java)
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }

    @Test
    @Order(3)
    fun `Should not add trader when email is blank`() {
        val dto = TraderDto(
            name = "Trader 1",
            email = "",
            mobile = "123456"
        )
        val requestBody = jacksonObjectMapper().writeValueAsString(dto)
        val headers = HttpHeaders()
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        val request = HttpEntity(requestBody, headers)
        val response = restTemplate.postForEntity("/trader", request, Long::class.java)
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }

    @Test
    @Order(5)
    fun `Should not add trader when email is not valid`() {
        val dto = TraderDto(
            name = "Trader 1",
            email = "abc.com",
            mobile = "123456"
        )
        val requestBody = jacksonObjectMapper().writeValueAsString(dto)
        val headers = HttpHeaders()
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        val request = HttpEntity(requestBody, headers)
        val response = restTemplate.postForEntity("/trader", request, Long::class.java)
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }

    @Test
    @Order(8)
    fun `Should not add trader when mobile is blank`() {
        val dto = TraderDto(
            name = "Trader 1",
            email = "abc@example.com",
            mobile = ""
        )
        val requestBody = jacksonObjectMapper().writeValueAsString(dto)
        val headers = HttpHeaders()
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        val request = HttpEntity(requestBody, headers)
        val response = restTemplate.postForEntity("/trader", request, Long::class.java)
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }

    @Test
    @Order(10)
    fun `Should add trader when request data is valid`() {
        val dto = TraderDto(
            name = "Trader 1",
            email = "abc@example.com",
            mobile = "123456"
        )
        val requestBody = jacksonObjectMapper().writeValueAsString(dto)
        val headers = HttpHeaders()
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        val request = HttpEntity(requestBody, headers)
        val response = restTemplate.postForEntity("/trader", request, Long::class.java)
        assertEquals(HttpStatus.OK, response.statusCode)
        traderId = response.body!!

    }

    @Test
    @Order(11)
    fun `Should get the trader and data should match`() {
        // fetch the added trader
        val dto = TraderDto(
            name = "Trader 1",
            email = "abc@example.com",
            mobile = "123456"
        )
        val traderResponse = restTemplate.getForEntity("/trader/${traderId}", TraderDto::class.java)
        val trader = traderResponse.body
        assertEquals(HttpStatus.OK, traderResponse.statusCode)
        assertEquals(dto.name, trader?.name)
        assertEquals(dto.email, trader?.email)
        assertEquals(dto.mobile, trader?.mobile)
    }

    @Test
    @Order(12)
    fun `Should fetch the list of all users`() {
        val tradersResponse = restTemplate.getForEntity("/trader", List::class.java)
        val traders = tradersResponse.body
        assertEquals(HttpStatus.OK, tradersResponse.statusCode)
        assertEquals(1, traders?.size)
    }

    @Test
    @Order(14)
    fun `Should add fund to trader when request is valid `() {

        val dto = AddFundRequest(
            traderId = traderId,
            fund = 100.0,
            fundSource = FundSource.ONLINE_BANKING
        )
        val requestBody = jacksonObjectMapper().writeValueAsString(dto)
        val headers = HttpHeaders()
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        val request = HttpEntity(requestBody, headers)
        val response = restTemplate.postForEntity("/trader/addFund", request, Unit::class.java)
        assertEquals(HttpStatus.OK, response.statusCode)

    }

}