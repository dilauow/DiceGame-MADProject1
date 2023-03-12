package com.example.dicegame


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.dicegame.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout : DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        val binding : ActivityMainBinding= DataBindingUtil.setContentView(this, R.layout.activity_main)
        drawerLayout = binding.drawerLayout

//        set up navcontroller
        val navigationController = this.findNavController(R.id.NavHostFragment)
//        NavigationUI.setupActionBarWithNavController(this,navigationController,drawerLayout)

//        var popUpBtn: Button = findViewById(R.id.about)
//        popUpBtn.setOnClickListener {
//            onButtonShowPopupWindowClick(it)
//        }
    }



    @SuppressLint("ClickableViewAccessibility")
    fun onButtonShowPopupWindowClick(view: View?) {
        // inflate the layout of the popup window
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.popup, null)

        // create the popup window
        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = true // lets taps outside the popup also dismiss it
        val popupWindow = PopupWindow(popupView, width, height, focusable)

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)

        // dismiss the popup window when touched
        popupView.setOnTouchListener { v, event ->
            popupWindow.dismiss()
            true
        }
    }

//    TODO generate Random Numbers
//    TODO Roll 5 dices Human computer Separately
//    TODO Handle the 3 Rolls
//    TODO Make the Computer Roll

//    TODO Marks Tied Situation at same Roll Values

}