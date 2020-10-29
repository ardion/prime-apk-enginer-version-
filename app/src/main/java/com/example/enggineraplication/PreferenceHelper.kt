package com.example.enggineraplication

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper(context: Context) {
    private val Pref_Name="shared"
    private val sharedpref:SharedPreferences
    val editor:SharedPreferences.Editor

    init {
        sharedpref=context.getSharedPreferences(Pref_Name,Context.MODE_PRIVATE)
        editor=sharedpref.edit()
    }

    fun put(key:String,value:String){
        editor.putString(key,value)
            .apply()
    }

    fun getString(key:String):String?{
       return  sharedpref.getString(key,null)
    }

    fun put(key:String,value:Boolean){
        editor.putBoolean(key,value)
            .apply()
    }

    fun getBoolean(key:String):Boolean{
        return  sharedpref.getBoolean(key,false)
    }

    fun clear(){
        editor.clear()
            .apply()
    }
}