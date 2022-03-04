package com.example.kproyectofinal.Fragments.Cesta

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kproyectofinal.BaseDatos.ProductDao
import com.example.kproyectofinal.databinding.FragmentCestaBinding
import com.example.kproyectofinal.mainModule.MainActivity
import com.google.android.material.bottomappbar.BottomAppBar


class FragmentCesta : Fragment() {

    private lateinit var mBinding: FragmentCestaBinding
    private var mActivity: MainActivity? = null
    private lateinit var productDao: ProductDao
    private var mIdCesta: Int? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { // todo de que vista extiende
        mBinding = FragmentCestaBinding.inflate(inflater, container, false)
        setOnClickers()
        return mBinding.root
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


}



