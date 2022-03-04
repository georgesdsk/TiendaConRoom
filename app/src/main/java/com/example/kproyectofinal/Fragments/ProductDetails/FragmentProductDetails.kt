package com.example.kproyectofinal.Fragments.ProductDetails

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.kproyectofinal.BaseDatos.ProductCestaReferencia
import com.example.kproyectofinal.BaseDatos.ProductDao
import com.example.kproyectofinal.BaseDatos.TiendaBBDD
import com.example.kproyectofinal.Entidades.ProductEntity
import com.example.kproyectofinal.R
import com.example.kproyectofinal.databinding.FragmentProductDetailsBinding
import com.example.kproyectofinal.mainModule.MainActivity


class FragmentProductDetails : Fragment() {

    private lateinit var mBinding: FragmentProductDetailsBinding
    private var mActivity: MainActivity? = null
    private var mSelectedProduct: ProductEntity? = null
    private lateinit var productDao: ProductDao
    private var mIdCesta: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? { // todo de que vista extiende
        productDao = TiendaBBDD.getInsance(requireContext()).productDao
        mBinding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mBinding.botonAnhadirCesta.setOnClickListener {
            insertProductCesta(mIdCesta, idProduct)
        }

//todo aqui ira la cesta

        mActivity = activity as? MainActivity
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true) // para el boton de retroceso
        //para cambiar el nombre de arriba
        mActivity?.supportActionBar?.title = getString(R.string.detallesProducto)

        setHasOptionsMenu(true) //que tenga acceso al menu
    }

    private fun insertProductCesta(mIdCesta: Long?, idProduct: Int?) {
        if (mIdCesta != null) {
            productDao.insertarProductoCesta(ProductCestaReferencia(mIdCesta.toInt(), idProduct!!))
        }else{

            Toast.makeText(activity, "error", Toast.LENGTH_SHORT).show()
        }
    }

/*
    private fun getProduct(id: Int) {

        doAsync {
            mSelectedProduct = productDao.getProductbyId(id)

            uiThread {
                if (mSelectedProduct != null) inlfarVista(mSelectedProduct!!)
            }
        }
    }

*/

    private fun inlfarVista(mSelectedProduct: ProductEntity) {
        with(mBinding) {
            tvNombre.setText(mSelectedProduct.name)
            tvCategoria.setText(mSelectedProduct.name)
            tvPrecio.setText(mSelectedProduct.unitPrice.toString())
            tvPrecioUnitario.setText(mSelectedProduct.priceKgL.toString())
            Glide.with(requireActivity())
                .load(mSelectedProduct.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(mBinding.imagen)
        }
    }

/*
    override fun onCreateOptionsMenu(
        menu: Menu,
        inflater: MenuInflater
    ) { //todo esto habria colocarlo en la
        //actividad principal
        inflater.inflate(R.menu.menu_save, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
*/
    //captar evento dentro del menu, para retroceder y para guardar

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            android.R.id.home -> {
                mActivity?.onBackPressed()
                true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }


}