package com.example.kproyectofinal.Fragments.Cesta

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.example.kproyectofinal.BaseDatos.ProductCestaReferencia
import com.example.kproyectofinal.BaseDatos.TiendaBBDD
import com.example.kproyectofinal.Entidades.Cesta
import com.example.kproyectofinal.Entidades.ProductEntity
import com.example.kproyectofinal.R
import kotlinx.coroutines.launch

class CestaViewModel(application: Application) : AndroidViewModel(application) {

    val context = getApplication<Application>().applicationContext

    // en la pantalla principal le voy a pasar toda la info
    // mirar si le doy para atras despues de comprar, si se puede cambiarle la cesta al MainViewModel
    val bbdd = TiendaBBDD.getInsance(context).productDao
    val isLoading = MutableLiveData<Boolean>()
    val cestaActual = MutableLiveData<Cesta>()
    val totalCompra = MutableLiveData<Double>()
    val products2 = MutableLiveData<List<ProductEntity>>()




    private val products: LiveData<MutableList<ProductEntity>> = liveData {
        isLoading.postValue(true)
        initCesta()
        val productLiveData = bbdd.getProductosCesta(cestaActual.value!!.idCesta)
        emitSource(productLiveData.map { products -> products.sortedBy { it.name }.toMutableList() }
        )
        isLoading.postValue(false)
    }



    //he cambiado el Job

    fun getProducts(): LiveData<MutableList<ProductEntity>> {
        return products
    }

    public fun calcularTotal(): Double { // se actualizara
        var copiaProducts = products.value!!
        var sumatorio: Double = 0.0
        copiaProducts.forEach {
            x-> sumatorio+= x.unitPrice
        }
        totalCompra.postValue(sumatorio)
        return sumatorio
    }

    fun setCestaActual(cesta: Cesta) {
        cestaActual.postValue(cesta)
    }


    fun initCesta()  {
        viewModelScope.launch { // si no hay ninguna cesta que te devuelve?
            var cesta: Cesta = bbdd.getCesta()
            cesta.idCesta
            if (cesta.idCesta == 0) {
                cesta = Cesta()// mirar el id
                insertarCesta(cesta)
            }
            cestaActual.postValue(cesta)
        }

    }

    fun insertarCesta(cestaNueva: Cesta) {
        viewModelScope.launch {
            val cesta = bbdd.addCesta(cestaNueva) // no se como funcionara
            cestaActual.postValue(Cesta(idCesta = cesta.toInt(), false))
        }
    }

    fun cambiarCestaActual(cestaAntigua: Cesta, cestaNueva: Cesta) {
        viewModelScope.launch {
            bbdd.actualizarCesta(cestaAntigua) // ponerle el true de antes, se podria hacer todo desde la badat
            val cesta = bbdd.addCesta(cestaNueva) // o reconstruir a partir del id , o buscarlo de nuevo
            cestaActual.postValue(Cesta(idCesta = cesta.toInt(), false))
        }
    }

    //realmente no se  tiene que comunicar con la interfaz pero paso, todo preguntar antes
    fun deleteProduct(producto: ProductEntity): Boolean {
        viewModelScope.launch {
            bbdd.borrarProductoCesta(
                ProductCestaReferencia(
                    cestaActual.value!!.idCesta,
                    producto.id
                )
            )
            Toast.makeText(context, R.string.product_deleted, Toast.LENGTH_SHORT).show()
        }
        return true
    }

    fun getCesta(): MutableLiveData<Cesta> {
        return cestaActual

    }

}