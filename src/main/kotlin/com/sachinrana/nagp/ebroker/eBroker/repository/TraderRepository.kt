package com.sachinrana.nagp.ebroker.eBroker.repository

import com.sachinrana.nagp.ebroker.eBroker.entity.Trader
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TraderRepository : JpaRepository<Trader, Long>