package com.example.forhealth.network

import com.example.forhealth.network.models.ProfileApiItem
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query


private const val BASE_URL = "http://192.168.1.105:8080/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface MainApiService
{
    //Регистрация - отправляем данные на проверку, получаем либо ошибку, либо успех с готовым профилем со ВСЕМИ полями
    @POST("/")
    suspend fun registerUserOnServer(@Body user:ProfileApiItem): Response<ProfileApiItem>

    //Вход - отправляем логин и пароль на проверку, при нахождении профиля в бд получаем профиль со ВСЕМИ полями
    @GET("/")
    suspend fun getUserFromServer(@Query("user_name") userName:String, @Query("user_password") userPassword:String): Response<ProfileApiItem>

    //Изменение - отправляем профиль со ВСЕМИ данными, при нахождении профиля по id и токену получаем измененый профиль
    @PUT("/")
    suspend fun changeUserOnServer(@Body user: ProfileApiItem): Response<ProfileApiItem>

    //Удаление - отправляем локальный id и токен, при нахождении на сервере переданного id и токена профиль удаляется
    @DELETE("/")
    suspend fun deleteUserFromServer(@Query("user_id") userId:Int, @Query("user_token") userToken:String): Response<Void>
}

object MainApiModule
{
    val retrofitService : MainApiService by lazy {retrofit.create(MainApiService::class.java)}
}