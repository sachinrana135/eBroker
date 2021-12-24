package com.sachinrana.nagp.ebroker.eBroker.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "trader")
data class Trader(
    @Id
    @GeneratedValue
    val id: Long? = null,

    var name: String,

    @Column(unique = true)
    var mobile: String,

    @Column(unique = true)
    var email: String,

    var funds: Double = 0.0,

    @Column(name = "date_added")
    @CreationTimestamp
    val dateAdded: Date? = null,

    @Column(name = "date_modified")
    @UpdateTimestamp
    val dateModified: Date? = null
) {
    @OneToMany(mappedBy = "trader")
    @JsonIgnoreProperties(value = ["trader"], allowSetters = true)
    val traderEquities: Set<TraderEquity>? = null
}