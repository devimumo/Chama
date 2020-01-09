package com.example.chama

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.chama.Security.Encrypt
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class LoginActivity : AppCompatActivity() {


    private var attempts = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun login(view: View) {

        val username_value=user_name.text.toString()
        val password=passWord.text.toString()

        if (TextUtils.isEmpty(username_value))
        {
            user_name.setError("Username required ")
        }
        else if (username_value.length!=9)
        {

            user_name.setError("Wrong number format")
        }
        else if (TextUtils.isEmpty(password))
        {
            passWord.setError("password required ")


        }
        else
        {
check_login(view)

        }

    }

    fun check_login(view: View?) {
        login_progressBar.visibility=View.VISIBLE
        login_loginScreen.visibility=View.GONE
    val requestQueue = Volley.newRequestQueue(applicationContext)

        val login_url =   "https://project-daudi.000webhostapp.com/ladies_group/login.php"
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST,
            login_url,
            Response.Listener { response ->
                Log.i("Response", response)
                try {
                    login_in_function(response)
                    // login_function(progressDialog,response);
                } catch (e: JSONException) {
                    e.printStackTrace()
                    //   responses.equals(login_response);
                    Log.i("JSONEXCEPTION", e.toString())
                    login_progressBar.visibility=View.GONE
                    login_loginScreen.visibility=View.VISIBLE
                }
            },
            Response.ErrorListener {


                val err=Volley_ErrorListener_Handler()

                err.check_error(it,view)
                login_progressBar.visibility= View.GONE
                login_loginScreen.visibility= View.VISIBLE

            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> =
                    HashMap()
                val encrypt = Encrypt()
                val MyPreferences = "mypref"
                val sharedPreferences =
                    getSharedPreferences(MyPreferences, Context.MODE_PRIVATE)
                // String session_id= sharedPreferences.getString("sessions_ids","");


                val session_idss = sharedPreferences.getString("session_ids", "")


                params["session_ids"] = session_idss!!
                params["phone_number"] = "+254" + user_name.text.toString()
                try {
                    params["encrypted_password"] = encrypt.encrypt(passWord.text.toString())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return params
            }
        }
        requestQueue.add(stringRequest)
    }


    @Throws(JSONException::class)
    private fun login_in_function(  response: String  )
    {
        val jsonObject_response = JSONObject(response)
        val responses = jsonObject_response.getString("response")

        if (responses == "wrong_pass") {
            attempts--
            if (attempts < 3 && attempts != 0) { //bothe the username and password are false
                Toast.makeText(
                    applicationContext,
                    "Wrong credentials.Try again",
                    Toast.LENGTH_SHORT
                ).show()
                login_progressBar.visibility=View.GONE
                login_loginScreen.visibility=View.VISIBLE

            } else { //both the username and password are false

                          finish()
            }
        } else if (responses == "!phone_number") {
            Toast.makeText(this, "Wrong credentials.Enter the correct username", Toast.LENGTH_LONG)
                .show()
            login_progressBar.visibility=View.GONE
            login_loginScreen.visibility=View.VISIBLE

        } else if (responses == "exists") {
            finish()
        } else {

            //////{"user_data":{"user_data":[{"firstname":"dausi","lastname":"mumo","email":"devimumo@gmail.com"}]},"session_id":"b1850067893af7e877b9d00e2a7adaa8"}
            val jsonObject = JSONObject(response)
            val responsed = jsonObject.getString("response")
            val session_id = jsonObject.getString("session_id")


            if (responsed == "successful") {
                Toast.makeText(applicationContext, "successful", Toast.LENGTH_LONG).show()
                val MyPreferences = "mypref"
                val sharedPreferences =  getSharedPreferences(MyPreferences, Context.MODE_PRIVATE)
                // String session_ide= sharedPreferences.getString("sessions_ids","");
                val editor = sharedPreferences.edit()
                // String phone_number_= phone_number.getText().toString().trim();
                val user_name: String = user_name.getText().toString()
                editor.remove("sessions_ids")
                editor.remove("phone_number")
                editor.putString("sessions_ids", session_id)
                editor.putString("phone_number", user_name)
                // editor.putString("phone_numbers",phone_number_);
                editor.commit()
              //  get_data(response)
                login_progressBar.visibility=View.GONE
                login_loginScreen.visibility=View.VISIBLE


                val intent= Intent(this,DashBoard::class.java)
                startActivity(intent)
            }
            ///////
        }
    }

    fun snack_bar(error: String?, view: View?) {
        val mysnackbar = Snackbar.make(view!!, error!!, Snackbar.LENGTH_LONG)
        mysnackbar.show()
    }


}
