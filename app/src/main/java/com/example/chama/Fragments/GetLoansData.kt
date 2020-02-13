package com.example.chama.Fragments


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.chama.Adapters.Loans_Statement_Adapter
import com.example.chama.DataClasses.Loans_statement_data
import com.example.chama.R
import com.example.chama.Volley_ErrorListener_Handler
import kotlinx.android.synthetic.main.my_contributions.*
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 */
class GetLoansData : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.my_loan_statement, container, false)



        get_loan_statement_data(view)
//my_contributions_progressbar_layout.visibility=View.GONE


        return view
    }




    private fun get_loan_statement_data(view: View) {

        val statement_datas = ArrayList<Loans_statement_data>()


        val requestQueue= Volley.newRequestQueue(view.context)
        val url="https://project-daudi.000webhostapp.com/ladies_group/loans_statement_data.php"
        val stringRequest=object : StringRequest(Request.Method.POST,url, Response.Listener { response->


            val MyPreferences = "mypref"
            val sharedPreferences =
                view.context.getSharedPreferences(MyPreferences, Context.MODE_PRIVATE)
            // String session_id= sharedPreferences.getString("sessions_ids","")


            // val response = sharedPreferences.getString("response", "")


            Log.i("response", response)
            val jsonObject = JSONObject(response)
            val data = jsonObject.getJSONArray("loan_statement")


            for (i in 0..data.length() - 1) {

                val contributions_data = data.getJSONObject(i)
                val contribution_dataas= Loans_statement_data(
                    contributions_data.getString("arrears_payed_transaction_date"),
                    contributions_data.getString("transaction_id"),
                    contributions_data.getString("paid"),
                    contributions_data.getInt("balance"),
                    contributions_data.getString("message")
                )

                statement_datas.add(contribution_dataas)

                recycler_view.layoutManager = LinearLayoutManager(view.context)

                val adap = Loans_Statement_Adapter(statement_datas, view.context)
                adap.notifyDataSetChanged()
                recycler_view.adapter = adap

                my_contributions_progressbar_layout.visibility = View.GONE


            }


        }, Response.ErrorListener {

            val error_handler= Volley_ErrorListener_Handler()
            error_handler.check_error(it,view)
            my_contributions_progressbar_layout.visibility=View.GONE

        })
        {

            override fun getParams(): MutableMap<String, String> {
                val params:MutableMap<String,String > =HashMap()

                val MyPreferences = "mypref"
                val sharedPreferences = view.context.getSharedPreferences(MyPreferences, Context.MODE_PRIVATE)
                // String session_id= sharedPreferences.getString("sessions_ids","");


                val phone_number = sharedPreferences.getString("phone_number", "")

                params.put("phone_number",phone_number.toString())
                params.put("description","loan_data_request")



                return  params
            }


        }
        requestQueue.add(stringRequest)
    }


}

