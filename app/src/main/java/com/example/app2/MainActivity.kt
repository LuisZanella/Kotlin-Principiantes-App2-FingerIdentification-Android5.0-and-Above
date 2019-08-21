package com.example.app2

import Model.User
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import android.text.TextUtils
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.easyfingerprint.EasyFingerPrint
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var autenticado : Boolean? = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnActions()
    }
    fun btnActions(){
        btnLogin.setOnClickListener{
            if(!validar()){
                var gender : Int = 1
                if(rdButtonWoman.isChecked){
                    gender = 2
                }
                val user = User(txtName.text.toString(),gender)
                val gson = Gson()
                val obj = gson.toJson(user)
                val intent = Intent(applicationContext,Main2Activity::class.java)
                intent.putExtra("obj", obj)
                startActivity(intent)
            }
        }
    }
    fun validar() : Boolean{
        var validadorInterno = false
        if (TextUtils.isEmpty(txtName.text.toString())){
            txtName.error = "Capture un nombre valido"
            validadorInterno = true
        }
        val id: Int = rdGroupGender.checkedRadioButtonId
        if (id==-1){
            Toast.makeText(applicationContext,"Seleccione su genero porfavor", Toast.LENGTH_SHORT).show()
            validadorInterno = true
        }
        if(!this.autenticado!!){
            Toast.makeText(this@MainActivity,"No se ha identificado correctamente su huella digital", Toast.LENGTH_SHORT).show()
            validadorInterno = true
        }
        return validadorInterno
    }
    fun abrirDialogoFinger(v: View) {
        EasyFingerPrint(this)
            .setTittle("Iniciar sesión")
            .setSubTittle("account@account.com.br")
            .setDescription("Use el identificador digital para iniciar sesión")
            .setColorPrimary(R.color.colorPrimary)
            .setIcon(ContextCompat.getDrawable(this,R.mipmap.ic_launcher_round))
            .setListern(object : EasyFingerPrint.ResultFingerPrintListern{
                override fun onError(mensage: String, code: Int) {

                    when(code){
                        EasyFingerPrint.CODE_ERRO_CANCEL -> { } // TO DO
                        EasyFingerPrint.CODE_ERRO_GREATER_ANDROID_M -> { } // TO DO
                        EasyFingerPrint.CODE_ERRO_HARDWARE_NOT_SUPPORTED -> { } // TO DO
                        EasyFingerPrint.CODE_ERRO_NOT_ABLED -> { } // TO DO
                        EasyFingerPrint.CODE_ERRO_NOT_FINGERS -> { } // TO DO
                        EasyFingerPrint.CODE_NOT_PERMISSION_BIOMETRIC -> { } // TO DO
                    }
                    autenticado = false
                    Toast.makeText(this@MainActivity,"No se ha identificado correctamente su huella digital", Toast.LENGTH_SHORT).show()
                }

                override fun onSucess(cryptoObject: FingerprintManagerCompat.CryptoObject?) {
                    autenticado = true
                }

            })
            .startScan()
    }

}
