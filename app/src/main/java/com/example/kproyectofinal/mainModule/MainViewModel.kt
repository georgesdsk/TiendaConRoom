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

import com.example.kproyectofinal.BaseDatos.TiendaBBDD
import com.example.kproyectofinal.Entidades.Cesta


import com.example.kproyectofinal.Entidades.ProductEntity
import com.example.kproyectofinal.R

class MainViewModel(application: Application): AndroidViewModel(application) {
/*

    //cambiar aqui el sortedBy
    private val context = getApplication<Application>().applicationContext
    private val bbdd =  TiendaBBDD.getInsance(context).productDao

    private val products : LiveData<MutableList<ProductEntity>> = liveData {
        val productLiveData = bbdd.getAllProducts()
        emitSource(productLiveData.map { stores ->
            stores.sortedBy { it.name }.toMutableList()
        })
    }

    private val cestaActual: LiveData<Cesta> = liveData{
        val cestaActualLiveData = bbdd.getCesta()
    }

    suspend fun nuevaCesta()   = withContext(Dispatchers.IO){


    }





*/


}