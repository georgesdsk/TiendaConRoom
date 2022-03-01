package com.example.kproyectofinal.BaseDatos

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.kproyectofinal.Entidades.Cesta
import com.example.kproyectofinal.Entidades.ProductEntity

data class CestaConProductos (
    @Embedded val cesta: Cesta,
    @Relation(
        parentColumn = "idCesta",
        entityColumn = "id",
        associateBy = Junction(ProductCestaReferencia::class)
    )
    val listaProductos: List<ProductEntity>
        )