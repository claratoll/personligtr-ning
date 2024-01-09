package se.claratoll.personligtrning

import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView
import se.claratoll.personligtrning.databinding.ActivityNavigationBinding

class NavigationActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarNavigation.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_navigation)

        val speedDialView = findViewById<SpeedDialView>(R.id.speedDial)
        speedDialView.addActionItem(
            SpeedDialActionItem.Builder(R.id.fab_action1, R.drawable.baseline_chat)
                .setFabBackgroundColor(ResourcesCompat.getColor(resources, R.color.white, theme))
                .create()
        )
        speedDialView.addActionItem(
            SpeedDialActionItem.Builder(R.id.fab_action2, R.drawable.baseline_barbell)
                .setFabBackgroundColor(ResourcesCompat.getColor(resources, R.color.white, theme))
                .create()
        )
        speedDialView.addActionItem(
            SpeedDialActionItem.Builder(R.id.fab_action3, R.drawable.baseline_walk)
                .setFabBackgroundColor(ResourcesCompat.getColor(resources, R.color.white, theme))
                .create()
        )


        speedDialView.setOnActionSelectedListener(SpeedDialView.OnActionSelectedListener { actionItem ->
            when (actionItem.id) {
                R.id.fab_action1 -> {
                    Toast.makeText(this, "fab action 1", Toast.LENGTH_SHORT).show()
                    speedDialView.close() // To close the Speed Dial with animation
                    return@OnActionSelectedListener true // false will close it without animation
                }
                R.id.fab_action2 -> {
                    navController.navigate(R.id.nav_new_workout)
                    speedDialView.close()
                    return@OnActionSelectedListener true
                }
                R.id.fab_action3 -> {
                    navController.navigate(R.id.nav_new_steps)
                    speedDialView.close()
                    return@OnActionSelectedListener true
                }
            }
            false
        })



        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_calendar, R.id.nav_workout
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_navigation)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}