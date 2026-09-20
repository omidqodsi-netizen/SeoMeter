package ir.seometer.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Analytics
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.Link
import androidx.compose.material.icons.rounded.PictureAsPdf
import androidx.compose.material.icons.rounded.Public
import androidx.compose.material.icons.rounded.Smartphone
import androidx.compose.material.icons.rounded.Speed
import androidx.compose.material.icons.rounded.Title
import androidx.compose.material.icons.rounded.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.seometer.app.model.HistoryItem
import ir.seometer.app.model.SeoReport
import ir.seometer.app.model.Severity
import ir.seometer.app.ui.components.GradientHeader
import ir.seometer.app.ui.components.MetricCard
import ir.seometer.app.ui.components.ScorePill
import ir.seometer.app.ui.theme.Bg
import ir.seometer.app.ui.theme.Blue
import ir.seometer.app.ui.theme.Green
import ir.seometer.app.ui.theme.Muted
import ir.seometer.app.ui.theme.Navy
import ir.seometer.app.ui.theme.Teal
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SplashScreen(onDone: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(1400)
        onDone()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Navy, Color(0xFF083D8C), Color(0xFF0B74E5))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(126.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(Brush.linearGradient(listOf(Blue, Teal, Green))),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Speed,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(72.dp)
                )
            }

            Spacer(Modifier.height(22.dp))
            Text(
                text = "سئومتر",
                fontSize = 38.sp,
                fontWeight = FontWeight.Black,
                color = Color.White
            )
            Text(
                text = "تحلیل هوشمند سئو سایت",
                color = Color.White.copy(alpha = 0.78f)
            )
            Spacer(Modifier.height(50.dp))
            LinearProgressIndicator(
                modifier = Modifier.width(110.dp),
                color = Teal,
                trackColor = Color.White.copy(alpha = 0.12f)
            )
            Spacer(Modifier.height(38.dp))
            Text(
                text = "طراح: امید قدسی زاده",
                color = Color.White.copy(alpha = 0.55f),
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun HomeScreen(
    history: List<HistoryItem>,
    onAnalyze: (String) -> Unit,
    onHistory: () -> Unit,
    onAbout: () -> Unit
) {
    var url by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Bg)
            .verticalScroll(rememberScrollState())
    ) {
        GradientHeader("سلام 👋", "سایتت را تحلیل کن، دقیق‌تر رشد کن")

        Column(Modifier.padding(16.dp)) {
            Card(
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(Modifier.padding(18.dp)) {
                    Text("آدرس سایت خود را وارد کنید", fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(10.dp))
                    OutlinedTextField(
                        value = url,
                        onValueChange = { url = it },
                        leadingIcon = {
                            Icon(Icons.Rounded.Language, contentDescription = null)
                        },
                        placeholder = { Text("example.com") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(12.dp))
                    Button(
                        onClick = { onAnalyze(url) },
                        enabled = url.isNotBlank(),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                    ) {
                        Icon(Icons.Rounded.Analytics, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("شروع تحلیل", fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(Modifier.height(18.dp))
            Text("چه چیزهایی بررسی می‌شود؟", fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(10.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(9.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(Modifier.weight(1f)) {
                    MetricCard(Icons.Rounded.Title, "عنوان و متا", "SEO", Blue)
                }
                Box(Modifier.weight(1f)) {
                    MetricCard(Icons.Rounded.Link, "لینک‌ها", "URL", Teal)
                }
                Box(Modifier.weight(1f)) {
                    MetricCard(Icons.Rounded.Image, "تصاویر", "ALT", Green)
                }
            }

            Spacer(Modifier.height(22.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("سایت‌های اخیر", fontWeight = FontWeight.Bold)
                TextButton(onClick = onHistory) {
                    Text("مشاهده همه")
                }
            }

            history.take(4).forEach { historyItem ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Rounded.Public, contentDescription = null, tint = Blue)
                        Spacer(Modifier.width(10.dp))
                        Column(Modifier.weight(1f)) {
                            Text(
                                historyItem.url
                                    .replace("https://", "")
                                    .replace("http://", ""),
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                formatDate(historyItem.createdAt),
                                color = Muted,
                                fontSize = 11.sp
                            )
                        }
                        ScorePill(historyItem.score)
                    }
                }
            }

            Spacer(Modifier.height(8.dp))
            OutlinedButton(
                onClick = onAbout,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Rounded.Info, contentDescription = null)
                Spacer(Modifier.width(6.dp))
                Text("درباره برنامه و حریم خصوصی")
            }
        }
    }
}

@Composable
fun ScanScreen(
    progress: Int,
    text: String,
    error: String?,
    onDone: () -> Unit,
    onRetry: () -> Unit
) {
    LaunchedEffect(progress) {
        if (progress >= 100) {
            delay(500)
            onDone()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Bg)
    ) {
        GradientHeader(
            "در حال تحلیل سایت...",
            "این فرایند روی دستگاه شما انجام می‌شود"
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            if (error != null) {
                Card(
                    modifier = Modifier.padding(22.dp),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Rounded.ErrorOutline,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(50.dp)
                        )
                        Spacer(Modifier.height(12.dp))
                        Text(error, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(14.dp))
                        Button(onClick = onRetry) {
                            Text("بازگشت و تلاش دوباره")
                        }
                    }
                }
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(
                            progress = { progress / 100f },
                            modifier = Modifier.size(160.dp),
                            strokeWidth = 13.dp,
                            color = Teal,
                            trackColor = Color(0xFFE5EEF8)
                        )
                        Text(
                            "$progress٪",
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Black,
                            color = Navy
                        )
                    }
                    Spacer(Modifier.height(24.dp))
                    Text(text, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(6.dp))
                    Text("صفحه را نبندید", color = Muted)
                }
            }
        }
    }
}

