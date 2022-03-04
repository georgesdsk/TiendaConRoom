package com.example.kproyectofinal.mainModule

import android.app.Application
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.lifecycle.map
import com.example.kproyectofinal.BaseDatos.TiendaBBDD
import com.example.kproyectofinal.Entidades.Cesta


import com.example.kproyectofinal.Entidades.ProductEntity
import com.example.kproyectofinal.R

class MainViewModel(application: Application) : AndroidViewModel(application) {

    //cambiar aqui el sortedBy
    //introudir el numero de cantidades en la cesta
    val context = getApplication<Application>().applicationContext
    val bbdd = TiendaBBDD.getInsance(context).productDao
    val isLoading = MutableLiveData<Boolean>()
    val cestaActual = MutableLiveData<Cesta>()

    private val products: LiveData<MutableList<ProductEntity>> = liveData {
        isLoading.postValue(true)
        val productLiveData = bbdd.getAllProducts()
        emitSource(productLiveData.map { products -> products.sortedBy { it.name }.toMutableList() }
        )
        isLoading.postValue(false)
    }


    fun getCesta() {
        viewModelScope.launch { // si no hay ninguna cesta que te devuelve?
            val cesta: Cesta = bbdd.getCesta()
            if (cesta == null) {
                insertarCesta(Cesta())
            } else {
                cestaActual.postValue(cesta)
            }
        }
    }

    //he cambiado el Job
    fun newCesta(cestaAntigua: Cesta, cestaNueva: Cesta) {
        viewModelScope.launch {
            bbdd.actualizarCesta(cestaAntigua) // ponerle el true de antes, se podria hacer todo desde la badat
            val cesta =
                bbdd.addCesta(cestaNueva) // o reconstruir a partir del id , o buscarlo de nuevo
            cestaActual.postValue(Cesta(idCesta = cesta.toInt(), false))
        }
    }

    fun insertarCesta(cestaNueva: Cesta) {
        viewModelScope.launch {
            val cesta = bbdd.addCesta(cestaNueva) // no se como funcionara
            cestaActual.postValue(Cesta(idCesta = cesta.toInt(), false))
        }
    }

    fun insertarProducto(productEntity: ProductEntity) {
        viewModelScope.launch {
            bbdd.addProduct(productEntity)
        }
    }

    fun getProducts(): LiveData<MutableList<ProductEntity>> {
        return products
    }

/*
    fun getProduct(id: Int): MutableLiveData<ProductEntity> {
        viewModelScope.launch {
           bbdd.getProductbyId(id)
        }

    }
*/


    /*TODO APUNTES

    class QuoteViewModel : ViewModel(){

    val quoteModel = MutableliveData<QuoteModel>()
    fun randomQuote(){
        val currentQuote : QuoteModel = QuoteProvider.random()
        quoteModel.postValue(currentQuote)
    }


//1 er video
private val quoteViewModel : QuoteViewModel by viewModels ()


quoteViewModel.quoteModel.observe( owner: this, Observer {
  binding.tvQuote.text = it. quote
  binding.tvAuthor.text = it.author
})


//2

suspend fun getQuotes (): List<QuoteModel> {
    return withContext (Dispatchers. I0) { this: CoroutineScope
        val response : Response<List<QuoteModel>> = retrofit.create(QuoteApiClient::class.java).getAllQuotes ()
        response.body() ?: emptyList()


     */


}