package com.sachinrana.nagp.ebroker.eBroker.integration

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.sachinrana.nagp.ebroker.eBroker.dto.EquityDto
import com.sachinrana.nagp.ebroker.eBroker.dto.TraderDto
import com.sachinrana.nagp.ebroker.eBroker.dto.request.AddFundRequest
import com.sachinrana.nagp.ebroker.eBroker.dto.request.BuyEquityRequest
import com.sachinrana.nagp.ebroker.eBroker.util.FundSource
import junit.framework.Assert.assertEquals
import org.junit.jupiter.api.*
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
class TradingIT(@Autowired val restTemplate: TestRestTemplate) {

    companion object {
        var traderId: Long = 0
        var equityId: Long = 0
    }

    @Test
    @Order(1)
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
    @Order(3)
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
    @Order(5)
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

    @Test
    @Order(7)
    fun `Should not able to buy the equity when balance is not sufficient`() {
        // buying equity more than available fund
        val buyEquityDto = BuyEquityRequest(
            traderId = traderId,
            equityId = equityId,
            quantity = 6
        )
        val buyEquityRequestBody = jacksonObjectMapper().writeValueAsString(buyEquityDto)
        val headers = HttpHeaders()
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        val buyEquityRequest = HttpEntity(buyEquityRequestBody, headers)
        assertThrows<Exception> {
            restTemplate.postForEntity("/trading/buy", buyEquityRequest, Unit::class.java)
        }
    }

    @Test
    @Order(9)
    fun `Should not able to buy the equity when equity quantity is not available`() {
        // buying equity more than available fund
        val buyEquityDto = BuyEquityRequest(
            traderId = traderId,
            equityId = equityId,
            quantity = 11
        )
        val buyEquityRequestBody = jacksonObjectMapper().writeValueAsString(buyEquityDto)
        val headers = HttpHeaders()
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        val buyEquityRequest = HttpEntity(buyEquityRequestBody, headers)
        assertThrows<Exception> {
            restTemplate.postForEntity("/trading/buy", buyEquityRequest, Unit::class.java)
        }
    }

    @Test
    @Order(11)
    fun `Should able to buy the equity when equity is in stock and fund is available`() {

        val buyEquityDto = BuyEquityRequest(
            traderId = traderId,
            equityId = equityId,
            quantity = 3
        )
        val buyEquityRequestBody = jacksonObjectMapper().writeValueAsString(buyEquityDto)
        val headers = HttpHeaders()
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        val buyEquityRequest = HttpEntity(buyEquityRequestBody, headers)

        var buyEquityResponse = restTemplate.postForEntity("/trading/buy", buyEquityRequest, Unit::class.java)
        assertEquals(HttpStatus.OK, buyEquityResponse.statusCode)

    }

    @Test
    @Order(13)
    fun `Should able to get trader allocated equities`() {
        val traderEquityResponse = restTemplate.getForEntity("/trader/${traderId}/equity", List::class.java)
        val traderEquities = traderEquityResponse.body
        assertEquals(HttpStatus.OK, traderEquityResponse.statusCode)
        assertEquals(1, traderEquities?.size)

    }

    @Test
    @Order(15)
    fun `Should not able to sell the equity when selling quantity is greater than trader hold`() {

        // selling equity more than trader holds
        val sellEquityDto = BuyEquityRequest(
            traderId = traderId,
            equityId = equityId,
            quantity = 4
        )
        val sellEquityRequestBody = jacksonObjectMapper().writeValueAsString(sellEquityDto)
        val headers = HttpHeaders()
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        val sellEquityRequest = HttpEntity(sellEquityRequestBody, headers)

        assertThrows<Exception> {
            restTemplate.postForEntity("/trading/sell", sellEquityRequest, Unit::class.java)
        }

    }

    @Test
    @Order(17)
    fun `Should able to sell the equity when selling quantity is less than trader hold`() {

        val sellEquityDto = BuyEquityRequest(
            traderId = traderId,
            equityId = equityId,
            quantity = 2
        )
        val sellEquityRequestBody = jacksonObjectMapper().writeValueAsString(sellEquityDto)

        val headers = HttpHeaders()
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        val sellEquityRequest = HttpEntity(sellEquityRequestBody, headers)

        val sellEquityResponse = restTemplate.postForEntity("/trading/sell", sellEquityRequest, Unit::class.java)

        assertEquals(HttpStatus.OK, sellEquityResponse.statusCode)

    }

}