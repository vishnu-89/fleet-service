package com.vishnu.fleet.repo

import com.vishnu.fleet.model.Fleet
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FleetRepository : JpaRepository<Fleet,Long>
