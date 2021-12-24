package com.sachinrana.nagp.ebroker.eBroker.entity

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "equity")
data class Equity(
    @Id
    @GeneratedValue
    val id: Long? = null,

    var name: String,

    var quantity: Long,

    var price: Double,

    @Column(name = "date_added")
    @CreationTimestamp
    val dateAdded: Date? = null,

    @Column(name = "date_modified")
    @UpdateTimestamp
    val dateModified: Date? = null
) {
    @OneToMany(mappedBy = "equity")
    val traderEquities: Set<TraderEquity>? = null
}
