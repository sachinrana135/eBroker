package com.sachinrana.nagp.ebroker.eBroker.entity

import com.sachinrana.nagp.ebroker.eBroker.util.FundSource
import org.hibernate.annotations.CreationTimestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "fund_transaction")
data class FundTransaction(
    @Id
    @GeneratedValue
    val id: Long? = null,

    @Column(name = "trader_id")
    val traderId: Long,

    @Column
    val amount: Double,

    @Column
    @Enumerated(EnumType.STRING)
    val fundSource: FundSource,

    @Column(name = "date_added")
    @CreationTimestamp
    val dateAdded: Date? = null
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trader_id", insertable = false, updatable = false)
    val trader: Trader? = null
}
