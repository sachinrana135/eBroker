package com.sachinrana.nagp.ebroker.eBroker.service

import com.sachinrana.nagp.ebroker.eBroker.entity.FundTransaction

interface FundTransactionService {

    fun addTransaction(fundTransaction: FundTransaction)

}