package com.marcohc.tier.sample.shared.data.api

import com.marcohc.tier.sample.shared.data.api.SecretKeyInterceptor.Companion.HEADER_SECRET_KEY_REQUIRED
import com.marcohc.tier.sample.shared.data.models.Vehicle
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface VehicleApi {

    @Headers(
        "Content-Type: application/json",
        HEADER_SECRET_KEY_REQUIRED
    )
    @GET("5fa8ff8dbd01877eecdb898f/")
    fun getVehicles(
        @Query("longitude") longitude: Double? = null,
        @Query("latitude") latitude: Double? = null
    ): Single<GetVehicleResponse>
}

data class GetVehicleResponse(
    val data: DataResponse
)

data class DataResponse(
    val current: List<Vehicle>
)
