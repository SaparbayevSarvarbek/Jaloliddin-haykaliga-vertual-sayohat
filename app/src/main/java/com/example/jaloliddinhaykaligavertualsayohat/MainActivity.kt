package com.example.jaloliddinhaykaligavertualsayohat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.jaloliddinhaykaligavertualsayohat.databinding.ActivityMainBinding
import com.example.jaloliddinhaykaligavertualsayohat.fragment.DashboardFragment
import com.example.jaloliddinhaykaligavertualsayohat.fragment.MapFragment
import com.example.jaloliddinhaykaligavertualsayohat.fragment.MenuFragment
import com.example.jaloliddinhaykaligavertualsayohat.fragment.VirtualFragment

private lateinit var binding: ActivityMainBinding
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}