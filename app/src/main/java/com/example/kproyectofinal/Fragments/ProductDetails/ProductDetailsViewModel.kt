package com.example.kproyectofinal.Fragments.ProductDetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.kproyectofinal.BaseDatos.TiendaBBDD
import com.example.kproyectofinal.Entidades.Cesta
import com.example.kproyectofinal.Entidades.ProductEntity

class ProductDetailsViewModel(application: Application): AndroidViewModel(application) {

    val context = getApplication<Application>().applicationContext

    // en la pantalla principal le voy a pasar toda la info
    // mirar si le doy para atras despues de comprar, si se puede cambiarle la cesta al MainViewModel
    val bbdd = TiendaBBDD.getInsance(context).productDao
    val isLoading = MutableLiveData<Boolean>()
    val cestaActual = MutableLiveData<Cesta>()
    val productoActual = MutableLiveData<ProductEntity>() // todo probar

    fun setProdctoActual(productEntity: ProductEntity){
        productoActual.postValue(productEntity)
    }

    fun getProductoActuall(): MutableLiveData<ProductEntity>{
        return productoActual
    }

    fun setCestaActual(cesta: Cesta){
        cestaActual.postValue(cesta)
    }

    fun getCestaActuall(): MutableLiveData<Cesta>{
        return cestaActual
    }

}