package com.sachinrana.nagp.ebroker.eBroker.repository

import com.sachinrana.nagp.ebroker.eBroker.entity.EquityTransaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EquityTransactionRepository : JpaRepository<EquityTransaction, Long>