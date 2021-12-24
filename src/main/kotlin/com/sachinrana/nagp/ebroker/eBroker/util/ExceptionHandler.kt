package com.sachinrana.nagp.ebroker.eBroker.util

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.persistence.EntityNotFoundException

@ControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(EntityNotFoundException::class)
    protected fun handleEntityNotFoundException(ex: EntityNotFoundException): ResponseEntity<Any?>? {
        ex.printStackTrace()
        val response = ex.message
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response)
    }

    // 500
    @ExceptionHandler(Exception::class)
    protected fun defaultExceptionHandler(ex: Exception): ResponseEntity<Any?>? {
        ex.printStackTrace()
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.message)
    }


}
