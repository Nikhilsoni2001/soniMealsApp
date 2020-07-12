package com.nikhil.sonimeals.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.nikhil.sonimeals.R

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
//
        val txtProfileName: TextView = view.findViewById(R.id.tvName)
        val txtProfileMobileNumber: TextView = view.findViewById(R.id.tvMobile)
        val txtProfileEmailAddress: TextView = view.findViewById(R.id.tvEmail)
        val txtProfileAddress: TextView = view.findViewById(R.id.tvAddress)
//
//        txtProfileName.text = sharedPreferences?.getString("user_name", "Name")
//        txtProfileMobileNumber.text = sharedPreferences?.getString("user_number", "Mobile Number")
//        txtProfileEmailAddress.text = sharedPreferences?.getString("user_email", "Email")
//        txtProfileAddress.text = sharedPreferences?.getString("user_address", "Address")
        return view
    }

}