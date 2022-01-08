package vn.deviot.notes

import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import vn.deviot.notes.screen.common.AppBarCenter
import vn.deviot.notes.screen.common.Route
import vn.deviot.notes.screen.login.LoginScreen
import vn.deviot.notes.screen.login.viewmodel.LoginViewModel
import vn.deviot.notes.screen.notes.NoteScreen
import vn.deviot.notes.screen.notes.viewmodel.NoteViewModel
import vn.deviot.notes.ui.theme.NotesTheme
import vn.deviot.notes.utils.TOKEN_ARGS

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val TAG = MainActivity::class.java.simpleName

    private val titleApp: MutableState<Int> = mutableStateOf(R.string.app_name)

    private val loginViewModel: LoginViewModel by viewModels()
    private val noteViewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        setContent {
            NotesTheme {
                val navHostController = rememberNavController()

                if (!TextUtils.isEmpty(loginViewModel.token.value)) {
                    navHostController.navigate(
                        Route.Notes.name + "/${loginViewModel.token.value}"
                    )
                }

                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(topBar = {
                        AppBarCenter(title = stringResource(id = titleApp.value))
                    }) { innerPadding ->
                        NavHost(
                            navController = navHostController,
                            startDestination = Route.Login.name,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable(Route.Login.name) {
                                titleApp.value = Route.Login.title
                                LoginScreen(loginViewModel,
                                    onLoginSuccess = { token ->
                                        navHostController.navigate(
                                            Route.Notes.name + "/$token"
                                        )
                                    })
                            }
                            composable(
                                Route.Notes.name + "/{$TOKEN_ARGS}",
                                arguments = listOf(
                                    navArgument(TOKEN_ARGS) {
                                        type = NavType.StringType
                                    }
                                )
                            ) { entry ->
                                val token = entry.arguments?.getString(TOKEN_ARGS)
                                titleApp.value = Route.Notes.title
                                NoteScreen(token = token, noteViewModel)
                            }
                        }
                    }
                }
            }
        }
    }
}
