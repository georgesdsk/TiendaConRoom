package com.example.kproyectofinal.Fragments.Cesta

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.example.kproyectofinal.BaseDatos.ProductCestaReferencia
import com.example.kproyectofinal.BaseDatos.TiendaBBDD
import com.example.kproyectofinal.Entidades.Cesta
import com.example.kproyectofinal.Entidades.ProductEntity
import com.example.kproyectofinal.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CestaViewModel(application: Application) : AndroidViewModel(application) {

    val context = getApplication<Application>().applicationContext

    // en la pantalla principal le voy a pasar toda la info
    // mirar si le doy para atras despues de comprar, si se puede cambiarle la cesta al MainViewModel
    val bbdd = TiendaBBDD.getInsance(context).productDao
    val isLoading = MutableLiveData<Boolean>()
    var cestaActual = MutableLiveData<Cesta>()
    val totalCompra = MutableLiveData<Double>()
    private var productList : MutableLiveData<List<ProductEntity>> = MutableLiveData()

    fun getProductList() : LiveData<List<ProductEntity>>{
        return productList
    }

    fun loadProductList() {
        viewModelScope.launch(Dispatchers.IO) {
            productList.postValue(cestaActual.value?.idCesta?.let { bbdd.getProductosCesta(it) })
        }

    }


    //he cambiado el Job
    fun newCesta(cestaAntigua: Cesta, cestaNueva: Cesta) {
        viewModelScope.launch {
            bbdd.actualizarCesta(cestaAntigua) // ponerle el true de antes, se podria hacer todo desde la badat
            val cesta = bbdd.addCesta(cestaNueva) // o reconstruir a partir del id , o buscarlo de nuevo
            cestaActual.postValue(Cesta(idCesta = cesta.toInt(), false))
            //actualizarLista(cestaAntigua) // para que se vuelva a hacer el getProducts al acutalizarse la lista
        }
    }


    public fun calcularTotal(): Double { // se actualizara
        val copiaProducts = productList.value
        var sumatorio: Double = 0.0
        copiaProducts?.forEach {
            x-> sumatorio+= x.unitPrice
        }
        totalCompra.postValue(sumatorio)
        return sumatorio

    }

    fun setCestaActuall(cesta: MutableLiveData<Cesta>) {
        //cestaActual.postValue(cesta.value!!)
        cestaActual = cesta

    }

    fun getCestaActuall(): MutableLiveData<Cesta> {
        return cestaActual
    }


    //realmente no se  tiene que comunicar con la interfaz pero paso, todo preguntar antes
    fun deleteProduct(producto: ProductEntity): Boolean {
        viewModelScope.launch(Dispatchers.IO) {

            val responseDeleteList = async {  cestaActual.value?.idCesta?.let {
                    ProductCestaReferencia(
                        it,
                        producto.id
                    )
                }?.let {
                    bbdd.borrarProductoCesta(
                        it
                    )
                }
            }
            responseDeleteList.await()
            loadProductList()

        }
        return true
    }

    fun actualizarLista(cesta: Cesta){
        val productoAux = 0;
        viewModelScope.launch {
            bbdd.insertarProductoCesta( ProductCestaReferencia(cesta.idCesta,productoAux))

            bbdd.borrarProductoCesta(
                ProductCestaReferencia(
                    cesta.idCesta,
                    productoAux
                )
            )

        }

    }

}