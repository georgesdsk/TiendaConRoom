package com.example.kproyectofinal.BaseDatos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.kproyectofinal.Entidades.Cesta
import com.example.kproyectofinal.Entidades.ProductEntity

@Dao
interface ProductDao {
    @Query("SELECT * FROM ProductEntity")
    fun getAllProducts(): LiveData<MutableList<ProductEntity>>

    @Insert()
    suspend fun addProduct(product: ProductEntity)

    @Update()
    fun update(product: ProductEntity)

    @Delete()
    fun delete(product: ProductEntity)

    @Update
    suspend fun actualizarCesta(cesta: Cesta)

// se podria mejorar
    @Query("SELECT * FROM ProductCestaReferencia" +
            " INNER JOIN ProductEntity ON ProductEntity.id = ProductCestaReferencia.id" +
            " WHERE idCesta = :id ") // solo tendria los ids
    fun getProductosCesta(id: Int): LiveData<MutableList<ProductEntity>>

    @Query("SELECT * FROM Cesta WHERE estadoCesta = 0") // tiene que devolver solo uno
    suspend fun getCesta(): Cesta

    @Query("SELECT * FROM ProductEntity WHERE id =:id") // espero que   funcione el true
    fun getProductbyId(id: Int): ProductEntity

    @Insert()
    suspend fun addCesta(cesta: Cesta): Long // para que en cuanto se anhada tengamos su id

    @Insert()
    suspend fun insertarProductoCesta(productoCestaReferencia: ProductCestaReferencia) // hacer que se actualice en la pantalla

    @Delete()
    suspend fun borrarProductoCesta(productoCestaReferencia: ProductCestaReferencia)

    @Delete()
    fun borrarCesta(cesta: Cesta)

    @Query("SELECT * FROM ProductCestaReferencia WHERE idCesta = :idCesta AND id = :idProducto")
    suspend fun productoEnCesta(idCesta: Int, idProducto: Int): ProductCestaReferencia


}