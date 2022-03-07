package com.example.kproyectofinal.Fragments.Cesta

import com.example.kproyectofinal.Entidades.ProductEntity

interface CestaOnClickListener {
    /*fun onClick(productEntity: ProductEntity)
    fun onLikeProduct(productEntity: ProductEntity)*/
    fun onDeleteProductFromCesta(productEntity: ProductEntity)

}