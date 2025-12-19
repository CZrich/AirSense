package com.plataformas.airsense

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.plataformas.airsense.databinding.ActivityMainBinding // Asegúrate de importar esto

class MainActivity : AppCompatActivity() {

    // 1. Declarar la variable binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 2. Inicializar binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        // 3. Configurar navegación
//        val navHostFragment = supportFragmentManager
//            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        val navController = navHostFragment.navController
// Por esto (más seguro)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as? NavHostFragment

        navHostFragment?.let {
            val navController = it.navController
            binding.bottomNavigation.setupWithNavController(navController)
        }
        // Esto conectará los clics del menú con los fragmentos automáticamente
        //binding.bottomNavigation.setupWithNavController(navController)
    }
}