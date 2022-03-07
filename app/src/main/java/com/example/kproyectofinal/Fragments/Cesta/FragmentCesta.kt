package com.example.kproyectofinal.Fragments.Cesta

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kproyectofinal.BaseDatos.ProductDao
import com.example.kproyectofinal.Entidades.Cesta
import com.example.kproyectofinal.Entidades.ProductEntity
import com.example.kproyectofinal.R
import com.example.kproyectofinal.databinding.FragmentCestaBinding
import com.example.kproyectofinal.mainModule.MainActivity
import com.example.kproyectofinal.mainModule.ProductAdapter
import com.google.android.material.bottomappbar.BottomAppBar


class FragmentCesta : Fragment(), CestaOnClickListener {

    private lateinit var mBinding: FragmentCestaBinding
    private var mActivity: MainActivity? = null
    private lateinit var mAdapter: CestaProductAdapter
    private lateinit var mGridLayout: GridLayoutManager

    private lateinit var mCestaViewModel: CestaViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }
//todo mirar el observer
    private fun setupViewModel() {
        mCestaViewModel = ViewModelProvider(requireActivity())[CestaViewModel::class.java]
        mCestaViewModel.getProducts().observe(requireActivity()) { products ->
            mAdapter.setProducts(products)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { // todo de que vista extiende
        mBinding = FragmentCestaBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickers()
        setupRecyclerView()
        setupViewModel()
        inlfarVista(mCestaViewModel.cestaActual.value!!)


        mActivity = activity as? MainActivity
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true) // para el boton de retroceso
        //para cambiar el nombre de arriba
        mActivity?.supportActionBar?.title = getString(R.string.cesta)
        setHasOptionsMenu(true) //que tenga acceso al menu

    }


    private fun inlfarVista(cesta: Cesta) {
        with(mBinding){
            tvTotalCompra
        }
    }



    private fun setupRecyclerView() {

        mAdapter = CestaProductAdapter(mutableListOf(), this)
        mGridLayout = GridLayoutManager(context, 1)

        mBinding.recyclerViewCesta.apply {
            setHasFixedSize(true)
            layoutManager = mGridLayout
            adapter = mAdapter
        }

    }


    fun setOnClickers(){
        mBinding.fab.setOnClickListener {
            if (mBinding.bottomAppBar.fabAlignmentMode == BottomAppBar.FAB_ALIGNMENT_MODE_CENTER) {
                mBinding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
            } else {
                mBinding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            }
        }
    }
/*
// realmente no hace falta
    override fun onClick(productEntity: ProductEntity) {
        TODO("Not yet implemented")
    }

    override fun onLikeProduct(productEntity: ProductEntity) {
        TODO("Not yet implemented")
    }
*/

    override fun onDeleteProductFromCesta(productEntity: ProductEntity) {

        //preguntar ,  cambiar la lista
        mCestaViewModel.deleteProduct(productEntity)
    }


}



