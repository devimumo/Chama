package com.example.chama

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class DashBoard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)
    }

    fun Card_click(view: View) {


        when (view.id)
        {
            R.id.transactions->{


                val intent= Intent(this,Contribution::class.java)
                startActivity(intent)
            }

            R.id.loans->{
                val intent= Intent(this,Loans::class.java)
                startActivity(intent)
            }

        }

    }
}
