package com.example.hoctap3.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.hoctap3.fragment.MonAnFragment
import com.example.hoctap3.fragment.PhanhoiFragment
import com.example.hoctap3.fragment.TaikhoanFragment
import com.example.hoctap3.fragment.TheLoaiFragment
import com.example.hoctap3.fragment.YeucauFragment

class ViewAdminAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager,lifecycle){
    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> TheLoaiFragment()
            1-> MonAnFragment()
            2-> YeucauFragment()
            3-> PhanhoiFragment()
            else -> TaikhoanFragment()
        }
    }

}