package com.sachinrana.nagp.ebroker.eBroker.repository

import com.sachinrana.nagp.ebroker.eBroker.entity.FundTransaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FundTransactionRepository : JpaRepository<FundTransaction, Long>