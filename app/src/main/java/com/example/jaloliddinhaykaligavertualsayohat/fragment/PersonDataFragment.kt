package com.example.jaloliddinhaykaligavertualsayohat.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.jaloliddinhaykaligavertualsayohat.Data.PersonData
import com.example.jaloliddinhaykaligavertualsayohat.R
import com.example.jaloliddinhaykaligavertualsayohat.databinding.FragmentLoginBinding
import com.example.jaloliddinhaykaligavertualsayohat.databinding.FragmentPersonDataBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PersonDataFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PersonDataFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var binding: FragmentPersonDataBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val data = PersonData
        binding = FragmentPersonDataBinding.inflate(layoutInflater)
        binding.apply {
            davlat.setText(data.davlatNomi)
            tugilishi.setText(data.tugilishi)
            vafoti.setText(data.vafoti)
            sulola.setText(data.sulola)
            otasi.setText(data.otasi)
            onasi.setText(data.onasi)
            din.setText(data.dini)
            batafsil.setText(data.batafsil)
            saqlash.setOnClickListener {
                data.davlatNomi = davlat.text.toString()
                data.tugilishi = tugilishi.text.toString()
                data.vafoti = vafoti.text.toString()
                data.sulola = sulola.text.toString()
                data.otasi = otasi.text.toString()
                data.onasi = onasi.text.toString()
                data.dini = din.text.toString()
                data.batafsil = batafsil.text.toString()
                findNavController().navigateUp()
            }
        }
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PersonDataFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}