package com.ehealthinformatics.prognocare.data.remote.api

import com.ehealthinformatics.prognocare.data.remote.models.CreateEncounterDto
import com.ehealthinformatics.prognocare.data.remote.models.Encounter
import com.ehealthinformatics.prognocare.data.remote.models.UpdateEncounterDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface EncounterApi {

    @GET("api/encounters")
    suspend fun list(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20,
        @Query("patientId") patientId: String? = null,
        @Query("visitId") visitId: String? = null,
    ): Response<List<Encounter>>

    @POST("api/encounters")
    suspend fun create(@Body dto: CreateEncounterDto): Response<Encounter>

    @GET("api/encounters/{id}")
    suspend fun getById(@Path("id") id: String): Response<Encounter>

    @PATCH("api/encounters/{id}")
    suspend fun update(
        @Path("id") id: String,
        @Body dto: UpdateEncounterDto,
    ): Response<Encounter>

    @DELETE("api/encounters/{id}")
    suspend fun delete(@Path("id") id: String): Response<Unit>
}
