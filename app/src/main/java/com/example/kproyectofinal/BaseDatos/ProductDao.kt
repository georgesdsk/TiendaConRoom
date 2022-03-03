package com.example.kproyectofinal.BaseDatos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.kproyectofinal.Entidades.Cesta
import com.example.kproyectofinal.Entidades.ProductEntity

@Dao
interface ProductDao {
    @Query("SELECT * FROM ProductEntity")
       fun getAllProducts():MutableList<ProductEntity>

    @Insert()
      fun addProduct(product: ProductEntity)

    @Update()
      fun update(product: ProductEntity)

    @Delete()
      fun delete(product: ProductEntity)

    @Update
     fun actualizarCesta(cesta: Cesta)


    @Query("SELECT * FROM Cesta WHERE idCesta = :id") // espero que   funcione el true
      fun getProductos(id: Int): LiveData<MutableList<CestaConProductos>>

      @Query("SELECT * FROM Cesta WHERE estadoCesta = 0") // tiene que devolver solo uno
       fun getCesta(): Cesta

    @Query("SELECT * FROM ProductEntity WHERE id =:id") // espero que   funcione el true
    fun getProductbyId(id: Int):ProductEntity


    @Insert()
     fun addCesta(cesta: Cesta): Long // para que en cuanto se anhada tengamos su id

    @Insert()
      fun insertarProductoCesta(productoCestaReferencia: ProductCestaReferencia) // hacer que se actualice en la pantalla

    @Delete()
      fun borrarProductoCesta(productoCestaReferencia: ProductCestaReferencia)

    @Delete()
      fun borrarCesta(cesta: Cesta)






}