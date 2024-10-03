package com.example.hoctap3.fragment

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.hoctap3.DoiPass
import com.example.hoctap3.LoginActivity
import com.example.hoctap3.MessHelpGuest
import com.example.hoctap3.R

class TaiKhoanGuestFragment : Fragment() {
    lateinit var txtHelp : TextView
    lateinit var txtDoiPass : TextView
    lateinit var txtDangxuat : TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tai_khoan_guest, container, false)
        txtHelp = view.findViewById(R.id.txt_help)
        txtDangxuat = view.findViewById(R.id.txt_out)
        txtDoiPass = view.findViewById(R.id.txt_doi_pass)
        txtHelp.setOnClickListener {
            val intent = Intent(requireContext(), MessHelpGuest::class.java)
            startActivity(intent)
        }
        txtDangxuat.setOnClickListener {
            val sharedPreferences = requireContext().getSharedPreferences("UserPref", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.clear() // Hoặc editor.remove("userId")
            editor.apply()

            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }
        txtDoiPass.setOnClickListener {
            val intent = Intent(requireContext(), DoiPass::class.java)
            startActivity(intent)
        }
        return view
    }
}