package com.example.chama.Fragments


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.chama.R
import kotlinx.android.synthetic.main.fragment_pending_loan_data.*

/**
 * A simple [Fragment] subclass.
 */
class PendingLoanData : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


val view=         inflater.inflate(R.layout.fragment_pending_loan_data, container, false)





      shared_pred_data(view)


    return  view
    }

    fun shared_pred_data(view: View)
    {
        val MyPreferences = "mypref"
        val sharedPreferences = view?.context?.getSharedPreferences(MyPreferences, Context.MODE_PRIVATE)
        // String session_id= sharedPreferences.getString("sessions_ids","");


        val loan_id = sharedPreferences?.getString("loan_id", "")
        val loan_approval_id = sharedPreferences?.getString("loan_approval_id", "")
        val disbursed_amount = sharedPreferences?.getString("disbursed_amount", "")
        val loan_disbursement_transaction_id = sharedPreferences?.getString("loan_disbursement_transaction_id", "")
        val disbursement_method = sharedPreferences?.getString("disbursement_method", "")
        val disbursement_date = sharedPreferences?.getString("disbursement_date", "")
        val loan_due_date = sharedPreferences?.getString("loan_due_date", "")
        val loan_balances = sharedPreferences?.getString("loan_balances", "")
        val loan_description = sharedPreferences?.getString("loan_description", "")

        loan_approval_id_pending_data_layout.text=loan_approval_id
        loan_id_pending_data_layout.text=loan_id
        disbursed_amount_pending_data_layout.text=disbursed_amount
        loan_disbursement_transaction_id_pending_data_layout.text=loan_disbursement_transaction_id
        disbursement_method_pending_data_layout.text=disbursement_method
        disbursement_date_pending_data_layout.text=disbursement_date
        loan_due_date_pending_data_layout.text=loan_due_date
        loan_balance_pending_data_layout.text=loan_balances
        loan_description_pending_data_layout.text=loan_description



    }


}
