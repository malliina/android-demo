package com.malliina.demo

import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.*
import java.io.IOException

class HttpClient(private val http: OkHttpClient = OkHttpClient()) {
  suspend fun makeRequest(url: HttpUrl): Response = suspendCancellableCoroutine { cont ->
    val call = http.newCall(Request.Builder().url(url).build())
    call.enqueue(object : Callback {
      override fun onResponse(call: Call, response: Response) {
        cont.resumeWith(Result.success(response))
      }

      override fun onFailure(call: Call, e: IOException) {
        cont.resumeWith(Result.failure(e))
      }
    })
  }
}
