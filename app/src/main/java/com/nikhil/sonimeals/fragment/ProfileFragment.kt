package com.nikhil.sonimeals.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.nikhil.sonimeals.R
import com.nikhil.sonimeals.util.DrawerLocker

class ProfileFragment : Fragment() {

    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        (activity as DrawerLocker).setDrawerEnabled(true)
        sharedPrefs = (activity as FragmentActivity).getSharedPreferences("FoodApp", Context.MODE_PRIVATE)
        val txtProfileName: TextView = view.findViewById(R.id.tvName)
        val txtProfileMobileNumber: TextView = view.findViewById(R.id.tvMobile)
        val txtProfileEmailAddress: TextView = view.findViewById(R.id.tvEmail)
        val txtProfileAddress: TextView = view.findViewById(R.id.tvAddress)
//
        txtProfileName.text = sharedPrefs.getString("user_name", "Name")
        val mobile = "+91-${sharedPrefs.getString("user_mobile_number", null)}"
        txtProfileMobileNumber.text = mobile
        txtProfileEmailAddress.text = sharedPrefs.getString("user_email", "Email")
        txtProfileAddress.text = sharedPrefs.getString("user_address", "Address")
        return view
    }

}