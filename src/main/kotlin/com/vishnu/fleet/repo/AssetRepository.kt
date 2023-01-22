package com.vishnu.fleet.repo

import com.vishnu.fleet.model.Asset
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AssetRepository : JpaRepository<Asset, Long> {

    fun findByFleetId(fleetId: Long): List<Asset>
    fun findByVinAndFleetId(vin: String, fleetId: Long): Asset?
}
