package com.vishnu.fleet.controller

import com.vishnu.fleet.NotFoundExecption
import com.vishnu.fleet.model.Asset
import com.vishnu.fleet.model.Fleet
import com.vishnu.fleet.service.AssetService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping("v1/assets")
class AssetController(val assetService: AssetService) {

    /**
     * @author Vishnu
     * Remove an asset from a fleet
     */
    @ApiOperation(value = "Remove asset in a fleet", response = Fleet::class)
    @ApiResponses(
        value = *arrayOf(
            ApiResponse(code = 204, message = "NO-CONTENT"),
            ApiResponse(code = 404, message = "The resource not found")
        )
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{assetId}")
    fun removeAsssetsOfAFleet(
        @PathVariable assetId: Long
    ) {
        assetService.deleteAsssetOfAFleet(assetId)
    }

    /**
     * @author Vishnu
     * Search for an asset by its VIN
     */
    @ApiOperation(value = "Find asset by VIN", response = Fleet::class)
    @ApiResponses(
        value = *arrayOf(
            ApiResponse(code = 200, message = "OK"),
            ApiResponse(code = 404, message = "The resource not found")
        )
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{vin}")
    fun findAssetByVin(
        @PathVariable vin: String,
        @RequestParam fleetId : Long
    ): Asset? {
        val asset =  assetService.searchAssetByVin(vin, fleetId)

        if(asset == null) {
           throw NotFoundExecption("Given vin for a fleet is not present")
        }
        return asset
    }

}
