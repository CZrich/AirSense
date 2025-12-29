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


        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as? NavHostFragment


        navHostFragment?.let {
            val navController = it.navController
            binding.bottomNavigation.setupWithNavController(navController)

            // Agregamos este listener para forzar el regreso manual si falla el automático
            binding.bottomNavigation.setOnItemSelectedListener { item ->
                if (item.itemId != navController.currentDestination?.id) {
                    navController.navigate(item.itemId)
                }
                true
            }
        }
    }
}