package com.example.kproyectofinal.Entidades

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Cesta(@PrimaryKey (autoGenerate = true) val idCesta : Int = 0,
                 val estadoCesta: Boolean = false) {
}