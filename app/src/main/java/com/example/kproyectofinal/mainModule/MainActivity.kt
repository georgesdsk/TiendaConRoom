package com.example.kproyectofinal.mainModule

import android.app.PendingIntent.getActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kproyectofinal.BaseDatos.ProductDao
import com.example.kproyectofinal.BaseDatos.TiendaBBDD
import com.example.kproyectofinal.Entidades.Cesta
import com.example.kproyectofinal.Entidades.ProductEntity
import com.example.kproyectofinal.Fragments.Cesta.CestaViewModel
import com.example.kproyectofinal.Fragments.Cesta.FragmentCesta
import com.example.kproyectofinal.Fragments.ProductDetails.FragmentProductDetails
import com.example.kproyectofinal.Fragments.ProductDetails.ProductDetailsViewModel
import com.example.kproyectofinal.R
import com.example.kproyectofinal.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mAdapter: ProductAdapter
    private lateinit var mGridLayout: GridLayoutManager

    private lateinit var mMainViewModel: MainViewModel
    private lateinit var mProductDetailsViewModel: ProductDetailsViewModel
    private lateinit var mCestaViewModel: CestaViewModel

    private lateinit var productDao: ProductDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        productDao = TiendaBBDD.getInsance(this).productDao


        setupViewModel()
        setupRecyclerView()
        //insertarCesta()

    }



    private fun insertarCesta() {
        mMainViewModel.insertarCesta(Cesta())
    }

    private fun setupViewModel() {
        mMainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mMainViewModel.getProducts().observe(this) { products ->
            mAdapter.setProducts(products)
        }
        mMainViewModel.isLoading.observe(this, Observer {
            mBinding.progressBar.isVisible = it
        })
        mMainViewModel.getCesta().value
        mMainViewModel.setFragment("Inicio")

        mProductDetailsViewModel = ViewModelProvider(this)[ProductDetailsViewModel::class.java]
        mCestaViewModel = ViewModelProvider(this)[CestaViewModel::class.java]

//        mCestaViewModel.cestaActual.observe(this){cesta->
//            mMainViewModel.setCesta(cesta)
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_save, menu)
        return true
    }


    //la barra superior
    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            mMainViewModel.setFragment("Inicio")
            this.supportActionBar?.title = getString(R.string.productos)
            this.onBackPressed()
            true
        }
        R.id.action_send -> {
            if (!mMainViewModel.getFragment().equals("cesta")) { // para que no se pueda entrar desde cesta
                launchCestaFragment()
            }
            true
        }
        else -> super.onOptionsItemSelected(item)
    }


    private fun launchProductFragment(productEntity: ProductEntity = ProductEntity()) {
        mMainViewModel.setFragment("product")
        mProductDetailsViewModel.setProdctoActual(productEntity)
        mMainViewModel.getCesta().value
        mProductDetailsViewModel.setCestaActual(mMainViewModel.cestaActual.value!!)

        val fragment = FragmentProductDetails();
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.addToBackStack("producto") // todo se anade para el paso atras
        fragmentTransaction.add(R.id.containerMain, fragment)
        fragmentTransaction.commit()
    }

    private fun launchCestaFragment() {
        //mCestaViewModel.setCestaActual(mMainViewModel.cestaActual.value!!)
        mMainViewModel.setFragment("cesta")
        val fragment = FragmentCesta()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.addToBackStack("cesta") // todo se anade para el paso atras
        fragmentTransaction.add(R.id.containerMain, fragment)
        fragmentTransaction.commit()
        mBinding.fab.hide()
    }

    private fun setupRecyclerView() {
        mAdapter = ProductAdapter(mutableListOf(), this)
        mGridLayout = GridLayoutManager(this, 2)

        mBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = mGridLayout
            adapter = mAdapter
        }
    }

    // lo ideal seria que este producto se guarde en el vm y se pase al otro fragment
    override fun onClick(productEntity: ProductEntity) {
        launchProductFragment(productEntity)

    }

    override fun onCestaProduct(productEntity: ProductEntity) {
        Snackbar.make(
            mBinding.root,
            getString(R.string.todo),
            Snackbar.LENGTH_SHORT
        )
            .show()
    }

    private fun insertarProductos() {
        val productos = listOf(
            ProductEntity(
                name = "Patata",
                image = "https://static.wikia.nocookie.net/minecraftpe/images/1/1e/New_Baked_PotatoB.png/revision/latest/scale-to-width-down/300?cb=20200615142618&path-prefix=es",
                priceKgL = 2.0, unitPrice = 1.0, quantity = 1
            ),
            ProductEntity(
                name = "Zanahoria",
                image = "https://static.wikia.nocookie.net/minecraftpe/images/3/31/Zanahoria.png/revision/latest/scale-to-width-down/150?cb=20140429154232&path-prefix=es",
                priceKgL = 4.0, unitPrice = 3.0, quantity = 2
            ),
            ProductEntity(
                name = "Pan",
                image = "https://static.wikia.nocookie.net/minecraftpe/images/c/ca/Pan.png/revision/latest/scale-to-width-down/150?cb=20140426181947&path-prefix=es",
                priceKgL = 3.0, unitPrice = 0.5, quantity = 1
            ),
            ProductEntity(
                name = "Tarta",
                image = "https://static.wikia.nocookie.net/minecraftpe/images/c/c7/Pastel.png/revision/latest/scale-to-width-down/150?cb=20200622172745&path-prefix=es",
                priceKgL = 10.0, unitPrice = 10.0, quantity = 1
            ),
            ProductEntity(
                name = "Amapola",
                image = "https://static.wikia.nocookie.net/minecraftpe/images/a/a4/Amapola.png/revision/latest/scale-to-width-down/150?cb=20140525035412&path-prefix=es",
                priceKgL = 20.0, unitPrice = 1.0, quantity = 1
            ),
            ProductEntity(
                name = "Zanahoria", image = "",
                priceKgL = 2.0, unitPrice = 1.0, quantity = 1
            ),
            ProductEntity(
                name = "Tierra",
                image = "https://static.wikia.nocookie.net/minecraftpe/images/7/7c/Tierra.png/revision/latest/scale-to-width-down/150?cb=20190923040636&path-prefix=es",
                priceKgL = 0.1, unitPrice = 0.1, quantity = 1
            ),
            ProductEntity(
                name = "Arco",
                image = "https://static.wikia.nocookie.net/minecraftpe/images/d/db/Arco.gif/revision/latest/scale-to-width-down/150?cb=20140201200529&path-prefix=es",
                priceKgL = 20.0, unitPrice = 40.0, quantity = 1
            ),
            ProductEntity(
                name = "Reloj",
                image = "https://static.wikia.nocookie.net/minecraftpe/images/8/83/Clock_JE2_BE1.gif/revision/latest/scale-to-width-down/160?cb=20211216235526&path-prefix=es",
                priceKgL = 200.0, unitPrice = 200.0, quantity = 1
            ),
        )
        productos.forEach { x -> mMainViewModel.insertarProducto(x) }
    }



/*
    override fun onCestaProduct(productEntity: ProductEntity) {
        productEntity.isFavorite = !productEntity.isFavorite;

        doAsync {
            appBbDD.database.productDao.update(productEntity) //productDao()
            uiThread {
                mAdapter.update(productEntity)
            }
        }


    }
*/

    /*todo avisar de que se va a borrar un producto, cuando se seleccione uno, que salga la vista de seleccion:
        -en todos los objetos de la lista abilitar el checkbox
        -a la hora de borrar :
            recorrer todo el listado y borrar los seleccionados
            crear un nuevo listado de seleccionados, problema de no saber cuando se quita un objeto de ese listado

    * */


/*
    override fun onDeleteProduct(productEntity: ProductEntity) {

        doAsync {
            productDao.delete(productEntity)

            uiThread {
                mAdapter.delete(productEntity)
            }
        }
    }
*/


    //insertarProductos()
/*
        mBinding.btnSave.setOnClickListener {
            val product = ProductEntity(
                name = mBinding.etName.text.toString().trim()) // para quitar los espacio blanco

            Thread {dfdfs
                ProductAplication.database.productDao().addProduct(product)
            }.start()

            mAdapter.add(product)
        }
*/


}