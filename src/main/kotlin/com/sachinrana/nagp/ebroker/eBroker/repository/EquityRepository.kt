package com.sachinrana.nagp.ebroker.eBroker.repository

import com.sachinrana.nagp.ebroker.eBroker.entity.Equity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EquityRepository : JpaRepository<Equity, Long>