package com.example.kproyectofinal.mainModule

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.kproyectofinal.Entidades.Cesta
import com.example.kproyectofinal.Entidades.ProductEntity
import com.example.kproyectofinal.R
import com.example.kproyectofinal.databinding.ItemProductBinding

class ProductAdapter(
    private var productList: MutableList<ProductEntity>,
    private var listener: OnClickListener
) :
     RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private lateinit var mContext: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.getContext()
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_product, parent, false)
        return ViewHolder(view) // asegurarse de darle al mip
        // Se guarda en el HOLDER, la vista a insertarse en todos los elementos de la lista
    }

    /** por cada elemento de la lista, le seteas su vista y su listener
     *
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val product =
            productList.get(position)// aqui es donde hare interacciones con los atributos de la vista
        with(holder) {
            setListener(product)
            binding.tvName.text = product.name
            Glide.with(mContext)
                .load(product.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .circleCrop()
                .into(binding.imgStore)
            binding.cbFavorite.isChecked = product.isFavorite
        }


    }

    override fun getItemCount(): Int = productList.size;


    fun setProducts(products: MutableList<ProductEntity>) {
        productList = products
        notifyDataSetChanged();
    }


    fun add(productEntity: ProductEntity) {
        productList.add(productEntity)
        notifyDataSetChanged()
    }

    fun update(productEntity: ProductEntity) {
        val index = productList.indexOf(productEntity)
        if (index > -1) {
            productList.set(index, productEntity)
            notifyItemChanged(index)
        }
    }

    fun delete(productEntity: ProductEntity) {
        val index = productList.indexOf(productEntity)
        if (index > -1) {
            productList.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun setCesta(cesta: Cesta) {

        add(ProductEntity(name = cesta.idCesta.toString()))

    }


    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemProductBinding.bind(view)

        fun setListener(productEntity: ProductEntity) {
            with(binding.root) {
                setOnClickListener { listener.onClick(productEntity) } //pasarle un metodo?
                setOnLongClickListener {
                    listener.onDeleteProduct(productEntity)
                    true
                }
            }
            binding.cbFavorite.setOnClickListener {
                listener.onCestaProduct(productEntity)
            }
        }
    }
}