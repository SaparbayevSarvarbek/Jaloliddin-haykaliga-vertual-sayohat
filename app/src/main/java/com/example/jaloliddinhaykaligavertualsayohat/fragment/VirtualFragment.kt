package com.example.jaloliddinhaykaligavertualsayohat.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jaloliddinhaykaligavertualsayohat.R
import com.example.jaloliddinhaykaligavertualsayohat.databinding.FragmentVirtualBinding
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback
import com.google.android.gms.maps.StreetViewPanorama
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment
import com.google.android.gms.maps.model.LatLng


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class VirtualFragment : Fragment(){
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
private lateinit var binding:FragmentVirtualBinding
private var streetViewPanorama:StreetViewPanorama?=null
    private var a=false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentVirtualBinding.inflate(layoutInflater)
        val position=LatLng(41.551676, 60.631407)
        val streetViewPanoramaFragment=childFragmentManager.findFragmentById(R.id.street_view) as SupportStreetViewPanoramaFragment?
        streetViewPanoramaFragment?.getStreetViewPanoramaAsync{panorama->
            panorama.setPosition(position)
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            VirtualFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}