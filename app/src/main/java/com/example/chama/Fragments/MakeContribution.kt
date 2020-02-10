package com.example.chama.Fragments


import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
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
import com.example.chama.Contribution
import com.example.chama.R
import com.example.chama.Volley_ErrorListener_Handler
import kotlinx.android.synthetic.main.make_contributions.*
import kotlinx.android.synthetic.main.make_contributions.view.*
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




        view.pay_with_mpesa.setOnClickListener {

            pay_with_mpesa(view)
        }

        return view
    }




    fun pay_with_mpesa(view: View)
    {
                    val phone_number=my_number_edittext.text.toString()
                   val amount=my_amount_edittext.text.toString()
        val message="Confirm Initiate mpesa transaction for phonenumber "+phone_number+" amount "+amount+""

      alert_dialog(message,phone_number,amount,view)

    }

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)


    private  fun check_balance( view: View)
{

    val requestQueue=Volley.newRequestQueue(view.context)
    val url="https://project-daudi.000webhostapp.com/ladies_group/check_contribution_balance.php"
    val stringRequest=object :StringRequest(Request.Method.POST,url,Response.Listener {response->


        Log.i("response",response)
        val jsonObject=JSONObject(response)

       // val jsonArray=jsonObject.getJSONArray("contribution_statement")


            /////////////////////////////////////////////////////////////
            val balance=jsonObject.getInt("balance")
            val date_due=jsonObject.getString("due_date")
            val bal=balance>0


            if (balance.equals(0))
            {
                balance_amount.text="kshs "+balance.toString()
                due_date.text=date_due.toString()

                with_balance.visibility=View.VISIBLE
                payment_promnt.visibility=View.VISIBLE
                make_contributions_progressbar_layout.visibility=View.GONE
               my_amount_edittext.text=balance.toString().toEditable()
                my_number_edittext.text=254713836954.toString().toEditable()

            }
            else if (balance>0)
            {

                balance_amount.text="kshs "+balance.toString()
                due_date.text=date_due.toString()
                my_amount_edittext.text=balance.toString().toEditable()
                my_number_edittext.text=713836954.toString().toEditable()

                with_balance.visibility=View.VISIBLE
                payment_promnt.visibility=View.VISIBLE

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

    fun pay_loan_from_mpesa(phone_number: String,amount: String,view:View)
    {


        val c=view.context
        val progressbar: ProgressDialog = ProgressDialog(view.context)
        progressbar.setMessage("Loading..........")
        progressbar.setCancelable(false)
        progressbar.show()
      //  pay_contribution_progress_bar.visibility=View.VISIBLE

        val queue = Volley.newRequestQueue(view.context)


        val MyPreferences = "mypref"
        val sharedPreferences = c?.getSharedPreferences(MyPreferences, Context.MODE_PRIVATE)
        // String session_id= sharedPreferences.getString("sessions_ids","");


        val session_id = sharedPreferences?.getString("sessions_ids", "")
        val url = "https://project-daudi.000webhostapp.com/ladies_group/lipa_online_contribution.php?session_id="+session_id+"&phone_number="+phone_number+"&amount="+amount

        val stringrequest=object :StringRequest(Request.Method.GET,url,Response.Listener { responsess->


            Log.i("responses",responsess)


            val server_response=JSONObject(responsess);
            val response_server: String = server_response.getString("server_response")

            when(response_server) {
                "!post_phone_number" -> {
                  //  pay_contribution_progress_bar.visibility=View.GONE
                    progressbar.dismiss()

                    Toast.makeText(view.context,"phone number needed", Toast.LENGTH_SHORT).show()


                }
                "invalid_phone_number" -> {
                  //  pay_contribution_progress_bar.visibility=View.GONE
                    progressbar.dismiss()
                    Toast.makeText(view.context,"transaction in progress", Toast.LENGTH_SHORT).show()


                }
                "timeout" -> {
                 //   pay_contribution_progress_bar.visibility=View.GONE
                    progressbar.dismiss()

                    Toast.makeText(view.context,"transaction timeout", Toast.LENGTH_SHORT).show()


                }
                "successful" -> {
                  //  pay_contribution_progress_bar.visibility=View.GONE


                    progressbar.dismiss()

                    Toast.makeText(view.context,"transaction successful", Toast.LENGTH_SHORT).show()

                    val intent=Intent(view.context,Contribution::class.java)
                    startActivity(intent)



                }
                "cancelled" -> {
                 //   pay_contribution_progress_bar.visibility=View.GONE
                    progressbar.dismiss()

                    Toast.makeText(view.context,"transaction cancelled", Toast.LENGTH_SHORT).show()


                }
                "limited" -> {
                 //   pay_contribution_progress_bar.visibility=View.GONE
                    progressbar.dismiss()

                    Toast.makeText(view.context,"transaction limited", Toast.LENGTH_SHORT).show()


                }


                else -> {
                 //   pay_contribution_progress_bar.visibility=View.GONE
                    progressbar.dismiss()

                    Toast.makeText(view.context,"transaction timed out", Toast.LENGTH_SHORT).show()

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

    private fun alert_dialog(message:String,phone_number:String,amount:String,view:View)
    {
        val builder = AlertDialog.Builder(view.context)
        builder.setMessage(message)
            .setPositiveButton("Confirm",
                DialogInterface.OnClickListener { dialog, id ->

                    pay_loan_from_mpesa(phone_number,amount,view)
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
