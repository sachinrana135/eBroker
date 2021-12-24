package com.sachinrana.nagp.ebroker.eBroker.dto

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class EquityDto(

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val id: Long? = 0,

    @field:NotNull
    @field:NotBlank
    val name: String,
    @field:NotNull

    val quantity: Long,
    @field:NotNull
    val price: Double
)
