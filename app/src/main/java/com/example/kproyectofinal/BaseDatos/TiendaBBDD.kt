package com.example.kproyectofinal.BaseDatos

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kproyectofinal.Entidades.Cesta
import com.example.kproyectofinal.Entidades.ProductEntity

@Database(
    entities = [
        ProductEntity::class,
        Cesta::class,
        ProductCestaReferencia::class
    ],
    version = 1
)
abstract class TiendaBBDD: RoomDatabase() {
    abstract val productDao: ProductDao
    //abstract val cestaDao: CestaDao

    companion object{
        @Volatile
        private var INSTANCIA: TiendaBBDD? = null

        fun getInsance(context: Context): TiendaBBDD{
            synchronized(this){
                return INSTANCIA?: Room.databaseBuilder(
                    context.applicationContext,
                    TiendaBBDD::class.java,
                    "tienda_bd"
                ).build().also{
                    INSTANCIA = it

                }
            }

        }
    }

}