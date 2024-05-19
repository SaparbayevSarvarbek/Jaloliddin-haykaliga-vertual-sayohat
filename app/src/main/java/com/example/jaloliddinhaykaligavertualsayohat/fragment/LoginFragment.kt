    package com.example.jaloliddinhaykaligavertualsayohat.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.jaloliddinhaykaligavertualsayohat.Data.LoginData
import com.example.jaloliddinhaykaligavertualsayohat.R
import com.example.jaloliddinhaykaligavertualsayohat.databinding.FragmentLoginBinding


    private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class LoginFragment : Fragment() {
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
private lateinit var binding:FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val loginData=LoginData
        binding=FragmentLoginBinding.inflate(layoutInflater)
        binding?.apply {
            kirish.setOnClickListener {
                if(name.text.toString().lowercase() == "admin" && phoneNumber.text.toString() == "932853874"){
                   findNavController().navigate(R.id.adminFragment)
                }else if(name.text.toString() == loginData.name && phoneNumber.text.toString()==loginData.phoneNumber){
                    findNavController().navigate(R.id.homeFragment)
                }
                else Toast.makeText(requireContext(), "Ism yoki telefon raqamili foydalanuvchi yo'q", Toast.LENGTH_SHORT).show()
            }
            registation.setOnClickListener {
                findNavController().navigate(R.id.registationFragment)
            }
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}