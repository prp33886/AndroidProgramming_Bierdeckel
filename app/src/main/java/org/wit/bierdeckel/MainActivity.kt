package org.wit.bierdeckel

import android.app.Application
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import org.wit.bierdeckel.databinding.ActivityMainBinding
import org.wit.bierdeckel.main.MainApp


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var app: MainApp


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)



        app = application as MainApp
        // ToDo: Hier noch einbauen dass man Nachricht senden kann an Partyroom besitzer
        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Hier kannst du Nachrichten senden", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }


        val drawerLayout: DrawerLayout = binding.drawerLayout
        //drawerLayout.setBackgroundResource(R.drawable.braun_eckig)

        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(

            setOf(
                R.id.nav_home,
                R.id.nav_gallery,
                R.id.nav_barsMap,
                R.id.nav_slideshow,
                R.id.nav_userInformations,
                R.id.nav_logout,
                R.id.nav_adminCenter
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        setHeaderData()
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    fun setHeaderData(){

        var name = app.user.vorName + " " + app.user.nachName
        var email = app.user.email
        binding.navView.getHeaderView(0).findViewById<TextView>(R.id.header_view_email).setText(email)
        binding.navView.getHeaderView(0).findViewById<TextView>(R.id.header_view_name).setText(name)
        //ToDO: UserProfilPic

        val navViewID = binding.navView.id
        val headerview = binding.root.findViewById<NavigationView>(navViewID).getHeaderView(0)
        val imageView = headerview.findViewById<ImageView>(R.id.header_view_image)


        //ToDo   Navview Image muss noch geladen werden...
       // val roundedBitmap = RoundedTransformation().transform(app.loadedProfilpic)
       // val roundedDrawable = BitmapDrawable(roundedBitmap)
       // setImageViewDrawable(imageView, roundedDrawable)

    }

    fun setImageViewDrawable(imageView: ImageView, drawable: Drawable) {
        imageView.setImageDrawable(drawable)
    }

}