@Composable
fun ResultsScreen(
    r: SeoReport,
    onIssues: () -> Unit,
    onShare: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Bg)
            .verticalScroll(rememberScrollState())
    ) {
        GradientHeader("نتایج تحلیل", r.url)

        Column(Modifier.padding(16.dp)) {
            Card(
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("امتیاز کلی سئو", fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(10.dp))
                    Box(contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(
                            progress = { r.score / 100f },
                            modifier = Modifier.size(140.dp),
                            strokeWidth = 12.dp,
                            color = when {
                                r.score >= 80 -> Green
                                r.score >= 60 -> Color(0xFFF59E0B)
                                else -> Color(0xFFEF4444)
                            },
                            trackColor = Color(0xFFE8EEF7)
                        )
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                r.score.toString(),
                                fontSize = 42.sp,
                                fontWeight = FontWeight.Black
                            )
                            Text("از 100", color = Muted)
                        }
                    }
                    Spacer(Modifier.height(14.dp))
                    Text(
                        when {
                            r.score >= 80 -> "وضعیت عالی"
                            r.score >= 60 -> "وضعیت خوب؛ قابل بهبود"
                            else -> "نیازمند بهبود جدی"
                        },
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(Modifier.height(14.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                SummaryBox(
                    "بحرانی",
                    r.critical,
                    Color(0xFFEF4444),
                    Modifier.weight(1f)
                )
                SummaryBox(
                    "هشدار",
                    r.warnings,
                    Color(0xFFF59E0B),
                    Modifier.weight(1f)
                )
                SummaryBox("تأیید", r.passed, Green, Modifier.weight(1f))
            }

            Spacer(Modifier.height(18.dp))
            Text("شاخص‌های اصلی", fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(10.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(Modifier.weight(1f)) {
                    MetricCard(
                        Icons.Rounded.Speed,
                        "پاسخ",
                        "${r.metrics.responseMs}ms",
                        Blue
                    )
                }
                Box(Modifier.weight(1f)) {
                    MetricCard(
                        Icons.Rounded.Smartphone,
                        "موبایل",
                        r.metrics.mobileScore.toString(),
                        Teal
                    )
                }
                Box(Modifier.weight(1f)) {
                    MetricCard(
                        Icons.Rounded.Link,
                        "لینک",
                        r.metrics.linkScore.toString(),
                        Green
                    )
                }
            }

            Spacer(Modifier.height(18.dp))
            Button(
                onClick = onIssues,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text("مشاهده مشکلات و راه‌حل‌ها")
            }

            Spacer(Modifier.height(8.dp))
            OutlinedButton(
                onClick = onShare,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Rounded.PictureAsPdf, contentDescription = null)
                Spacer(Modifier.width(7.dp))
                Text("ساخت و اشتراک گزارش PDF")
            }

            TextButton(
                onClick = onBack,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("بازگشت")
            }
        }
    }
}

@Composable
private fun SummaryBox(
    label: String,
    n: Int,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        color = color.copy(alpha = 0.1f)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                n.toString(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = color
            )
            Text(label, color = color, fontSize = 12.sp)
        }
    }
}

