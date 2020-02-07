package com.example.chama

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_pending_loan_data.*

class PendiD : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pendi_d)

        val view=        setContentView(R.layout.activity_pendi_d)

        shared_pred_data(this)

    }
    fun shared_pred_data(context: Context)
    {
        val MyPreferences = "mypref"
        val sharedPreferences = context.getSharedPreferences(MyPreferences, Context.MODE_PRIVATE)
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
