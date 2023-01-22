package com.vishnu.fleet.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.vishnu.fleet.model.Asset
import com.vishnu.fleet.model.AssetRequest
import com.vishnu.fleet.model.Fleet
import com.vishnu.fleet.service.FleetService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.doNothing
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@ExtendWith(MockitoExtension::class)
class FleetControllerTest {

    lateinit var mockMvc: MockMvc

    @Mock
    lateinit var fleetService: FleetService

    @InjectMocks
    lateinit var fleetController: FleetController

    @BeforeEach
    open fun setUp() {

        mockMvc = MockMvcBuilders
            .standaloneSetup(fleetController)
            //.setControllerAdvice(ApiExceptionHandlers())
            //.setMessageConverters(MappingJackson2HttpMessageConverter(objectMapper))
            .build()
    }

    @Test
    fun `Add assets to a fleet`() {
        val fleet = FleetRequest("fleet1")
        val assetRequestBody = jacksonObjectMapper().writeValueAsString(fleet)
        given(fleetService.createFleet(fleet)).willReturn(Fleet(name = "fleet"))
        mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/fleets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(assetRequestBody)
        ).andExpect(MockMvcResultMatchers.status().isCreated)
    }

    @Test
    fun `Add assets to a fleet throw bad request if name is not valid`() {
        val fleet = FleetRequest("f")
        val assetRequestBody = jacksonObjectMapper().writeValueAsString(fleet)
        mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/fleets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(assetRequestBody)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `Get fleet by ID - Success OK`() {
        given(fleetService.fetchFleetById(1)).willReturn(Fleet(name = "fleet"))
        val fleetResponse = """{"id":null,"name":"fleet","updatedAt":null,"createdAt":null,"updatedBy":null}"""
        mockMvc.perform(
            MockMvcRequestBuilders.get("/v1/fleets/1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().string(fleetResponse))
    }

    @Test
    fun `Add assset to a fleet - Success Created`() {
        val assetReq = AssetRequest(name = "car", vin = "DAGBC1234DGVR")
        val assetRequestBody = jacksonObjectMapper().writeValueAsString(assetReq)

        given(fleetService.createAssetsToFleet(assetReq, 1)).willReturn(
            Asset(
                name = "car",
                vin = "DAGBC1234DGVR",
                fleet = Fleet(name = "fleet")
            )
        )

        mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/fleets/1/assets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(assetRequestBody)
        ).andExpect(MockMvcResultMatchers.status().isCreated)
    }

    @Test
    fun `Add assset to a fleet - Bad Request`() {
        val assetReq = AssetRequest(name = "", vin = "fleet")
        val assetRequestBody = jacksonObjectMapper().writeValueAsString(assetReq)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/fleets/1/assets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(assetRequestBody)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `Get all assset of a fleet - OK`() {
        given(fleetService.fetchAllAsssetsOfAFleet( 1)).willReturn(
            listOf(
                Asset(
                    name = "car",
                    vin = "DAGBC1234DGVR",
                    fleet = Fleet(name = "fleet")
                )
            )
        )

        mockMvc.perform(
            MockMvcRequestBuilders.get("/v1/fleets/1/assets")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `Delete fleet and its assets - No Content`() {
        doNothing().`when`(fleetService).deleteFleet(1)

        mockMvc.perform(
            MockMvcRequestBuilders.delete("/v1/fleets/1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent)
    }
}
