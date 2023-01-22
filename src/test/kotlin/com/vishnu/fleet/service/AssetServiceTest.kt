package com.vishnu.fleet.service

import com.vishnu.fleet.NotFoundExecption
import com.vishnu.fleet.model.Asset
import com.vishnu.fleet.model.Fleet
import com.vishnu.fleet.repo.AssetRepository
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
class AssetServiceTest {

    @Mock
    lateinit var assetRepository: AssetRepository

    @InjectMocks
    lateinit var assetService: AssetService

    @Test
    fun `Delete asset of a fleet`() {
        given(assetRepository.findById(1)).willReturn(
            Optional.of(
                Asset(
                    id = 1L,
                    name = "car",
                    vin = "ASCER4556SDFG",
                    fleet = Fleet(id = 1L, name = "fleet"),
                )
            )
        )

        doNothing().`when`(assetRepository).deleteById(1)
        assetService.deleteAsssetOfAFleet(1)
    }

    @Test
    fun `Delete asset of a fleet - Not found`() {
        given(assetRepository.findById(1)).willReturn(
            Optional.empty()
        )

        assertThrows<NotFoundExecption> {
            assetService.deleteAsssetOfAFleet(1)
        }

    }

    @Test
    fun `Search asset by VIN `() {
        given(assetRepository.findByVinAndFleetId("ASDCGN1234AJ", 1)).willReturn(
            Asset(name = "Car", vin = "ASDCGN1234AJ", fleet = Fleet(name = "fleet"))
        )

        val asset = assetService.searchAssetByVin("ASDCGN1234AJ", 1)
        assertThat(asset)
        assertEquals("ASDCGN1234AJ",asset?.vin)
    }
}
