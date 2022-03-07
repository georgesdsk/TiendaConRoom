package com.example.kproyectofinal.Fragments.ProductDetails

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kproyectofinal.BaseDatos.ProductCestaReferencia
import com.example.kproyectofinal.BaseDatos.TiendaBBDD
import com.example.kproyectofinal.Entidades.Cesta
import com.example.kproyectofinal.Entidades.ProductEntity
import com.example.kproyectofinal.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

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


    //realmente no se  tiene que comunicar con la interfaz pero paso
    fun addProduct():Boolean {

        viewModelScope.launch {
            if(bbdd.productoEnCesta(cestaActual.value!!.idCesta, productoActual.value!!.id) == null ){ // si el producto no existe en la cesta
                bbdd.insertarProductoCesta(ProductCestaReferencia(cestaActual.value!!.idCesta, productoActual.value!!.id))
            }else{
                Toast.makeText(context, R.string.producte_exist, Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }

}