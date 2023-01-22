package com.vishnu.fleet.model

import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

@Entity
@Table(name="fleets")
data class Fleet(
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native" )
    @GenericGenerator(name = "native", strategy = "native" )
    override val id: Long? = null,
    @Column(name = "name")
    val name: String,
) : BaseEntity()
