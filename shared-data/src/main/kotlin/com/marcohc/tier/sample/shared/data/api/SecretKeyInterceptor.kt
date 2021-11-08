package com.marcohc.tier.sample.shared.data.api

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * We must inject the secret key when needed into the request without hardcoding it
 */
internal class SecretKeyInterceptor(private val secretKey: String?) : Interceptor {

    private val headerRequiredKey = HEADER_SECRET_KEY_REQUIRED.split(":").first()

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request.header(headerRequiredKey)?.let {
            if (secretKey.isNullOrEmpty()) {
                throw IllegalStateException("Ey developer, you must set the secret api key in your local.properties files to make this work")
            } else {
                request = request.newBuilder()
                    .removeHeader(headerRequiredKey)
                    .addHeader(HEADER_SECRET_KEY_PREFIX, secretKey)
                    .build()
            }
        }
        return chain.proceed(request)
    }

    companion object {
        const val HEADER_SECRET_KEY_REQUIRED = "header_secret_key_required: header_secret_key_required"
        private const val HEADER_SECRET_KEY_PREFIX = "secret-key"
    }
}
