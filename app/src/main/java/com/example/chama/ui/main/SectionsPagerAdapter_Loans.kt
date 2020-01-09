package com.example.chama.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.chama.Fragments.GetLoansData
import com.example.chama.Fragments.PayLoan
import com.example.chama.R

private val TAB_TITLES = arrayOf(
    R.string.loans_tab_text_1,
    R.string.loans_tab_text_2
,R.string.loans_tab_text_3

)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter_Loans(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        //return PlaceholderFragment.newInstance(position + 1)

          when(position)
        {
            0->
            {
                return GetLoansData()
            }
              1->
              {

                  return PayLoan()
              }

              else->return GetLoansData()
        }


    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 3
    }
}