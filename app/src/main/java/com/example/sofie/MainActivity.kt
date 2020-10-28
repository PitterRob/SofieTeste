package com.example.sofie

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable

import androidx.appcompat.app.AppCompatActivity
import com.example.ListAdapte
import com.example.sofie.http.HttpHelper
import com.example.sofie.models.ListaTarefas
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*

import org.jetbrains.anko.doAsync
import org.json.JSONArray
import org.json.JSONObject

import java.net.HttpURLConnection
import java.net.URL


class MainActivity() : AppCompatActivity(), Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle("Sofie");

    val url = "https://9g1borgfz0.execute-api.sa-east-1.amazonaws.com/beta/task"

        @Suppress("DEPRECATION")
        AsyncTaskHandleJson().execute(url)
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            val intent = Intent(this, NovaTarefa::class.java)

            startActivity(intent)
        }
    }

    @Suppress("DEPRECATION")
    inner class AsyncTaskHandleJson : AsyncTask<String, String, String >() {
        override fun doInBackground(vararg url: String?): String {
            var text: String
            val connection = URL(url[0]).openConnection() as HttpURLConnection
            try {
                connection.connect()
                text = connection.inputStream.use { it.reader().use{reader -> reader.readText()} }
            }finally {
                connection.disconnect()

            }
            return text
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            handleJson(result)
        }

    }

    private fun handleJson(jsonString: String?) {
        val jsonObj = JSONObject(jsonString)
        val jsonArray = JSONArray(jsonObj.get("data").toString())

         val list = ArrayList<ListaTarefas>()

        var x = 0
        while (x < jsonArray.length()){

            val jsonObject = jsonArray.getJSONObject(x)
            list.add(ListaTarefas(
                    jsonObject.getString("title"),
                    jsonObject.getString("description")

            ))
            x ++

            val adapter = ListAdapte(this, list)
            list_tarefas.adapter = adapter

        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MainActivity> {
        override fun createFromParcel(parcel: Parcel): MainActivity {
            return MainActivity(parcel)
        }

        override fun newArray(size: Int): Array<MainActivity?> {
            return arrayOfNulls(size)
        }
    }

}

