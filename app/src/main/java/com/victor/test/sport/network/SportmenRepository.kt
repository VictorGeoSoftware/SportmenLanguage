package com.victor.test.sport.network

import com.victor.test.sport.data.TypeDto
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers

/**
 * Created by victorpalmacarrasco on 29/3/18.
 * ${APP_NAME}
 */
interface SportmenRepository {
    @Headers("Content-Type: application/json;charset=UTF-8")

    @GET("/bins/66851")
    fun getSportMenList(): Observable<ArrayList<TypeDto>>
}