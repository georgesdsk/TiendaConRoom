package com.example.kproyectofinal.Fragments.ProductDetails

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.kproyectofinal.BaseDatos.ProductCestaReferencia
import com.example.kproyectofinal.BaseDatos.ProductDao
import com.example.kproyectofinal.BaseDatos.TiendaBBDD
import com.example.kproyectofinal.Entidades.ProductEntity
import com.example.kproyectofinal.R
import com.example.kproyectofinal.databinding.FragmentProductDetailsBinding
import com.example.kproyectofinal.mainModule.MainActivity
import com.google.android.material.snackbar.Snackbar


class FragmentProductDetails : Fragment() {

    private lateinit var mBinding: FragmentProductDetailsBinding
    private var mActivity: MainActivity? = null
    private lateinit var mProductDetailsViewModel: ProductDetailsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mProductDetailsViewModel =
            ViewModelProvider(requireActivity())[ProductDetailsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
         ): View? { // todo de que vista extiende

        mBinding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.botonAnhadirCesta.setOnClickListener {
            mProductDetailsViewModel.addProduct()
        }
        inlfarVista(mProductDetailsViewModel.getProductoActuall().value!!)

        //todo aqui ira la cesta
        mActivity = activity as? MainActivity
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true) // para el boton de retroceso
        //para cambiar el nombre de arriba
        mActivity?.supportActionBar?.title = getString(R.string.detallesProducto)

        setHasOptionsMenu(true) //que tenga acceso al menu
    }


    private fun inlfarVista(mSelectedProduct: ProductEntity) {
        with(mBinding) {
            tvNombre.setText(mSelectedProduct.name)
            tvPrecio.setText(mSelectedProduct.unitPrice.toString())
            tvPrecioUnitario.setText(mSelectedProduct.priceKgL.toString())
            Glide.with(requireActivity())
                .load(mSelectedProduct.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(mBinding.imagen)
            //
        }
    }


}