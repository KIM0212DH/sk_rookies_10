package com.superstrong.android.view
import android.content.Context
import android.content.Intent
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.superstrong.android.R

class MyNavigationListener(private val context: Context) : BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.navigation_home -> {
                context.startActivity(Intent(context, WalletActivity::class.java))
                return true
            }
            R.id.navigation_account -> {
                context.startActivity(Intent(context, AccountManageActivity::class.java))
                return true
            }
            else -> return false
        }
    }
}