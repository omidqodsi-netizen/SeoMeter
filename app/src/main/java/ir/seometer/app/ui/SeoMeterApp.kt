package ir.seometer.app.ui

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import ir.seometer.app.ui.screens.*
import ir.seometer.app.util.ReportExporter

@Composable fun SeoMeterApp(vm:AppViewModel=viewModel()){
 val nav=rememberNavController(); val s by vm.state.collectAsState(); val ctx=LocalContext.current
 NavHost(nav,"splash"){
  composable("splash"){SplashScreen{nav.navigate("home"){popUpTo("splash"){inclusive=true}}}}
  composable("home"){HomeScreen(s.history,onAnalyze={vm.analyze(it);nav.navigate("scan")},onHistory={nav.navigate("history")},onAbout={nav.navigate("about")})}
  composable("scan"){ScanScreen(s.progress,s.progressText,s.error,onDone={nav.navigate("results"){popUpTo("scan"){inclusive=true}}},onRetry={nav.popBackStack()})}
  composable("results"){s.report?.let{r->ResultsScreen(r,onIssues={nav.navigate("issues")},onShare={val uri=ReportExporter.create(ctx,r);ctx.startActivity(Intent.createChooser(Intent(Intent.ACTION_SEND).apply{type="application/pdf";putExtra(Intent.EXTRA_STREAM,uri);addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)},"اشتراک گزارش"))},onBack={nav.popBackStack()})}}
  composable("issues"){s.report?.let{IssuesScreen(it,onBack={nav.popBackStack()})}}
  composable("history"){HistoryScreen(s.history,onBack={nav.popBackStack()})}
  composable("about"){AboutScreen(onBack={nav.popBackStack()})}
 }
}
