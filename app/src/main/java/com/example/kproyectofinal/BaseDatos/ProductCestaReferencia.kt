package com.example.kproyectofinal.BaseDatos

import androidx.room.Entity

@Entity(primaryKeys = ["idCesta","id"])
data class ProductCestaReferencia (
    val idCesta : Int,
    val id : Int)




