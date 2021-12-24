package com.sachinrana.nagp.ebroker.eBroker.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

@JsonInclude(JsonInclude.Include.NON_NULL)
data class TraderDto(

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val id: Long? = null,

    @field:NotNull
    @field:NotBlank
    val name: String,

    @field:NotNull
    @field:NotBlank
    @field:Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\$", message = "Invalid email")
    val email: String,

    @field:NotNull
    @field:NotBlank
    val mobile: String,

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val fund: Double = 0.0
)
