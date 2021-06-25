package com.projects.advancedtodo

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.firebase.ui.auth.AuthUI
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView
    lateinit var txtHeaderName: TextView

    var previousMenuItem: MenuItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar=findViewById(R.id.toolbar)
        sharedPreferences=getSharedPreferences("User data", Context.MODE_PRIVATE)
        drawerLayout = findViewById(R.id.drawerLayout)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        frameLayout = findViewById(R.id.frame)
        navigationView = findViewById(R.id.navigationView)

        val headerView = navigationView.getHeaderView(0)
        txtHeaderName = headerView.findViewById(R.id.txtHeaderName)

        txtHeaderName.text=sharedPreferences.getString("userEmail","None")
        setUpToolbar()

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {

            if (previousMenuItem != null) {
                previousMenuItem?.isChecked = false
            }

            it.isCheckable = true
            it.isChecked = true
            previousMenuItem = it

            when (it.itemId) {
                R.id.home -> {
                    openHome()
                    drawerLayout.closeDrawers()
                }

                R.id.stats -> {
                    openStats()
                    drawerLayout.closeDrawers()
                }

                R.id.about -> {
                    openAbout()
                    drawerLayout.closeDrawers()
                }

                R.id.logout -> {

                    val dialog = AlertDialog.Builder(this)
                    dialog.setTitle("Confirmation")
                    dialog.setMessage("Are you sure you want to exit?")

                    dialog.setPositiveButton("YES") { text, listener ->
                        signOut()
                    }
                    dialog.setNegativeButton("NO") { text, listener ->
                        //close dialog and open home
                        //openHome()
                        drawerLayout.closeDrawers()
                    }
                    dialog.create()
                    dialog.show()
                }
            }
            return@setNavigationItemSelectedListener true
        }

        openHome()
    }


    private fun signOut() {
        // [START auth_fui_signout]
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener {
                    sharedPreferences.edit().clear().apply()
                    startActivity(Intent(this, FirebaseUIActivity::class.java))
                    finish()
                }
        // [END auth_fui_signout]
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "TODO"
        supportActionBar?.setHomeButtonEnabled(true)    //1
        supportActionBar?.setDisplayHomeAsUpEnabled(true)   //2

    }

    private fun openHome() {
        print("opening HomePage")
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.frame,
                AboutFragment()
            )
            .commit()
        supportActionBar?.title = "TODO"
        navigationView.setCheckedItem(R.id.home)
    }

    private fun openStats() {
        print("opening Stats page")
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.frame,
                StatsFragment()
            )
            .commit()
        //supportActionBar?.title = "All Restaurants"
        navigationView.setCheckedItem(R.id.stats)
    }

    private fun openAbout() {
        print("opening Stats page")
        supportFragmentManager.beginTransaction()
                .replace(
                        R.id.frame,
                        AboutFragment()
                )
                .commit()
        //supportActionBar?.title = "All Restaurants"
        navigationView.setCheckedItem(R.id.stats)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> drawerLayout.openDrawer(GravityCompat.START)
        }

        return super.onOptionsItemSelected(item)
    }
}