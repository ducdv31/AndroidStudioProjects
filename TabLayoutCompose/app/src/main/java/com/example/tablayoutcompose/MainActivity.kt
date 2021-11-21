package com.example.tablayoutcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tablayoutcompose.ui.theme.TabLayoutComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TabLayoutComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MyApp()
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewApp() {
    MyApp()
}

@Composable
fun MyApp() {
    Scaffold() { innerPadding ->
        Column {
            Tab(selected = true, onClick = { }) {
                Text(text = "Tab 1")
                Text(text = "Tab 1.2")
            }
            Tab(selected = false, onClick = { /*TODO*/ }) {
                Text(text = "Tab 2")
            }

            /* Tab row */
            var tabIndex by remember { mutableStateOf(0) }
            val tabData = listOf(
                "MUSIC" to Icons.Filled.Home,
                "MARKET" to Icons.Filled.ShoppingCart,
                "FILMS" to Icons.Filled.AccountBox,
                "BOOKS" to Icons.Filled.Settings,
            )

            ScrollableTabRow(
                selectedTabIndex = tabIndex,
                divider = {
                    TabRowDefaults.Divider(
                        thickness = 2.dp,
                        color = Color.Red
                    )
                },
                indicator = {
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(it[tabIndex]),
                        height = 5.dp,
                        color = Color.Green
                    )
                },
                edgePadding = 16.dp
            ) {
                tabData.forEachIndexed { index, pair ->
                    Tab(selected = tabIndex == index, onClick = {
                        tabIndex = index
                    }, text = {
                        Text(
                            text = pair.first,
                            fontWeight = if (tabIndex == index)
                                FontWeight.Bold else FontWeight.Normal
                        )
                    }, icon = {
                        Icon(imageVector = pair.second, contentDescription = null)
                    })
                }
            }

            /* nav controller */
            /*var currentScreen by rememberSaveable {
                mutableStateOf(HomeTab::class.java.simpleName)
            }*/
            val navHostController = rememberNavController()
            val backStackEntry = navHostController.currentBackStackEntryAsState()
            val currentScreen = backStackEntry.value?.destination?.route
            NavHost(
                navController = navHostController,
                startDestination = HomeTab::class.java.simpleName.toString(),
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(
                    HomeTab::class.java.simpleName
                ) {

                }
            }
            /* end - nav controller */

            val modifierText = Modifier.align(alignment = CenterHorizontally)
            val modifierImage = Modifier
                .size(50.dp)
                .fillMaxWidth()
                .align(alignment = CenterHorizontally)

            when (tabIndex) {
                0 -> {
                    Text(
                        text = "Tab 1",
                        modifier = modifierText
                    )
                    Image(
                        painter = painterResource(
                            id = R.drawable.outline_home_black_48dp
                        ),
                        contentDescription = null,
                        modifier = modifierImage
                    )
                }
                1 -> {
                    Text(
                        text = "Tab 2",
                        modifier = modifierText
                    )
                    Image(
                        painter = painterResource(
                            id = R.drawable.outline_local_grocery_store_black_24dp
                        ),
                        contentDescription = null,
                        modifier = modifierImage
                    )
                }
                2 -> {
                    Text(
                        text = "Tab 3",
                        modifierText
                    )
                    Image(
                        painter = painterResource(
                            id = R.drawable.outline_confirmation_number_black_24dp
                        ),
                        contentDescription = null,
                        modifier = modifierImage
                    )
                }
                3 -> {
                    Text(
                        text = "Tab 4",
                        modifierText
                    )
                    Image(
                        painter = painterResource(
                            id = R.drawable.outline_menu_book_black_24dp
                        ),
                        contentDescription = null,
                        modifier = modifierImage
                    )
                }
            }
        }
    }
}