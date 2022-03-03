package com.example.kproyectofinal.mainModule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kproyectofinal.BaseDatos.ProductDao
import com.example.kproyectofinal.BaseDatos.TiendaBBDD
import com.example.kproyectofinal.Entidades.ProductEntity
import com.example.kproyectofinal.FragmentProductDetails
import com.example.kproyectofinal.R
import com.example.kproyectofinal.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mAdapter: ProductAdapter
    private lateinit var mGridLayout: GridLayoutManager

    private lateinit var mMainViewModel: MainViewModel

    private lateinit var productDao: ProductDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        productDao = TiendaBBDD.getInsance(this).productDao

        mBinding.fab.setOnClickListener { launchEditFragment() }

        setupRecyclerView()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_save, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_send -> {
            Snackbar.make(
                mBinding.root,
                getString(R.string.cesta_enviada),
                Snackbar.LENGTH_SHORT
            )
                .show()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun launchEditFragment(args: Bundle? = null) {

        val fragment = FragmentProductDetails()
        if (args != null) fragment.arguments = args

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.addToBackStack(null) // todo se anade para el paso atras

        fragmentTransaction.add(R.id.containerMain, fragment)

        fragmentTransaction.commit()
        mBinding.fab.hide()
    }



    private fun setupRecyclerView() {

        mAdapter = ProductAdapter(mutableListOf(), this)
        mGridLayout = GridLayoutManager(this, 2)
        //getAllProducts()

        mBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = mGridLayout
            adapter = mAdapter
        }

    }


    private fun getAllProducts() {
        // val productDao = TiendaBBDD.getInsance(this).productDao
        doAsync {
            val products = productDao.getAllProducts()
            uiThread {
                mAdapter.setStores(products)
            }
        }
}



    // lo ideal seria que este producto se guarde en el vm y se pase al otro fragment
    override fun onClick(idProduct: Int) {
        val args = Bundle()
        args.putInt(
            getString(R.string.product_id),
            idProduct
        ) // preguntar si el producto ya esta en la cesta actual
        launchEditFragment(args)


        Toast.makeText(this,args.getLong(getString(R.string.idCesta)).toString(), Toast.LENGTH_SHORT)
            .show() // muestra una tostada

    }


    override fun onCestaProduct(productEntity: ProductEntity) {
        productEntity.isFavorite = !productEntity.isFavorite;

        doAsync {
            productDao.update(productEntity) //productDao()
            uiThread {
                mAdapter.update(productEntity)
            }
        }


    }


    /*todo avisar de que se va a borrar un producto, cuando se seleccione uno, que salga la vista de seleccion:
        -en todos los objetos de la lista abilitar el checkbox
        -a la hora de borrar :
            recorrer todo el listado y borrar los seleccionados
            crear un nuevo listado de seleccionados, problema de no saber cuando se quita un objeto de ese listado

    * */



    override fun onDeleteProduct(productEntity: ProductEntity) {

        doAsync {
            productDao.delete(productEntity)

            uiThread {
                mAdapter.delete(productEntity)
            }
        }
    }


/*

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
        var bbdd = TiendaBBDD.getInsance(this)
        doAsync {
            productos.forEach { bbdd.productDao.addProduct(it) }
        }
    }

*/



}