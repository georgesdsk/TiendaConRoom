package com.example.kproyectofinal.BaseDatos

import androidx.room.*
import com.example.kproyectofinal.Entidades.Cesta

@Dao
interface CestaDao {
    @Query("SELECT * FROM Cesta WHERE estadoCesta = 1") // espero que funcione el true
     fun getProductos():MutableList<CestaConProductos>

    @Insert()
     fun addCesta(cesta: Cesta): Long // para que en cuanto se anhada tengamos su id

    @Insert()
     fun insertarProductoCesta(productoCestaReferencia: ProductCestaReferencia) // hacer que se actualice en la pantalla

    @Delete()
     fun borrarProductoCesta(productoCestaReferencia: ProductCestaReferencia)

    @Delete()
     fun borrarCesta(cesta: Cesta)


}