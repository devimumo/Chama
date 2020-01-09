package com.example.chama

import android.content.Context
import android.view.View

class get_shared_prefernces()
{

    fun getSharedPreferences_data(key:String,view: View): String {

        val myPreferences = "mypref"
         val sharedPreferences = view.context.getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
        // String session_id= sharedPreferences.getString("sessions_ids","");


        val phone_number = sharedPreferences.getString(key, "")

        return phone_number.toString()
    }

}