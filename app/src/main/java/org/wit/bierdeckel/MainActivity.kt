package org.wit.bierdeckel

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import org.wit.bierdeckel.databinding.ActivityMainBinding
import org.wit.bierdeckel.main.MainApp


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var app: MainApp
    var mode = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)



        app = application as MainApp



        val drawerLayout: DrawerLayout = binding.drawerLayout
        //drawerLayout.setBackgroundResource(R.drawable.braun_eckig)

        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(

            setOf(
                R.id.nav_home,
                R.id.nav_beerpong,
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

    //ToDo: Themes anpassen
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId==R.id.theme_setting) {

            if (mode == false) {
                Toast.makeText(this, "Theme wird geändert!", Toast.LENGTH_SHORT).show()
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                mode = true
                return super.onOptionsItemSelected(item)
            }

            if (mode== true){
                Toast.makeText(this, "Theme wird geändert!", Toast.LENGTH_SHORT).show()
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                mode = false
                return super.onOptionsItemSelected(item)
            }
        }

        return super.onOptionsItemSelected(item)
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