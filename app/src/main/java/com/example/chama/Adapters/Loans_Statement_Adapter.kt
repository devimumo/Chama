package com.example.chama.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chama.DataClasses.Loans_statement_data
import com.example.chama.R
import kotlinx.android.synthetic.main.loan_statement.view.*

class Loans_Statement_Adapter(private val statement_detail: ArrayList<Loans_statement_data>, var c: Context):RecyclerView.Adapter<Loans_Statement_Adapter.ViewHolder>()
{
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Loans_Statement_Adapter.ViewHolder {
val view= LayoutInflater.from(parent.context).inflate(R.layout.loan_statement,parent,false)


        return  ViewHolder(view)    }

    override fun getItemCount(): Int {
        return  statement_detail.size    }

    override fun onBindViewHolder(holder: Loans_Statement_Adapter.ViewHolder, position: Int) {
        val user_data: Loans_statement_data=statement_detail[position]



        holder?.itemview.payed_date.text=user_data.arrears_payed_transction_date

        holder?.itemview.transaction_id.text= user_data.transaction_id.toString()

        holder?.itemview.paid.text= user_data.paid.toString()

        holder?.itemview.bal.text= user_data.bal.toString()

        holder?.itemview.message.text= user_data.message.toString()


    }


    class ViewHolder(val itemview: View):RecyclerView.ViewHolder(itemview)
    {}

}