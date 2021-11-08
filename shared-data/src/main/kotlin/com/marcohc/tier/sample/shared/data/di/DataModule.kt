package com.marcohc.tier.sample.shared.data.di

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.marcohc.terminator.core.koin.CoreModule
import com.marcohc.tier.sample.shared.data.api.SecretKeyInterceptor
import com.marcohc.tier.sample.shared.data.api.VehicleApi
import com.marcohc.tier.sample.shared.data.repositories.VehicleRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZonedDateTime
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object DataModule : CoreModule {

    override val module = module {

        single {
            val okHttpClientBuilder = OkHttpClient.Builder()
                .connectTimeout(CONNECTION_TIME_OUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT.toLong(), TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT.toLong(), TimeUnit.SECONDS)

            okHttpClientBuilder.addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )

            okHttpClientBuilder.addInterceptor(
                get<SecretKeyInterceptor>()
            )

            val gson = GsonBuilder()
                .registerTypeAdapter(
                    LocalDateTime::class.java,
                    JsonDeserializer<Any?> { json, _, _ ->
                        ZonedDateTime.parse(json.asJsonPrimitive.asString).toLocalDateTime()
                    }
                )
                .create()

            val builder = Retrofit.Builder()
                .baseUrl("https://api.jsonbin.io/b/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClientBuilder.build())

            builder.build()
        }

        single { get<Retrofit>().create(VehicleApi::class.java) }

        single {
            SecretKeyInterceptor(secretKey = get(named(API_KEY)))
        }

        single {
            VehicleRepository(
                api = get(),
                scheduler = get()
            )
        }
    }

    const val API_KEY = "mapApiKey"
    private const val CONNECTION_TIME_OUT = 30
    private const val WRITE_TIME_OUT = 30
    private const val READ_TIME_OUT = 30
}
