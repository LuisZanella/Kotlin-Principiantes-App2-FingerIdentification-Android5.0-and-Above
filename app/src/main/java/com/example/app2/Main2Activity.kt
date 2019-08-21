package com.example.app2

import Model.User
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        fillData()
    }
    fun fillData(){
        try {
            val bundle = intent.extras
            val obj = bundle!!.getString("obj")
            val gson = Gson()
            val userData = gson.fromJson(obj, User::class.java)
            if (userData.gender == 2) txtWelcome.text = "Bienvenida" else txtWelcome.text = "Bienvenido"
            txtName.text = userData.name
        } catch (e: Exception) {
            Log.i("Error en los parametros", "onCreate:  hubo un error: " + e.message)
        }
    }
}
