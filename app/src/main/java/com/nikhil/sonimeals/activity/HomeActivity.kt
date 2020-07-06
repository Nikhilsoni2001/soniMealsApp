package com.nikhil.sonimeals.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.nikhil.sonimeals.R
import com.nikhil.sonimeals.fragment.*

class HomeActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var frame: FrameLayout
    lateinit var navigationView: NavigationView

    var previousItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //Initializations
        init()

        //adding toolbar
        setUpToolbar()

        displayHome()

        /*The closing of navigation drawer is delayed to make the transition smooth
            * We delay it by 0.1 second*/
        val mPendingRunnable = Runnable { drawerLayout.closeDrawer(GravityCompat.START) }
        Handler().postDelayed(mPendingRunnable, 100)

        //making hamburger icon
        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        //making nav items clickable
        navigationView.setNavigationItemSelectedListener {

            if (previousItem != null) {
                previousItem?.isChecked = false
            }

            it.isCheckable = true
            it.isChecked = true
            previousItem = it

            when (it.itemId) {
                R.id.home -> displayHome()
                R.id.myProfile -> displayProfile()
                R.id.favRes -> displayFavRes()
                R.id.orderHistory -> displayOrderHistory()
                R.id.faqs -> displayFaq()
                R.id.logout -> {
                    /*Creating a confirmation dialog*/
                    val builder = AlertDialog.Builder(this@HomeActivity)
                    builder.setTitle("Confirmation")
                        .setMessage("Are you sure you want exit?")
                        .setPositiveButton("Yes") { _, _ ->
//                            ActivityCompat.finishAffinity(this)
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                        }
                        .setNegativeButton("No") { _, _ ->
                            displayHome()
                        }
                        .create()
                        .show()
                }
            }
            return@setNavigationItemSelectedListener true
        }


    }

    private fun displayHome() {
        val fragment = HomeFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame, fragment)
        transaction.commit()
        supportActionBar?.title = "All Restaurants"
        navigationView.setCheckedItem(R.id.home)
        drawerLayout.closeDrawers()
    }

    private fun displayProfile() {
        val fragment = ProfileFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame, fragment)
        transaction.commit()
        supportActionBar?.title = "Profile"
        drawerLayout.closeDrawers()
    }

    private fun displayFavRes() {
        val fragment = FavouriteFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame, fragment)
        transaction.commit()
        supportActionBar?.title = "Favourite Restaurant"
        drawerLayout.closeDrawers()
    }

    private fun displayOrderHistory() {
        val fragment = OrderHistoryFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame, fragment)
        transaction.commit()
        supportActionBar?.title = "Order History"
        drawerLayout.closeDrawers()
    }

    private fun displayFaq() {
        val fragment = FaqFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame, fragment)
        transaction.commit()
        supportActionBar?.title = "FAQ"
        drawerLayout.closeDrawers()
    }

    //Function for setting the toolbar
    fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Toolbar Title"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    //making hamburger icon Functional
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.frame)
        navigationView.checkedItem?.isChecked = false

        when (frag) {
            !is HomeFragment -> displayHome()

            else -> super.onBackPressed()
        }
    }

    fun init() {
        drawerLayout = findViewById(R.id.drawerLayout)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        toolbar = findViewById(R.id.toolbar)
        frame = findViewById(R.id.frame)
        navigationView = findViewById(R.id.navigationView)
    }

}