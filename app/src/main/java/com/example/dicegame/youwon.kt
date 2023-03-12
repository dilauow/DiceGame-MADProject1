package com.example.dicegame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.dicegame.databinding.FragmentYouwonBinding


class youwon : Fragment() {
    lateinit var binding : FragmentYouwonBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_youwon, container,false)
        binding.playagain.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_youwon_to_titleFragment)

        }

        return binding.root
    }


}