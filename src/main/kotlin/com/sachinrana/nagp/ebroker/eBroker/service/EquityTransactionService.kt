package com.sachinrana.nagp.ebroker.eBroker.service

import com.sachinrana.nagp.ebroker.eBroker.entity.EquityTransaction

interface EquityTransactionService {

    fun saveTransaction(equityTransaction: EquityTransaction)
}