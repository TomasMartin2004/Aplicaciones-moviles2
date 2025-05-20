package com.example.tp2b

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tp2b.viewmodel.CapitalCityViewModel

@Composable
fun AppNavigation(
    viewModel: CapitalCityViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        composable("main") {
            MainScreen(
                viewModel = viewModel,
                onNavigate = { screen ->
                    when (screen) {
                        CapitalCityScreen.Add -> navController.navigate("add")
                        CapitalCityScreen.Search -> navController.navigate("search")
                        CapitalCityScreen.Delete -> navController.navigate("delete")
                        CapitalCityScreen.DeleteByCountry -> navController.navigate("delete_country")
                        CapitalCityScreen.UpdatePopulation -> navController.navigate("update_population")
                        else -> {}
                    }
                }
            )
        }
        
        composable("add") {
            AddCityScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable("search") {
            SearchCityScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable("delete") {
            DeleteCityScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable("delete_country") {
            DeleteCountryCitiesScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable("update_population") {
            UpdatePopulationScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
