package com.example.jaloliddinhaykaligavertualsayohat.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jaloliddinhaykaligavertualsayohat.Data.PersonData
import com.example.jaloliddinhaykaligavertualsayohat.R
import com.example.jaloliddinhaykaligavertualsayohat.databinding.FragmentDashboardBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DashboardFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
private lateinit var binding:FragmentDashboardBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val data=PersonData
     binding=FragmentDashboardBinding.inflate(layoutInflater)
        binding.apply {
            davlat.text=data.davlatNomi
            tugilishi.text=data.tugilishi
            vafoti.text=data.vafoti
            sulola.text=data.sulola
            otasi.text=data.otasi
            onasi.text=data.onasi
            din.text=data.dini
            batafsil.text=data.batafsil
        }
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DashboardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}