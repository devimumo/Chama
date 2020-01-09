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
import kotlinx.android.synthetic.main.fragment_pay_loan.*
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 */
class PayLoan : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=         inflater.inflate(R.layout.fragment_pay_loan, container, false)

        get_contribution_statement_data(view)

        return view
    }




    private fun get_contribution_statement_data(view: View) {



        val requestQueue= Volley.newRequestQueue(view.context)
        val url="https://project-daudi.000webhostapp.com/ladies_group/loans_details.php"
        val stringRequest=object : StringRequest(Request.Method.POST,url, Response.Listener { response->





            Log.i("response", response)
            val jsonObject = JSONObject(response)
val server_response=jsonObject.getString("server_response")
            val data=jsonObject.getString("data")

            if (server_response.equals("1"))
            {
                val data_json_object=JSONObject(data)
                val loan_approval_id=data_json_object.getString("loan_approval_id")
                val loan_id=data_json_object.getString("loan_id")
                val disbursed_amount=data_json_object.getString("disbursed_amount")
                val loan_disbursement_transaction_id=data_json_object.getString("loan_disbursement_transaction_id")
                val disbursement_method=data_json_object.getString("disbursement_method")
                val disbursement_date=data_json_object.getString("disbursement_date")
                val loan_due_date=data_json_object.getString("loan_due_date")
                val loan_balances=data_json_object.getString("loan_balance")
                val loan_description=data_json_object.getString("loan_description")


                due_date.text=loan_due_date
                loan_balance.text=loan_balances

                pay_loan_progress_bar.visibility=View.GONE







            }
            else
            {


            }



        }, Response.ErrorListener {

            val error_handler= Volley_ErrorListener_Handler()
            error_handler.check_error(it,view)
            pay_loan_progress_bar.visibility=View.GONE

        })
        {

            override fun getParams(): MutableMap<String, String> {
                val params:MutableMap<String,String > =HashMap()

                val MyPreferences = "mypref"
                val sharedPreferences = view.context.getSharedPreferences(MyPreferences, Context.MODE_PRIVATE)
                // String session_id= sharedPreferences.getString("sessions_ids","");


                val phone_number = sharedPreferences.getString("phone_number", "")

                params.put("phone_number",phone_number.toString())



                return  params
            }


        }
        requestQueue.add(stringRequest)
    }



}
