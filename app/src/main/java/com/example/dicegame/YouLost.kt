package com.example.dicegame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.dicegame.databinding.FragmentYouLostBinding

class YouLost : Fragment() {
    lateinit var binding :  FragmentYouLostBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_you_lost, container,false)
        binding.playagainLost.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_youLost_to_titleFragment)

        }

        return binding.root
    }


}