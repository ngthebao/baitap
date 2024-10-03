package com.example.hoctap3.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.hoctap3.fragment.CartFragment
import com.example.hoctap3.fragment.FavoriteFragment
import com.example.hoctap3.fragment.HomeFragment
import com.example.hoctap3.fragment.MonAnFragment
import com.example.hoctap3.fragment.PhanhoiFragment
import com.example.hoctap3.fragment.TaiKhoanGuestFragment
import com.example.hoctap3.fragment.TaikhoanFragment
import com.example.hoctap3.fragment.TheLoaiFragment
import com.example.hoctap3.fragment.YeucauFragment

class ViewGuestAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager,lifecycle){
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> HomeFragment()
            1-> CartFragment()
            2-> FavoriteFragment()
            else -> TaiKhoanGuestFragment()
        }
    }

}