package com.vishnu.fleet.controller

import com.vishnu.fleet.model.Asset
import com.vishnu.fleet.model.AssetRequest
import com.vishnu.fleet.model.Fleet
import com.vishnu.fleet.service.FleetService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@RestController
@RequestMapping("v1/fleets")
@Api(description = "Rest API for fleets operation", tags = arrayOf("Fleet API"))
class FleetController(val fleetService: FleetService) {

    /**
     * @author Vishnu
     * Create a fleet
     */
    @ApiOperation(value = "Create a fleet", response = Fleet::class)
    @ApiResponses(
        value = *arrayOf(
            ApiResponse(code = 201, message = "CREATED"),
            ApiResponse(code = 404, message = "The resource not found")
        )
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun addFleet(@RequestBody @Valid fleet: FleetRequest): Fleet {
        return fleetService.createFleet(fleet)
    }

    /**
     * @author Vishnu
     * Find fleet by its ID
     */
    @ApiOperation(value = "Get a fleet by ID", response = Fleet::class)
    @ApiResponses(
        value = *arrayOf(
            ApiResponse(code = 200, message = "OK"),
            ApiResponse(code = 404, message = "The resource not found")
        )
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{fleetId}")
    fun getFleetById(
        @PathVariable fleetId: Long
    ): Fleet {
        return fleetService.fetchFleetById(fleetId)
    }

    /**
     * @author Vishnu
     * Add an asset to a fleet
     */
    @ApiOperation(value = "Add an asset to the fleet", response = Asset::class)
    @ApiResponses(
        value = *arrayOf(
            ApiResponse(code = 201, message = "CREATED"),
            ApiResponse(code = 404, message = "The resource not found")
        )
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{fleetId}/assets")
    fun addAsssetsToFleet(
        @RequestBody @Valid asset: AssetRequest,
        @PathVariable fleetId: Long
    ): Asset {
        return fleetService.createAssetsToFleet(asset, fleetId)
    }

    /**
     * @author Vishnu
     * Get all assets in a fleet
     */
    @ApiOperation(value = "Get all assets of the fleet")
    @ApiResponses(
        value = *arrayOf(
            ApiResponse(code = 201, message = "CREATED"),
            ApiResponse(code = 404, message = "The resource not found")
        )
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{fleetId}/assets")
    fun getAllAsssetsOfAFleet(
        @PathVariable fleetId: Long
    ): List<Asset> {
        return fleetService.fetchAllAsssetsOfAFleet(fleetId)
    }

    /**
     * @author Vishnu
     * Remove fleet and all of its assets
     */
    @ApiOperation(value = "Delete fleet by ID")
    @ApiResponses(
        value = *arrayOf(
            ApiResponse(code = 201, message = "CREATED"),
            ApiResponse(code = 404, message = "The resource not found")
        )
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{fleetId}")
    fun deleteAFleet(
        @PathVariable fleetId: Long
    ) {
        fleetService.deleteFleet(fleetId)
    }


}


data class FleetRequest(

    @get: Size(min = 2, max = 10, message = "The fleet name length must be between 2 and 10.")
    @get: NotBlank(message = "The fleet name is required.")
    val name: String
)
