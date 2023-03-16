package com.example.dicegame

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation

import com.example.dicegame.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//         Inflate the layout for this fragment
        val binding : FragmentTitleBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_title,container,false)
        binding.newGameBtn.setOnClickListener {view:View ->
            Navigation.findNavController(view).navigate(R.id.action_titleFragment_to_gamePlayFragement5)
        }
        binding.about.setOnClickListener {
            val popUp = AboutDialogFragment.newInstance()
            popUp.show(childFragmentManager,"dialog")
        }
        return binding.root
    }




}