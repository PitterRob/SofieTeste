package com.example.sofie

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

import com.example.sofie.http.HttpHelper
import com.example.sofie.models.SaveNovaTarefa
import com.google.gson.Gson
import org.jetbrains.anko.doAsync


class NovaTarefa : AppCompatActivity() {

    lateinit var dialog: AlertDialog


    protected var isInternetAvailable: Boolean = false

    lateinit var text_input_email: String
    lateinit var text_input_title: String
    lateinit var text_input_description: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nova_tarefa)

        val button = findViewById<Button>(R.id.close)

        val text_input_email : EditText = findViewById(R.id.email_input)
        val text_input_title : EditText = findViewById(R.id.titletext_input)
        val text_input_description: EditText = findViewById(R.id.description_input)


        button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)

            startActivity(intent)
        }

        val buttonsave = findViewById<Button>(R.id.save)
        buttonsave.setOnClickListener {
            if(text_input_email.text.trim().isEmpty()) {
                text_input_email.error = "Email é obrigatório"
                Toast.makeText(applicationContext, "Email é obrigatória ", Toast.LENGTH_SHORT).show()
            }
            else if (text_input_title.text.toString().trim().isEmpty()) {
                text_input_title.error = "Título é obrigatório"
                Toast.makeText(applicationContext, " Título é obrigatório ", Toast.LENGTH_SHORT).show()
            }
            else if (text_input_description.text.toString().trim().isEmpty()) {
                text_input_description.error = "Descrição é obrigatória"
                Toast.makeText(applicationContext, "Descrição é obrigatória ", Toast.LENGTH_SHORT).show()
            }else{
                isInternetAvailable = isOnline(applicationContext)
                if (!isInternetAvailable) {
                    showMessageInternet(this, dialog, getString(R.string.no_connection))
                } else {
                    this.text_input_email = text_input_email.text.toString()
                    this.text_input_title = text_input_title.text.toString()
                    this.text_input_description= text_input_description.text.toString()
                     save()
                }
            }
        }


    }


fun save() {
    println(text_input_email)
    println(text_input_title)
    println(text_input_description)

    val novatarefa = SaveNovaTarefa()
    novatarefa.title = this.text_input_title
    novatarefa.email = this.text_input_email
    novatarefa.description = this.text_input_description

    val gson = Gson()
    val novaTarefaJson = gson.toJson(novatarefa)

    doAsync {
        val http = HttpHelper()

   val response = http.post(novaTarefaJson)
         if(response == 201){

            val intent = Intent(this@NovaTarefa, MainActivity::class.java)
            startActivity(intent)
         }

    }



}
    fun isOnline(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }

    fun showMessageInternet(context: Context?, dialog: AlertDialog?, msg: String) {
        if (context != null && dialog != null && !(context as Activity).isFinishing) {
            dialog.setMessage(msg)

            dialog.show()

        }

    }
    fun showMessageSucess (context: Context?, dialog: AlertDialog?, msg: String){
        val dialogCustomer = AlertDialog.Builder(context!!)
        dialogCustomer.setMessage(msg)
        dialogCustomer.setPositiveButton("Ok") { _, _ ->
            val customDialog = Intent(applicationContext, MainActivity::class.java)
            startActivity(customDialog)
        }
        dialogCustomer.show()
    }

}