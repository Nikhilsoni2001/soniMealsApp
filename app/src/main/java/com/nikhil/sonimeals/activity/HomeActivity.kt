package com.nikhil.sonimeals.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.android.volley.toolbox.Volley
import com.google.android.material.navigation.NavigationView
import com.nikhil.sonimeals.R
import com.nikhil.sonimeals.fragment.*
import com.nikhil.sonimeals.util.DrawerLocker
import com.nikhil.sonimeals.util.SessionManager

class HomeActivity : AppCompatActivity(), DrawerLocker {

    override fun setDrawerEnabled(enabled: Boolean) {
        val lockMode = if (enabled)
            DrawerLayout.LOCK_MODE_UNLOCKED
        else
            DrawerLayout.LOCK_MODE_LOCKED_CLOSED

        drawerLayout.setDrawerLockMode(lockMode)
        actionBarDrawerToggle.isDrawerIndicatorEnabled = enabled
    }

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    var previousItem: MenuItem? = null
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var frame: FrameLayout
    lateinit var headerName: TextView
    lateinit var headerMobile: TextView
    lateinit var headerImg: ImageView
    private lateinit var sessionManager: SessionManager
    private lateinit var sharedPrefs: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        sessionManager = SessionManager(this@HomeActivity)
        sharedPrefs = this@HomeActivity.getSharedPreferences(
            sessionManager.PREF_NAME,
            sessionManager.PRIVATE_MODE
        )

        //Initializations
        init()


        //adding toolbar
        setUpToolbar()

        setupActionBarToggle()

        displayHome()


        //making nav items clickable
        navigationView.setNavigationItemSelectedListener {

            if (previousItem != null) {
                previousItem?.isChecked = false
            }

            it.isCheckable = true
            it.isChecked = true
            previousItem = it

            /*The closing of navigation drawer is delayed to make the transition smooth
           * We delay it by 0.1 second*/
            val mPendingRunnable = Runnable { drawerLayout.closeDrawer(GravityCompat.START) }
            Handler().postDelayed(mPendingRunnable, 100)

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
                            sessionManager.setLogin(false)
                            sharedPrefs.edit().clear().apply()
                            val intent = Intent(this, LoginActivity::class.java)
                            Volley.newRequestQueue(this).cancelAll(this::class.java.simpleName)
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

        headerName.text = sharedPrefs.getString("user_name", null)
        val mobile = "+91-${sharedPrefs.getString("user_mobile_number", null)}"
        headerMobile.text = mobile

        headerName.setOnClickListener {
            displayProfile()
            val mPendingRunnable = Runnable { drawerLayout.closeDrawer(GravityCompat.START) }
            Handler().postDelayed(mPendingRunnable, 50)
        }

        headerImg.setOnClickListener {
            displayProfile()
            val mPendingRunnable = Runnable { drawerLayout.closeDrawer(GravityCompat.START) }
            Handler().postDelayed(mPendingRunnable, 50)
        }
    }

    private fun setupActionBarToggle() {
        actionBarDrawerToggle = object :
            ActionBarDrawerToggle(this@HomeActivity, drawerLayout, R.string.open_drawer, R.string.close_drawer) {
            override fun onDrawerStateChanged(newState: Int) {
                super.onDrawerStateChanged(newState)
                /*The closing of navigation drawer is delayed to make the transition smooth
            * We delay it by 0.1 second*/
                val pendingRunnable = Runnable {
                    val inputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                }
/*delaying the closing of the navigation drawer for that the motion looks smooth*/
                Handler().postDelayed(pendingRunnable, 50)
            }
        }
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
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
        supportActionBar?.title = "My Profile"
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
        toolbar.setTitleTextAppearance(this, R.style.PoppinsTextAppearance)
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
        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)
        frame = findViewById(R.id.frame)
        val headerView = navigationView.getHeaderView(0)
        headerName = headerView.findViewById(R.id.tvName)
        headerMobile = headerView.findViewById(R.id.tvMobile)
        headerImg = headerView.findViewById(R.id.imgProfile)
    }

}