package com.example.sofie.http

import okhttp3.*


class HttpHelper {

    fun post(json: String): Int {

        //URL POST
        val URL ="https://9g1borgfz0.execute-api.sa-east-1.amazonaws.com/beta/task"
        val headerHttp = MediaType.parse("application/json; charset=utf-8")
        val client = OkHttpClient()
        val body = RequestBody.create(headerHttp, json)
        val request = Request.Builder().url(URL).post(body).build()
        val response = client.newCall(request).execute()
        return response.code()
    }

    fun get(): ResponseBody? {
        //URL GET
        val URL ="https://9g1borgfz0.execute-api.sa-east-1.amazonaws.com/beta/task"
        val client = OkHttpClient()
        val request = Request.Builder().url(URL).get().build()
        val response = client.newCall(request).execute()
        val responseBody = response.body()

        if(responseBody != null){
            val json = responseBody.string()
            println("REPOSTA #######" + json)

            return responseBody
        }else{
            return responseBody
        }


    }
}