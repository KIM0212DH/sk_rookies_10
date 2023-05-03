package com.superstrong.android.viewmodel

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.superstrong.android.view.CoinListFragment
import com.superstrong.android.view.LogFragment

class TabFragmentAdapter (fa: FragmentActivity) : FragmentStateAdapter(fa) {
    //position 에 따라 원하는 Fragment로 이동시키기
    override fun createFragment(position: Int): Fragment {
        val fragment =  when(position)
        {
            0-> CoinListFragment()
            1-> LogFragment()
            else -> CoinListFragment()
        }
        return fragment
    }

    //tab의 개수만큼 return
    override fun getItemCount(): Int = 2

    //tab의 이름 fragment마다 바꾸게 하기
}