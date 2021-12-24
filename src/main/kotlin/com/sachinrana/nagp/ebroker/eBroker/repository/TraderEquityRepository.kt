package com.sachinrana.nagp.ebroker.eBroker.repository

import com.sachinrana.nagp.ebroker.eBroker.entity.TraderEquity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface TraderEquityRepository : JpaRepository<TraderEquity, Long> {

    fun findByTraderIdAndEquityId(traderId: Long, equityId: Long): TraderEquity?

}