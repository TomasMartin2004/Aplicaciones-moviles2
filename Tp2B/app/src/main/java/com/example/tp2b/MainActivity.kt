package com.example.tp2b

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.tp2b.ui.theme.CapitalCitiesTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tp2b.viewmodel.CapitalCityViewModel
import com.example.tp2b.viewmodel.CapitalCityViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CapitalCitiesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //obtiene la instancia de la base de datos
                    val database = CapitalCityDatabase.getDatabase(applicationContext)
                    //crea una instancia del DAO
                    val dao = database.capitalCityDao()
                    //crea una instancia del repositorio con el DAO
                    val repository = CapitalCityRepository(dao)
                    //crea una instancia del ViewModel utilizando la factory
                    val viewModel: CapitalCityViewModel = viewModel(
                        factory = CapitalCityViewModelFactory(repository)
                    )
                    //navegacion de la aplicacion.
                    AppNavigation(viewModel = viewModel)
                }
            }
        }
    }
}
