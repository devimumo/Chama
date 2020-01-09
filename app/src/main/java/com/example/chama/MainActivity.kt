package com.example.chama

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.File
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val sharedPref = this?.getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultValue = false
        val app_state = sharedPref.getBoolean("app_state", defaultValue)
        if (app_state.equals(false))
        {
            setContentView(R.layout.activity_main)
submit.setOnClickListener {
    phone_verification(it)

}


        }
        else
        {

            val intent= Intent(this, LoginActivity::class.java)
            startActivity(intent)        }


    }

    fun phone_verification(view: View)
    {

submit.visibility=View.GONE
        progressBar.visibility=View.VISIBLE
        val queue by lazy { Volley.newRequestQueue(this) }

        //val queue = Volley.newRequestQueue(this)
        val url = "https://project-daudi.000webhostapp.com/ladies_group/otp_validation.php"

        val stringrequest=object :StringRequest(Request.Method.POST,url,Response.Listener {

            val server_response= JSONObject(it)
            val response_server: String = server_response.getString("server_response")

         try {

             deleteCache(context = this)
             when (response_server)
             {
                 "success"->{

                     snack_bar("One time pin send to your email.",view)
                     progressBar.visibility=View.GONE


                     val intent= Intent(this, LoginActivity::class.java)
                     startActivity(intent)


                 }

                 "!phone_number"->{
                     snack_bar("Not a valid phone number.If it is,please contact your administrator",view)
                     progressBar.visibility=View.GONE
                     submit.visibility=View.VISIBLE



                 }

                 else->
                 {
                     snack_bar("Not a valid phone number.If it is,please contact your administrator",view)
                     progressBar.visibility=View.GONE
                     submit.visibility=View.VISIBLE

                 }
             }
         }catch ( e: Error)
         {

             Log.i("error",e.toString())

         }

        },Response.ErrorListener {


            if (it is TimeoutError || it is NoConnectionError) {
                snack_bar("No connection", view)
                progressBar.visibility=View.GONE
                submit.visibility=View.VISIBLE


                //This indicates that the reuest has either time out or there is no connection
            } else if (it is AuthFailureError) {
                snack_bar("No connection", view)
                progressBar.visibility=View.GONE
                submit.visibility=View.VISIBLE


            } else if (it is ServerError) {
                snack_bar("No connection", view)
                progressBar.visibility=View.GONE
                submit.visibility=View.VISIBLE


            } else if (it is NetworkError) {
                snack_bar("No connection", view)
                progressBar.visibility=View.GONE
                submit.visibility=View.VISIBLE


            } else if (it is ParseError) {
                snack_bar("No connection", view)
                progressBar.visibility=View.GONE
                submit.visibility=View.VISIBLE


            }


        })
        {


            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()

                val phone_number=phone_number.text

                params.put("phone_number", phone_number.toString())
                //  params.put("amount", amount.toString())



                return params
            }

        }
queue.add(stringrequest)


    }

    fun snack_bar(message:String,view: View)
    {

        val snack=Snackbar.make(view,message,Snackbar.LENGTH_LONG)
        snack.show()
    }


    fun deleteCache( context:Context) {
        try {
            val dir = context.getCacheDir();
            deleteDir(dir);
        } catch ( e: Error) {

        }
    }

    fun deleteDir( dir:File): Boolean {
        if (dir != null && dir.isDirectory()) {
            var children = dir.list();
            for (i in 0..children.size) {
                var success = deleteDir(File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile())
            return dir.delete();
        else {
            return false;
        }
    }

    fun login(view: View) {

        val intent= Intent(this, LoginActivity::class.java)
        startActivity(intent)


    }


}
