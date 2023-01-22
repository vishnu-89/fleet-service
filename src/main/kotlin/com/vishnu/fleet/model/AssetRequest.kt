package com.vishnu.fleet.model

import io.swagger.annotations.ApiModelProperty
import org.intellij.lang.annotations.Pattern
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class AssetRequest(

    @ApiModelProperty(notes = "Provided name", required = true)
    @get: Size(min=2 , max = 10)
    @get: NotBlank(message = "The asset name is required.")
    val name: String,

    @ApiModelProperty(notes = "Provided vin", required = true)
    @get: NotBlank(message = "The VIN is required.")
    @get: Pattern("^[a-zA-Z0-9 ]+$")
    val vin: String

    )
