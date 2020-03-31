package com.example.chama.Fragments


import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.chama.PendiD
import com.example.chama.R
import com.example.chama.Volley_ErrorListener_Handler
import kotlinx.android.synthetic.main.fragment_pay_loan.*
import kotlinx.android.synthetic.main.fragment_pay_loan.view.*
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




        view.loan_balance_layout.setOnClickListener {
//PendingLoanData()
   val intent= Intent(activity,PendiD::class.java)
    startActivity(intent)
    }
       view.dde.setOnClickListener {

           val payment_amount=loan_payment_amount.text.toString()
   alert_dialog("Confirm pay loan of amount "+payment_amount+" kshs \n via mpesa",phone_numbersss.text.toString(),payment_amount)

     }

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
                val phone_numbers=data_json_object.getString("phone_number")


                phone_numbersss.text=phone_numbers

                due_date.text=loan_due_date
                loan_balance.text=loan_balances

                val MyPreferences = "mypref"
                val sharedPreferences = context?.getSharedPreferences(MyPreferences, Context.MODE_PRIVATE)
                // String session_ide= sharedPreferences.getString("sessions_ids","");
                val editor = sharedPreferences?.edit()
                // String phone_number_= phone_number.getText().toString().trim();
                editor?.remove("loan_approval_id")
                editor?.remove("loan_id")
                editor?.remove("disbursed_amount")
                editor?.remove("loan_disbursement_transaction_id")
                editor?.remove("disbursement_method")
                editor?.remove("disbursement_date")
                editor?.remove("loan_due_date")
                editor?.remove("loan_description")


                editor?.putString("loan_approval_id", loan_approval_id)
                editor?.putString("loan_id", loan_id)
                editor?.putString("disbursed_amount", disbursed_amount)
                editor?.putString("loan_disbursement_transaction_id", loan_disbursement_transaction_id)
                editor?.putString("disbursement_method", disbursement_method)
                editor?.putString("disbursement_date", disbursement_date)
                editor?.putString("loan_due_date", loan_due_date)
                editor?.putString("loan_description", loan_description)

                // editor.putString("phone_numbers",phone_number_);
                editor?.commit()



                pay_loan_progress_bar.visibility=View.GONE

                loan_balance_layout.visibility=View.VISIBLE
                pay.visibility=View.VISIBLE






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

    fun pay_loan_from_mpesa(phone_number: String,amount: String) {


        val c=this.context
        val progressbar: ProgressDialog = ProgressDialog(this.context)
        progressbar.setMessage("Loading..........")
        progressbar.setCancelable(false)
        progressbar.show()
        pay_loan_progress_bar.visibility=View.VISIBLE

        val queue = Volley.newRequestQueue(this.context)


        val MyPreferences = "mypref"
        val sharedPreferences = c?.getSharedPreferences(MyPreferences, Context.MODE_PRIVATE)
        // String session_id= sharedPreferences.getString("sessions_ids","");


        val loan_id = sharedPreferences?.getString("loan_id", "")
        val url = "https://project-daudi.000webhostapp.com/ladies_group/lipa_online.php?loan_id="+loan_id+"&phone_number="+phone_number+"&amount="+amount

        val stringrequest=object :StringRequest(Request.Method.GET,url,Response.Listener { responsess->


            Log.i("responses",responsess)


            val server_response=JSONObject(responsess);
            val response_server: String = server_response.getString("server_response")

            when(response_server) {
                "!post_phone_number" -> {
                    pay_loan_progress_bar.visibility=View.GONE

                    Toast.makeText(this.context,"phone number needed", Toast.LENGTH_SHORT).show()


                }
                "invalid_phone_number" -> {
                    pay_loan_progress_bar.visibility=View.GONE
                    progressbar.dismiss()
                    Toast.makeText(this.context,"transaction in progress", Toast.LENGTH_SHORT).show()


                }
                "timeout" -> {
                    pay_loan_progress_bar.visibility=View.GONE
                    progressbar.dismiss()

                    Toast.makeText(this.context,"transaction timeout", Toast.LENGTH_SHORT).show()


                }
                "successful" -> {
                    pay_loan_progress_bar.visibility=View.GONE
                    progressbar.dismiss()

                    Toast.makeText(this.context,"transaction successful", Toast.LENGTH_SHORT).show()


                }
                "cancelled" -> {
                    pay_loan_progress_bar.visibility=View.GONE
                    progressbar.dismiss()

                    Toast.makeText(this.context,"transaction cancelled", Toast.LENGTH_SHORT).show()


                }
                "limited" -> {
                    pay_loan_progress_bar.visibility=View.GONE
                    progressbar.dismiss()

                    Toast.makeText(this.context,"transaction limited", Toast.LENGTH_SHORT).show()


                }


                else -> {
                    pay_loan_progress_bar.visibility=View.GONE
                    progressbar.dismiss()

                    Toast.makeText(this.context,"transaction timed out", Toast.LENGTH_SHORT).show()

                }
            }

        },Response.ErrorListener {
            Log.i("ErrorListener",it.toString())
            progressbar.dismiss()

        })


      /*  {

            @Throws(AuthFailureError::class)
            override fun getParams(): MutableMap<String, String> {
                val params = java.util.HashMap<String, String>()


                val MyPreferences = "mypref"
                val sharedPreferences = c?.getSharedPreferences(MyPreferences, Context.MODE_PRIVATE)
                // String session_id= sharedPreferences.getString("sessions_ids","");


                val loan_id = sharedPreferences?.getString("loan_id", "")

                params.put("phone_number", phone_number.toString())
                params.put("amount", amount.toString())
                params.put("loan_id", loan_id.toString())



                return params
            }
        }*/

        {}

        // DefaultRetryPolicy.DEFAULT_MAX_RETRIES
        queue.add(stringrequest)
        stringrequest.setRetryPolicy(
            DefaultRetryPolicy(
                80000,
               0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        )

    }

    private fun alert_dialog(message:String,phone_number:String,amount:String) {
        val builder = AlertDialog.Builder(this.context)
        builder.setMessage(message)
            .setPositiveButton("Confirm",
                DialogInterface.OnClickListener { dialog, id ->

                    pay_loan_from_mpesa(phone_number,amount)
                    // FIRE ZE MISSILES!
                })
            .setNegativeButton("Cancel",
                DialogInterface.OnClickListener { dialog, id ->
                    // User cancelled the dialog
                })
        // Create the AlertDialog object and return it
        builder.create()
        builder.show()


    }


}
