package com.example.animalsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.animalsapp.screens.AnimalDetailScreen
import com.example.animalsapp.screens.AnimalListScreen
import com.example.animalsapp.screens.EnvironmentDetailScreen
import com.example.animalsapp.screens.EnvironmentListScreen
import com.example.animalsapp.ui.theme.AnimalsAPPTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnimalsAPPTheme {
                var selectedScreen by remember {
                    mutableStateOf("animal-list")
                }
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                        .background(Color(0xFF0C3611)),
                    contentColor = Color.Transparent,
                    containerColor = Color.Transparent,
                    bottomBar = {
                        Box (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 20.dp).padding(horizontal = 40.dp)
                                .background(Color(0xFFAEB044), shape = RoundedCornerShape(40.dp))

                        ) {
                            Row (
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Absolute.SpaceBetween
                            ) {
                                NavigationBarItem(
                                    selected = selectedScreen == "animal-list",
                                    onClick = {
                                        selectedScreen = "animal-list"
                                        navController.navigate("animal-list")
                                    },
                                    icon = {
                                        Row (
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.animalicon),
                                                contentDescription = "Animals",
                                                modifier = Modifier.padding(end = 8.dp).size(32.dp),
                                                colorFilter = ColorFilter.tint(Color.Black)
                                            )
                                            Text(
                                                text = "Inicio",
                                                color = Color.Black,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        indicatorColor = Color.Transparent
                                    )
                                )
                                NavigationBarItem(
                                    selected = selectedScreen == "environment-list",
                                    onClick = {
                                        selectedScreen = "environment-list"
                                        navController.navigate("environment-list")
                                    },
                                    icon = {
                                        Row (
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.environmenticon),
                                                contentDescription = "Environments",
                                                modifier = Modifier.padding(end = 8.dp).size(32.dp),
                                                colorFilter = ColorFilter.tint(Color.Black)
                                            )
                                            Text(
                                                text = "Ambientes",
                                                color = Color.Black,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        indicatorColor = Color.Transparent
                                    )
                                )
                            }
                        }

                    }

                ) { innerPadding ->
                    NavHost(
                        navController = navController, startDestination = "animal-list"
                    ) {
                        composable(route = "animal-list") {
                            AnimalListScreen(
                                innerPadding = innerPadding, navController = navController
                            )
                        }
                        composable(
                            route = "animal-list/{animalId}",
                            arguments = listOf(
                                navArgument("animalId") {
                                    type = NavType.StringType
                                    nullable = false
                                }
                            )
                        ) {
                            val animalId = it.arguments?.getString("animalId") ?: ""
                            AnimalDetailScreen(
                                innerPadding = innerPadding,
                                animalId = animalId
                            )
                        }
                        composable(route = "environment-list") {
                            EnvironmentListScreen(
                                innerPadding = innerPadding, navController = navController
                            )
                        }
                        composable(
                            route = "environment-list/{environmentId}",
                            arguments = listOf(
                                navArgument("environmentId") {
                                    type = NavType.StringType
                                    nullable = false
                                }
                            )
                        ) {
                            val environmentId = it.arguments?.getString("environmentId") ?: ""
                            EnvironmentDetailScreen(
                                innerPadding = innerPadding,
                                environmentId = environmentId,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}