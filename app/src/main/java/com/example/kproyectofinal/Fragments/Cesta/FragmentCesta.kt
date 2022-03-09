package com.example.kproyectofinal.Fragments.Cesta

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kproyectofinal.Entidades.Cesta
import com.example.kproyectofinal.Entidades.ProductEntity
import com.example.kproyectofinal.R
import com.example.kproyectofinal.databinding.FragmentCestaBinding
import com.example.kproyectofinal.mainModule.MainActivity
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.dialog.MaterialAlertDialogBuilder


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
        var tusmuertos : String = "no"

        mCestaViewModel.getProductList().observe(requireActivity(), ::onProductListLoaded)
        mCestaViewModel.loadProductList()

        mCestaViewModel.totalCompra.observe(requireActivity()) { total ->
            val totalString = total.toString()
            mBinding.tvTotalCompra.setText(totalString + "€")
        }
        mCestaViewModel.cestaActual.observe(requireActivity()) { cesta ->

        }
    }

    private fun onProductListLoaded(productList : List<ProductEntity>){
        //TODO setear adapter
        mAdapter.setProducts(productList)
        mCestaViewModel.calcularTotal()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View { // todo de que vista extiende
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
        with(mBinding) {
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


    fun setOnClickers() {
        mBinding.fab.setOnClickListener {
            mBinding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            mBinding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.enviar_cesta)
                .setPositiveButton(R.string.dialog_delete_confirm) { _, _ ->
                    enviarCorreo()
                    var cestaActual = mCestaViewModel.getCestaActuall().value!!
                    cestaActual.estadoCesta = true
                    mCestaViewModel.newCesta(cestaActual, Cesta())
                    mActivity?.supportActionBar?.title = getString(R.string.app_name)
                    requireActivity().onBackPressed()
                }
                .setNegativeButton(R.string.dialog_delete_cancel, null)
                .show()
        }
    }


    fun enviarCorreo() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/html"
        intent.putExtra(Intent.EXTRA_EMAIL, "emailaddress@emailaddress.com")
        intent.putExtra(Intent.EXTRA_SUBJECT, "Cesta")

        intent.putExtra(
            Intent.EXTRA_TEXT, "La cesta estará preparada en 2 horas. Coste total: "
                    + mCestaViewModel.totalCompra.value!!
        )
        startActivity(Intent.createChooser(intent, "Send Email"))


    }

    override fun onDeleteProductFromCesta(productEntity: ProductEntity) {

        //preguntar ,  cambiar la lista
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.dialog_delete_title)
            .setPositiveButton(R.string.dialog_delete_confirm) { _, _ ->
                mCestaViewModel.deleteProduct(productEntity)
            }
            .setNegativeButton(R.string.dialog_delete_cancel, null)
            .show()

    }


}



