package com.example.enggineraplication

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.enggineraplication.login.loginActivity
import kotlinx.android.synthetic.main.activity_parent.*



class parentActivity : AppCompatActivity() {
    lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController: NavController
    lateinit var sharedPref: PreferenceHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent)
        //bottomnav
        navController = findNavController(R.id.hostFragmen)
        bottom_navigation.setupWithNavController(navController)

        //navupbutton
        appBarConfiguration = AppBarConfiguration(navController.graph, drawer_layout)

        NavigationUI.setupActionBarWithNavController(this, navController, drawer_layout)

        //Drawer Navigation
        NavigationUI.setupWithNavController(navigation_view, navController)


        navigation_view.setNavigationItemSelectedListener {
            when (it.itemId) {

                R.id.nav_about -> {
                    Toast.makeText(this, "menu about", Toast.LENGTH_LONG).show()
                }
                R.id.nav_help->{
                    Toast.makeText(this, "menu about", Toast.LENGTH_LONG).show()
                }
                R.id.nav_faq -> {
                    Toast.makeText(this, "menu faq", Toast.LENGTH_LONG).show()
                }
                R.id.nav_addacount -> {
                    Toast.makeText(this, "menu add account", Toast.LENGTH_LONG).show()
                }

                R.id.nav_logout -> logout()

            }
            return@setNavigationItemSelectedListener true

        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    private fun logout(){
        sharedPref = PreferenceHelper(this)

        val dialog = AlertDialog.Builder(this)
            .setTitle("User Confirmation")
            .setPositiveButton("Log Out") { jajal: DialogInterface?, which: Int ->
                sharedPref.clear()
                showmessage("keluar")
                val inten=Intent(this, loginActivity::class.java)
                startActivity(inten)
            }
            .setNegativeButton("Dismiss") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
        dialog.show()
    }

    private fun showmessage(message:String){
        Toast.makeText(applicationContext,message, Toast.LENGTH_SHORT).show()
    }



}