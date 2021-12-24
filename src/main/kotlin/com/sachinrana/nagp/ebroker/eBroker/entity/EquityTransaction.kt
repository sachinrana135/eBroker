package com.sachinrana.nagp.ebroker.eBroker.entity

import com.sachinrana.nagp.ebroker.eBroker.util.TransactionType
import org.hibernate.annotations.CreationTimestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "equity_transaction")
data class EquityTransaction(
    @Id
    @GeneratedValue
    val id: Long? = null,

    @Column(name = "equity_id")
    val equityId: Long,

    val quantity: Int,

    val price: Double,

    @Enumerated(EnumType.STRING)
    val transactionType: TransactionType,

    @Column(name = "date_added")
    @CreationTimestamp
    val dateAdded: Date? = null
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equity_id", insertable = false, updatable = false)
    val equity: Equity? = null
}
