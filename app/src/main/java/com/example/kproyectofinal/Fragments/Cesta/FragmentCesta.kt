package com.example.kproyectofinal.Fragments.Cesta

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
import com.example.kproyectofinal.mainModule.MainViewModel
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class FragmentCesta(mMainViewModel: MainViewModel) : Fragment(), CestaOnClickListener {

    private lateinit var mBinding: FragmentCestaBinding
    private var mActivity: MainActivity? = null
    private lateinit var mAdapter: CestaProductAdapter
    private lateinit var mGridLayout: GridLayoutManager
    private lateinit var mCestaViewModel: CestaViewModel
    private lateinit var mMainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    //todo mirar el observer
    private fun setupViewModel() {

        mMainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mCestaViewModel = ViewModelProvider(requireActivity())[CestaViewModel::class.java]
        mCestaViewModel.getProducts().observe(requireActivity()) { products ->
            mAdapter.setProducts(products)
            mCestaViewModel.calcularTotal()
        }
        mCestaViewModel.totalCompra.observe(requireActivity()) { total ->
            val totalString = total.toString()
            mBinding.tvTotalCompra.setText(totalString + "€")
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

                    var cestaActual = mCestaViewModel.cestaActual.value!!
                    cestaActual.estadoCesta = true
                    val cesta = Cesta()
                    mCestaViewModel.cambiarCestaActual(cestaActual,cesta) // cambia la base de datos, pero como hacerle al viewModel
                    Toast.makeText(requireContext(), getString(R.string.cesta_enviada), Toast.LENGTH_LONG)
                    requireActivity().onBackPressed()
                }
                .setNegativeButton(R.string.dialog_delete_cancel, null)
                .show()
        }
    }


    fun enviarCorreo(){
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/html"
        intent.putExtra(Intent.EXTRA_EMAIL, "emailaddress@emailaddress.com")
        intent.putExtra(Intent.EXTRA_SUBJECT, "Cesta")

        intent.putExtra(Intent.EXTRA_TEXT, "La cesta estará preparada en 2 horas. Coste total: "
                +mCestaViewModel.totalCompra.value!!)
        startActivity(Intent.createChooser(intent, "Send Email"))



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
        MaterialAlertDialogBuilder(requireContext())
        .setTitle(R.string.dialog_delete_title)
            .setPositiveButton(R.string.dialog_delete_confirm) { _, _ ->
                mCestaViewModel.deleteProduct(productEntity)
            }
            .setNegativeButton(R.string.dialog_delete_cancel, null)
            .show()

    }


}



