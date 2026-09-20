package ir.seometer.app.ui

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ir.seometer.app.ui.screens.AboutScreen
import ir.seometer.app.ui.screens.HistoryScreen
import ir.seometer.app.ui.screens.HomeScreen
import ir.seometer.app.ui.screens.IssuesScreen
import ir.seometer.app.ui.screens.ResultsScreen
import ir.seometer.app.ui.screens.ScanScreen
import ir.seometer.app.ui.screens.SplashScreen
import ir.seometer.app.util.ReportExporter

@Composable
fun SeoMeterApp(vm: AppViewModel = viewModel()) {
    val nav = rememberNavController()
    val state by vm.state.collectAsState()
    val context = LocalContext.current

    NavHost(navController = nav, startDestination = "splash") {
        composable("splash") {
            SplashScreen {
                nav.navigate("home") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }

        composable("home") {
            HomeScreen(
                history = state.history,
                onAnalyze = {
                    vm.analyze(it)
                    nav.navigate("scan")
                },
                onHistory = { nav.navigate("history") },
                onAbout = { nav.navigate("about") }
            )
        }

        composable("scan") {
            ScanScreen(
                progress = state.progress,
                text = state.progressText,
                error = state.error,
                onDone = {
                    nav.navigate("results") {
                        popUpTo("scan") { inclusive = true }
                    }
                },
                onRetry = { nav.popBackStack() }
            )
        }

        composable("results") {
            state.report?.let { report ->
                ResultsScreen(
                    r = report,
                    onIssues = { nav.navigate("issues") },
                    onShare = {
                        val uri = ReportExporter.create(context, report)
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "application/pdf"
                            putExtra(Intent.EXTRA_STREAM, uri)
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        }
                        context.startActivity(Intent.createChooser(intent, "اشتراک گزارش"))
                    },
                    onBack = { nav.popBackStack() }
                )
            }
        }

        composable("issues") {
            state.report?.let { report ->
                IssuesScreen(report, onBack = { nav.popBackStack() })
            }
        }

        composable("history") {
            HistoryScreen(
                history = state.history,
                onHome = {
                    nav.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                onAbout = { nav.navigate("about") }
            )
        }

        composable("about") {
            AboutScreen(
                onHome = {
                    nav.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                onHistory = { nav.navigate("history") }
            )
        }
    }
}