@Composable
fun IssuesScreen(r: SeoReport, onBack: () -> Unit) {
    var filter by remember { mutableStateOf<Severity?>(null) }
    val filteredIssues = r.issues.filter { issue ->
        filter == null || issue.severity == filter
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Bg)
    ) {
        GradientHeader(
            "مشکلات و پیشنهادها",
            "${r.critical} بحرانی • ${r.warnings} هشدار"
        )

        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            FilterChip(
                selected = filter == null,
                onClick = { filter = null },
                label = { Text("همه") }
            )
            FilterChip(
                selected = filter == Severity.CRITICAL,
                onClick = { filter = Severity.CRITICAL },
                label = { Text("بحرانی") }
            )
            FilterChip(
                selected = filter == Severity.WARNING,
                onClick = { filter = Severity.WARNING },
                label = { Text("هشدار") }
            )
        }

        LazyColumn(
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(filteredIssues) { issue ->
                val issueColor = when (issue.severity) {
                    Severity.CRITICAL -> Color(0xFFEF4444)
                    Severity.WARNING -> Color(0xFFF59E0B)
                    Severity.PASSED -> Green
                }

                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(Modifier.padding(15.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (issue.severity == Severity.PASSED) {
                                    Icons.Rounded.CheckCircle
                                } else {
                                    Icons.Rounded.WarningAmber
                                },
                                contentDescription = null,
                                tint = issueColor
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                issue.title,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Spacer(Modifier.height(7.dp))
                        Text(issue.detail, color = Muted, fontSize = 13.sp)

                        if (issue.severity != Severity.PASSED) {
                            Spacer(Modifier.height(8.dp))
                            Surface(
                                color = Blue.copy(alpha = 0.07f),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    "راه‌حل: ${issue.fix}",
                                    color = Navy,
                                    modifier = Modifier.padding(10.dp),
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }

            item {
                TextButton(
                    onClick = onBack,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("بازگشت")
                }
            }
        }
    }
}

@Composable
fun HistoryScreen(history: List<HistoryItem>, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Bg)
    ) {
        GradientHeader(
            "گزارش‌ها و تاریخچه",
            "نتیجه تحلیل‌های قبلی روی همین دستگاه"
        )

        LazyColumn(
            contentPadding = PaddingValues(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(history) { historyItem ->
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Rounded.Public, contentDescription = null, tint = Blue)
                        Spacer(Modifier.width(10.dp))
                        Column(Modifier.weight(1f)) {
                            Text(historyItem.url, fontWeight = FontWeight.Bold)
                            Text(
                                formatDate(historyItem.createdAt),
                                color = Muted,
                                fontSize = 11.sp
                            )
                        }
                        ScorePill(historyItem.score)
                    }
                }
            }

            item {
                TextButton(
                    onClick = onBack,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("بازگشت")
                }
            }
        }
    }
}

@Composable
fun AboutScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Bg)
            .verticalScroll(rememberScrollState())
    ) {
        GradientHeader("درباره سئومتر", "تحلیل فنی سئو بدون API پولی")

        Column(Modifier.padding(18.dp)) {
            Text("سئومتر", fontSize = 28.sp, fontWeight = FontWeight.Black)
            Text("نسخه 1.0.0", color = Muted)
            Spacer(Modifier.height(18.dp))

            InfoBlock(
                "حریم خصوصی",
                "سئومتر حساب کاربری، تبلیغات، ردیاب و سرویس تحلیل ثالث ندارد. " +
                    "آدرس سایتی که وارد می‌کنید فقط برای دریافت عمومی HTML همان سایت " +
                    "استفاده می‌شود و گزارش روی دستگاه شما ساخته و ذخیره می‌شود."
            )
            InfoBlock(
                "دسترسی‌ها",
                "برنامه فقط به اینترنت و وضعیت شبکه نیاز دارد. دسترسی مکان، مخاطبین، " +
                    "پیامک، دوربین یا حافظه عمومی درخواست نمی‌شود."
            )
            InfoBlock(
                "دقت گزارش",
                "امتیاز سئو یک شاخص داخلی برای اولویت‌بندی مشکلات Technical/On-page است " +
                    "و تضمین‌کننده رتبه در موتور جستجو نیست."
            )
            InfoBlock("طراح", "امید قدسی زاده")

            TextButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("بازگشت")
            }
        }
    }
}

@Composable
private fun InfoBlock(title: String, body: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(Modifier.padding(15.dp)) {
            Text(title, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(6.dp))
            Text(body, color = Muted, lineHeight = 22.sp)
        }
    }
}

private fun formatDate(ms: Long): String {
    return SimpleDateFormat(
        "yyyy/MM/dd HH:mm",
        Locale.getDefault()
    ).format(Date(ms))
}
