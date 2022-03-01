package com.example.kproyectofinal.mainModule

import com.example.kproyectofinal.Entidades.ProductEntity

interface OnClickListener {
    fun onClick(productId: Int)
    fun onCestaProduct(productEntity: ProductEntity)
    fun onDeleteProduct(productEntity: ProductEntity)

}