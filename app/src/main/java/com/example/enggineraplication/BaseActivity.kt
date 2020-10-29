package com.example.enggineraplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    open fun findviews() {}
    open fun initlisteners() {}
    open fun get(){}
    open fun layoutid(Id: Int) {
        setContentView(Id)
    }

    var start:Intent?=null
    protected inline fun <reified ClassActivity>inten(context: Context){
        start = Intent(context,ClassActivity::class.java)
    }
    open fun putextra(name:String,value:String){
        start?.putExtra(name,value)
    }

    fun setErrorDialog(title: String?, message: String?) {
        val dialog = AlertDialog.Builder(this)
            .setTitle(title)
            .setIcon(R.drawable.ic_round_error_outline_24)
            .setMessage(message)
            .setCancelable(true)
            .setPositiveButton("Ok") { dialog, id ->
                dialog.dismiss()}
        dialog.show()
    }


}