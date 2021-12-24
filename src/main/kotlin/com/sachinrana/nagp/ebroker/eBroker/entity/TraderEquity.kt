package com.sachinrana.nagp.ebroker.eBroker.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "trader_equity")
data class TraderEquity(
    @Id
    @GeneratedValue
    val id: Long? = null,

    @Column(name = "trader_id")
    val traderId: Long,

    @Column(name = "equity_id")
    val equityId: Long,

    var quantity: Int,

    @Column(name = "date_added")
    @CreationTimestamp
    val dateAdded: Date? = null,

    @UpdateTimestamp
    @Column(name = "date_modified")
    val dateModified: Date? = null
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trader_id", insertable = false, updatable = false)
    @JsonIgnoreProperties(value = ["traderEquities"], allowSetters = true)
    val trader: Trader? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equity_id", insertable = false, updatable = false)
    val equity: Equity? = null
}
