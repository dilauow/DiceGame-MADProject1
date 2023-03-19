package com.example.dicegame


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import com.example.dicegame.databinding.ActivityMainBinding

//the video link below
//https://drive.google.com/file/d/13zxOg0ih-h3YxdpoNSDqmwAQQiwpjbUd/view?usp=sharing

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout : DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : ActivityMainBinding= DataBindingUtil.setContentView(this, R.layout.activity_main)
        drawerLayout = binding.drawerLayout

//        set up navcontroller
        val navigationController = this.findNavController(R.id.NavHostFragment)
    }


}