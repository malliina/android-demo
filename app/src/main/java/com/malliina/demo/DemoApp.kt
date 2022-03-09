package com.malliina.demo

import android.app.Application

class DemoApp: Application() {
  val http: HttpClient = HttpClient()
}
