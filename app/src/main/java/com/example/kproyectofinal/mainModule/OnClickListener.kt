package com.example.kproyectofinal.mainModule

import com.example.kproyectofinal.Entidades.ProductEntity

interface OnClickListener {
    fun onClick(productEntity: ProductEntity)
    fun onCestaProduct(productEntity: ProductEntity)
    fun onDeleteProduct(productEntity: ProductEntity)

}