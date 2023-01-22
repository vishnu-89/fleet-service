package com.vishnu.fleet.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*


@Entity
@Table(name = "assets")
data class Asset(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    override val id: Long? = null,
    @Column(name = "name")
    val name: String,

    @Column(name = "vin")
    val vin: String,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "fleet_id",
        referencedColumnName = "id",
        nullable = false
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    val fleet: Fleet
) : BaseEntity()
