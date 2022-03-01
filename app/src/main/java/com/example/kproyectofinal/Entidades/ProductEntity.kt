package com.example.kproyectofinal.Entidades

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName="ProductEntity")
data class ProductEntity(@PrimaryKey(autoGenerate = true) val id: Int=0,
                         val name: String = "Patata",
                         val image: String ="https://pics.me.me/thumb_dig-potato-for-vodka-is-russian-winter-kartoshka-and-hat-50276150.png",
                         val unitPrice: Double = 0.5,
                         val quantity: Int = 5,
                         val priceKgL: Double = 3.5,
                         var isFavorite: Boolean = false)

