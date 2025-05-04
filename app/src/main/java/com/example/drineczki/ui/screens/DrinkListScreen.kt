package com.example.drineczki.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.drineczki.data.MyDatabase
import com.example.drineczki.data.model.Koktajl
import com.example.drineczki.ui.components.RandomDrinkIcon
import com.google.accompanist.pager.*
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DrinkListScreen(
    navController: NavController? = null,
    database: MyDatabase,
    onDrinkSelected: ((Int) -> Unit)? = null,
    onMenuClick: (() -> Unit)? = null // Dodano parametr do obsługi otwierania szuflady
) {
    val viewModel = remember { DrinkListViewModel(database) }
    val easyDrinks by viewModel.easyDrinks.collectAsState()
    val hardDrinks by viewModel.hardDrinks.collectAsState()

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    var searchQuery by remember { mutableStateOf("") }
    var isSearchVisible by remember { mutableStateOf(false) } // Kontrola widoczności pola wyszukiwania

    LaunchedEffect(Unit) {
        viewModel.loadKoktajle()
    }

    Column(
        modifier = if (navController == null) {
            Modifier
                .fillMaxWidth(0.33f)
                .background(Color(0xFFa66730))
        } else {
            Modifier
                .fillMaxSize()
                .background(Color(0xFFa66730))
        }
    ) {
        // Pasek aplikacji
        TopAppBar(
            title = { Text("DrineczkiApp", color = Color.White) },
            navigationIcon = {
                IconButton(onClick = { onMenuClick?.invoke() }) { // Ikona hamburgera otwierająca szufladę
                    Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
                }
            },
            actions = {
                IconButton(onClick = { isSearchVisible = !isSearchVisible }) {
                    Icon(Icons.Default.Search, contentDescription = "Szukaj", tint = Color.White)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFa66730)),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFFa66730)
            )
        )

        // Pole wyszukiwania widoczne tylko po kliknięciu ikony lupy
        if (isSearchVisible) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Wyszukaj drinka...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Zakładki (TabRow)
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color(0xFFfab170),
            contentColor = Color.Black
        ) {
            Tab(
                selected = pagerState.currentPage == 0,
                onClick = { coroutineScope.launch { pagerState.animateScrollToPage(0) } },
                text = { Text("Łatwe") }
            )
            Tab(
                selected = pagerState.currentPage == 1,
                onClick = { coroutineScope.launch { pagerState.animateScrollToPage(1) } },
                text = { Text("Trudne") }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Pager do przełączania między zakładkami za pomocą gestów
        HorizontalPager(
            count = 2,
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val drinksToShow = if (page == 0) easyDrinks else hardDrinks
            val filteredDrinks = drinksToShow.filter { it.nazwa?.contains(searchQuery, ignoreCase = true) == true }
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 150.dp),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredDrinks) { koktajl ->
                    KoktajlItem(navController, koktajl = koktajl, onDrinkSelected)
                }
            }
        }
    }
}

@Composable
fun KoktajlItem(
    navController: NavController?,
    koktajl: Koktajl,
    onDrinkSelected: ((Int) -> Unit)? = null
) {
    Card(
        modifier = if (navController == null) {
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
        } else {
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(160.dp)
        },
        elevation = CardDefaults.cardElevation(4.dp),
        onClick = {
            val id = koktajl.id
            onDrinkSelected?.invoke(id!!)
            if (navController != null) {
                navController.navigate("Drink/$id")
            }
        },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFfab170),
            contentColor = Color.Black
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = koktajl.nazwa ?: "",
                style = MaterialTheme.typography.headlineMedium
            )
            RandomDrinkIcon(koktajl.id?:0)
        }
    }
}

@Composable
fun AppNavigation(database: MyDatabase) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.background(Color(0xFFa66730))
            ) {
                Text(
                    text = "Menu",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
                Divider(color = Color.White, thickness = 1.dp)

                // Przycisk do DrinkListScreen
                NavigationDrawerItem(
                    label = { Text("Lista drinków", color = Color.White) },
                    selected = false,
                    onClick = {
                        coroutineScope.launch { drawerState.close() }
                        navController.navigate("DrinkListScreen")
                    },
                    modifier = Modifier.padding(8.dp)
                )

                // Przycisk do InfoScreen
                NavigationDrawerItem(
                    label = { Text("Info", color = Color.White) },
                    selected = false,
                    onClick = {
                        coroutineScope.launch { drawerState.close() }
                        navController.navigate("Info")
                    },
                    modifier = Modifier.padding(8.dp)
                )

                // Martwe przyciski
                NavigationDrawerItem(
                    label = { Text("Opcja 1", color = Color.White) },
                    selected = false,
                    onClick = { /* Martwy przycisk */ },
                    modifier = Modifier.padding(8.dp)
                )
                NavigationDrawerItem(
                    label = { Text("Opcja 2", color = Color.White) },
                    selected = false,
                    onClick = { /* Martwy przycisk */ },
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    ) {
        NavHost(navController = navController, startDestination = "DrinkListScreen") {
            composable("DrinkListScreen") {
                DrinkListScreen(
                    navController = navController,
                    database = database,
                    onMenuClick = { coroutineScope.launch { drawerState.open() } } // Otwieranie szuflady
                )
            }
            composable("Info") { InfoScreen(navController) }
            composable(
                "Drink/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: 0
                DrinkScreen(navController, id, database)
            }
        }
    }
}
