package com.vishnu.fleet.service

import com.vishnu.fleet.NotFoundExecption
import com.vishnu.fleet.controller.FleetRequest
import com.vishnu.fleet.model.Asset
import com.vishnu.fleet.model.AssetRequest
import com.vishnu.fleet.model.Fleet
import com.vishnu.fleet.repo.AssetRepository
import com.vishnu.fleet.repo.FleetRepository
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class FleetService(
    val fleetRepository: FleetRepository,
    val assetRepository: AssetRepository,
) {

    fun createFleet(fleetRequest: FleetRequest): Fleet {
        val time = Instant.now()
        return fleetRepository.save(Fleet(name = fleetRequest.name).apply {
            this.updatedBy = UUID.randomUUID().toString()
            this.createdAt = time
            this.updatedAt = time
        })
    }

    fun createAssetsToFleet(assetRequest: AssetRequest, fleetId: Long): Asset {
        val fleet = fleetRepository.findById(fleetId)
        if (!fleet.isPresent) {
            throw NotFoundExecption("The Fleet is not found")
        }
        val time = Instant.now()
        return assetRepository.save(
            Asset(
                name = assetRequest.name,
                vin = assetRequest.vin,
                fleet = fleet.get()
            ).apply {
                this.updatedBy = UUID.randomUUID().toString()
                this.createdAt = time
                this.updatedAt = time
            })
    }

    fun fetchAllAsssetsOfAFleet(fleetId: Long): List<Asset> {
        val fleet = fleetRepository.findById(fleetId)
        if (!fleet.isPresent) {
            throw NotFoundExecption("The Fleet is not found")
        }

        return assetRepository.findByFleetId(fleetId)
    }

    fun fetchFleetById(fleetId: Long): Fleet {
        val fleet = fleetRepository.findById(fleetId)

        if (!fleet.isPresent) {
            throw NotFoundExecption("The given Fleet is not present.")
        }
        return fleet.get()
    }

    fun deleteFleet(fleetId: Long) {
        val fleet = fleetRepository.findById(fleetId)

        if (!fleet.isPresent) {
            throw NotFoundExecption("The given Fleet is not present.")
        }
        fleetRepository.delete(fleet.get())
    }

}
