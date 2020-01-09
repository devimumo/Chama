package com.example.chama.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chama.DataClasses.Contribution_statement_data
import com.example.chama.R
import kotlinx.android.synthetic.main.contributions_statement.view.*

class Contributon_Statement_Adapter (val statement_details:ArrayList<Contribution_statement_data>,var c: Context):RecyclerView.Adapter<Contributon_Statement_Adapter.ViewHolder>()
{
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Contributon_Statement_Adapter.ViewHolder {
val view= LayoutInflater.from(parent.context).inflate(R.layout.contributions_statement,parent,false)


        return  ViewHolder(view)    }

    override fun getItemCount(): Int {
        return  statement_details.size    }

    override fun onBindViewHolder(holder: Contributon_Statement_Adapter.ViewHolder, position: Int) {
        val user_data: Contribution_statement_data=statement_details[position]



        holder?.itemview.payed_date.text=user_data.date

        holder?.itemview.amount_due.text= user_data.amount_due.toString()

        holder?.itemview.paid.text= user_data.paid.toString()

        holder?.itemview.bal.text= user_data.bal.toString()

        holder?.itemview.message.text= user_data.message


    }


    class ViewHolder(val itemview: View):RecyclerView.ViewHolder(itemview)
    {}

}