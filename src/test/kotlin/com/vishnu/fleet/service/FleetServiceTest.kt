package com.vishnu.fleet.service

import com.vishnu.fleet.NotFoundExecption
import com.vishnu.fleet.model.Asset
import com.vishnu.fleet.model.Fleet
import com.vishnu.fleet.repo.AssetRepository
import com.vishnu.fleet.repo.FleetRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.doNothing
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class FleetServiceTest {

    @Mock
    lateinit var fleetRepository: FleetRepository

    @Mock
    lateinit var assetRepository: AssetRepository

    @InjectMocks
    lateinit var fleetService: FleetService

    @Test
    fun `Fetch all assets of a Fleet`() {
        val fleet = Fleet(id = 1, name = "fleet-name")
        given(fleetRepository.findById(1)).willReturn(Optional.of(fleet))
        given(assetRepository.findByFleetId(1)).willReturn(
            listOf(
                Asset(
                    name = "car",
                    vin = "ASWE123DHN234",
                    fleet = Fleet(name = "fleet-name")
                )
            )
        )
        val response = fleetService.fetchAllAsssetsOfAFleet(1)

        assertEquals(1, response.size)
        assertEquals("car", response.get(0).name)
    }

    @Test
    fun `Throw not found if fleet not present`() {
        given(fleetRepository.findById(1)).willReturn(Optional.empty())

        assertThrows<NotFoundExecption>() {
            fleetService.fetchAllAsssetsOfAFleet(1)
        }

    }

    @Test
    fun `Fetch fleet by ID `() {
        given(fleetRepository.findById(1)).willReturn(Optional.of(Fleet(id = 1, name = "fleet-name")))

        val result = fleetService.fetchFleetById(1)

        assertThat(result)
        assertEquals("fleet-name", result.name)

    }

    @Test
    fun `Fetch fleet by ID throw exception if not found`() {
        given(fleetRepository.findById(1)).willReturn(Optional.empty())

        assertThrows<NotFoundExecption>() {
            fleetService.fetchFleetById(1)
        }

    }

    @Test
    fun `Delete fleet `() {
        val fleet = Fleet(id = 1, name = "fleet-name")
        given(fleetRepository.findById(1)).willReturn(Optional.of(fleet))
        doNothing().`when`(fleetRepository).delete(fleet)
        fleetService.deleteFleet(1)

    }

    @Test
    fun `Delete fleet throw exception if not found`() {
        given(fleetRepository.findById(1)).willReturn(Optional.empty())

        assertThrows<NotFoundExecption>() {
            fleetService.deleteFleet(1)
        }

    }
}
