package com.vishnu.fleet.model

import java.time.Instant
import javax.persistence.Column
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class BaseEntity {

    abstract val id: Long?

    var updatedAt: Instant? = null

    @Column(updatable = false)
    var createdAt: Instant? = null

    @Column(updatable = false)
    private var createdBy: String? = null

    var updatedBy: String? = null
}
