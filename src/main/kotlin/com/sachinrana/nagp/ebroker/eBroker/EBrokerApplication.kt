package com.sachinrana.nagp.ebroker.eBroker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableTransactionManagement
class EBrokerApplication

fun main(args: Array<String>) {
    runApplication<EBrokerApplication>(*args)
}
