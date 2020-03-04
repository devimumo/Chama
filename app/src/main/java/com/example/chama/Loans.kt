package com.example.chama

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.chama.Fragments.PendingLoanData
import com.example.chama.ui.main.SectionsPagerAdapter_Loans
import com.google.android.material.tabs.TabLayout

class Loans : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loans)
        val sectionsPagerAdapter = SectionsPagerAdapter_Loans(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)


        fun ddd(view: View) {
            PendingLoanData()
        }
    }
}