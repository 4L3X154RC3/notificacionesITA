package com.example.myapplication

import android.app.Application
import com.android.volley.Request
import com.android.volley.VolleyLog.TAG
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class ClaseVolley: Application(){  //Creamos la clase que hereda de Aplicacion
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
    val peticionDeCola: RequestQueue? = null
    get(){
        if (field == null){
            return Volley.newRequestQueue(applicationContext)
        }
        return field
    }
    fun <T> agregaALaCola(peticion: Request<T>){
        peticion.tag = TAG
        peticionDeCola?.add(peticion)
    }
    companion object {
        private val TAG = ClaseVolley::class.java.simpleName
        @get:Synchronized var instance: ClaseVolley? = null
        private set
    }
}