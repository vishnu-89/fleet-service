package com.vishnu.fleet.service

import com.vishnu.fleet.NotFoundExecption
import com.vishnu.fleet.model.Asset
import com.vishnu.fleet.repo.AssetRepository
import org.springframework.stereotype.Service

@Service
class AssetService(
    val assetRepository: AssetRepository,
) {

    fun deleteAsssetOfAFleet(assetId: Long) {
        val asset = assetRepository.findById(assetId)
        if (!asset.isPresent) {
            throw NotFoundExecption("The asset is not present")
        }
        assetRepository.deleteById(assetId)
    }

    fun searchAssetByVin(vin: String, fleetId: Long): Asset? {
        return assetRepository.findByVinAndFleetId(vin, fleetId)
    }
}
