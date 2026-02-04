package com.vishnu.fleet.controller

import com.vishnu.fleet.model.Asset
import com.vishnu.fleet.model.Fleet
import com.vishnu.fleet.service.AssetService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@ExtendWith(MockitoExtension::class)
class AssetsControllerTest {

    lateinit var mockMvc: MockMvc

    @Mock
    lateinit var assetService: AssetService

    @InjectMocks
    lateinit var assetController: AssetController

    @BeforeEach
    fun setUp() {

        mockMvc = MockMvcBuilders
            .standaloneSetup(assetController)
            .build()
    }

    @Test
    fun `Remove an asset from a fleet`() {
        doNothing().`when`(assetService).deleteAsssetOfAFleet(anyLong())
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/v1/assets/1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent)
    }

    @Test
    fun `Find asset by VIN - Success OK`() {
        val asset = Asset(name = "car", vin = "ASHJCU123JN", fleet = Fleet(name = "fleet"))
        given(assetService.searchAssetByVin(anyString(), anyLong())).willReturn(asset)
        mockMvc.perform(
            MockMvcRequestBuilders.get("/v1/assets/ASHJCU123JN" + "?fleetId=1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

   // @Test
    fun `Find asset by VIN - Not Found`() {
        given(assetService.searchAssetByVin(anyString(), anyLong())).willReturn(null)
        mockMvc.perform(
            MockMvcRequestBuilders.get("/v1/assets/ASHJCU123JN" + "?fleetId=1")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect({ result -> assertEquals("Given vin for a fleet is not present",result.resolvedException?.message)} )

    }
}
