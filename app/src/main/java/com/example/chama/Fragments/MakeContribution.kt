package com.example.chama.Fragments


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.chama.R
import com.example.chama.Volley_ErrorListener_Handler
import kotlinx.android.synthetic.main.make_contributions.*
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 */
class MakeContribution : Fragment() {

    val context=this

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment


        val view= inflater.inflate(R.layout.make_contributions, container, false)
check_balance(view)




        return view
    }

private  fun check_balance( view: View)
{

    val requestQueue=Volley.newRequestQueue(view.context)
    val url="https://project-daudi.000webhostapp.com/ladies_group/check_contribution_balance.php"
    val stringRequest=object :StringRequest(Request.Method.POST,url,Response.Listener {response->


        Log.i("response",response)
        val jsonObject=JSONObject(response)

       // val jsonArray=jsonObject.getJSONArray("contribution_statement")


            /////////////////////////////////////////////////////////////
            val balance=jsonObject.getInt("obligation_amount")
            val date_due=jsonObject.getString("due_date")
            val bal=balance>0


            if (balance.equals(0))
            {
                balance_amount.text="kshs "+balance.toString()
                due_date.text=date_due.toString()

                with_balance.visibility=View.VISIBLE
                make_contributions_progressbar_layout.visibility=View.GONE


            }
            else if (balance>0)
            {

                balance_amount.text="kshs "+balance.toString()
                due_date.text=date_due.toString()

                with_balance.visibility=View.VISIBLE
                make_contributions_progressbar_layout.visibility=View.GONE


            }












    },Response.ErrorListener {

        val error_handler=Volley_ErrorListener_Handler()
        error_handler.check_error(it,view)
        make_contributions_progressbar_layout.visibility=View.GONE

    })
    {

        override fun getParams(): MutableMap<String, String> {
            val params:MutableMap<String,String > =HashMap()

            val MyPreferences = "mypref"
            val sharedPreferences = view.context.getSharedPreferences(MyPreferences, Context.MODE_PRIVATE)
            // String session_id= sharedPreferences.getString("sessions_ids","");


            val phone_number = sharedPreferences.getString("phone_number", "")

            params.put("phone_number",phone_number.toString())
            params.put("monthly_contribution","monthly_contribution")



        return  params
        }


    }
    requestQueue.add(stringRequest)
}

    private fun set_statement()
    {

    }
}
