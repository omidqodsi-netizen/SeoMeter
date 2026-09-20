package ir.seometer.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.seometer.app.model.HistoryItem
import ir.seometer.app.model.SeoIssue
import ir.seometer.app.model.SeoReport
import ir.seometer.app.model.Severity
import ir.seometer.app.ui.components.*
import ir.seometer.app.ui.theme.*
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SplashScreen(onDone: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(1500)
        onDone()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF06172F),
                        Color(0xFF072E61),
                        Color(0xFF0C5FA8)
                    )
                )
            )
    ) {
        Box(
            modifier = Modifier
                .size(270.dp)
                .offset(x = 170.dp, y = (-70).dp)
                .clip(CircleShape)
                .background(Blue.copy(alpha = 0.20f))
        )
        Box(
            modifier = Modifier
                .size(220.dp)
                .align(Alignment.BottomStart)
                .offset(x = (-100).dp, y = 70.dp)
                .clip(CircleShape)
                .background(Teal.copy(alpha = 0.20f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            BrandLogo(Modifier.size(126.dp))
            Spacer(Modifier.height(26.dp))
            Text(
                text = "سئومتر",
                color = Color.White,
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Black
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = "تحلیل هوشمند سئو سایت",
                color = Color.White.copy(alpha = 0.90f),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(5.dp))
            Text(
                text = "سایت بهتر، فرصت‌های بیشتر",
                color = Color.White.copy(alpha = 0.60f),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(48.dp))
            LinearProgressIndicator(
                modifier = Modifier
                    .width(108.dp)
                    .height(4.dp)
                    .clip(CircleShape),
                color = Teal,
                trackColor = Color.White.copy(alpha = 0.12f)
            )
        }

        Text(
            text = "طراح: امید قدسی زاده",
            color = Color.White.copy(alpha = 0.46f),
            fontSize = 11.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 30.dp)
        )
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

    Scaffold(
        containerColor = Bg,
        bottomBar = {
            SeoBottomBar(
                selected = 0,
                onHome = {},
                onHistory = onHistory,
                onAbout = onAbout
            )
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
                .background(Bg)
                .verticalScroll(rememberScrollState())
        ) {
            PremiumHeader(
                title = "سلام 👋",
                subtitle = "سایتت را تحلیل کن، دقیق‌تر رشد کن",
                showLogo = true
            )

            Column(Modifier.padding(horizontal = 16.dp, vertical = 18.dp)) {
                PremiumCard {
                    Text(
                        text = "آدرس سایت خود را وارد کنید",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(12.dp))

                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                        OutlinedTextField(
                            value = url,
                            onValueChange = { url = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("example.com") },
                            leadingIcon = { Icon(Icons.Rounded.Language, null, tint = Blue) },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
                            shape = RoundedCornerShape(17.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Blue,
                                unfocusedBorderColor = Border,
                                focusedContainerColor = SurfaceSoft,
                                unfocusedContainerColor = SurfaceSoft
                            )
                        )
                    }

                    Spacer(Modifier.height(12.dp))
                    PrimaryActionButton(
                        text = "شروع تحلیل",
                        enabled = url.isNotBlank(),
                        icon = Icons.Rounded.Analytics,
                        onClick = { onAnalyze(url) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(Modifier.height(22.dp))
                SectionTitle("چه چیزهایی بررسی می‌شود؟")
                Spacer(Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    FeatureTile(
                        icon = Icons.Rounded.Title,
                        value = "SEO",
                        label = "عنوان و متا",
                        tint = Blue,
                        modifier = Modifier.weight(1f)
                    )
                    FeatureTile(
                        icon = Icons.Rounded.Link,
                        value = "URL",
                        label = "لینک‌ها",
                        tint = Teal,
                        modifier = Modifier.weight(1f)
                    )
                    FeatureTile(
                        icon = Icons.Rounded.Image,
                        value = "ALT",
                        label = "تصاویر",
                        tint = Green,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    SectionTitle("سایت‌های اخیر")
                    TextButton(onClick = onHistory) {
                        Text("مشاهده همه", color = Blue, fontWeight = FontWeight.Bold)
                    }
                }

                if (history.isEmpty()) {
                    PremiumCard(modifier = Modifier.fillMaxWidth()) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                Modifier
                                    .size(42.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(Blue.copy(alpha = 0.10f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Rounded.History, null, tint = Blue)
                            }
                            Spacer(Modifier.width(12.dp))
                            Column {
                                Text("هنوز گزارشی ندارید", fontWeight = FontWeight.Bold)
                                Text("اولین سایت را تحلیل کنید تا اینجا ذخیره شود.", color = Muted, fontSize = 12.sp)
                            }
                        }
                    }
                } else {
                    history.take(4).forEach { item ->
                        RecentSiteCard(item)
                        Spacer(Modifier.height(9.dp))
                    }
                }

                Spacer(Modifier.height(14.dp))
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
            delay(550)
            onDone()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Bg)
    ) {
        PremiumHeader(
            title = "در حال تحلیل سایت...",
            subtitle = "در حال بررسی بخش‌های مختلف سئو",
            compact = true
        )

        if (error != null) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                PremiumCard(Modifier.padding(20.dp)) {
                    Box(
                        Modifier
                            .size(58.dp)
                            .align(Alignment.CenterHorizontally)
                            .clip(CircleShape)
                            .background(Danger.copy(alpha = 0.10f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Rounded.ErrorOutline, null, tint = Danger, modifier = Modifier.size(30.dp))
                    }
                    Spacer(Modifier.height(14.dp))
                    Text(error, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                    Spacer(Modifier.height(14.dp))
                    PrimaryActionButton("بازگشت و تلاش دوباره", onClick = onRetry, modifier = Modifier.fillMaxWidth())
                }
            }
            return
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PremiumCard(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 28.dp, horizontal = 18.dp)
            ) {
                Box(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        progress = { progress.coerceIn(0, 100) / 100f },
                        modifier = Modifier.size(154.dp),
                        color = Teal,
                        trackColor = Color(0xFFE6EEF8),
                        strokeWidth = 12.dp
                    )
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${progress.coerceIn(0, 100)}٪".toFaDigits(),
                            fontSize = 37.sp,
                            fontWeight = FontWeight.Black,
                            color = Navy
                        )
                        Text("پیشرفت", color = Muted, fontSize = 11.sp)
                    }
                }
                Spacer(Modifier.height(18.dp))
                Text(
                    text = text.ifBlank { "آماده‌سازی تحلیل" },
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "این فرایند ممکن است چند ثانیه زمان ببرد",
                    color = Muted,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            Spacer(Modifier.height(18.dp))
            PremiumCard(Modifier.fillMaxWidth()) {
                ScanStep("عنوان‌ها و متادیتا", progress >= 20, progress in 6..19)
                StepDivider()
                ScanStep("تصاویر و تجربه موبایل", progress >= 40, progress in 20..39)
                StepDivider()
                ScanStep("لینک‌ها، robots و sitemap", progress >= 75, progress in 40..74)
                StepDivider()
                ScanStep("خزش صفحات و امتیاز نهایی", progress >= 96, progress in 75..95)
            }

            Spacer(Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Rounded.Lightbulb, null, tint = Blue, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(6.dp))
                Text("برای نتیجه کامل، صفحه را نبندید.", color = Muted, fontSize = 12.sp)
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
        PremiumHeader(
            title = "نتایج تحلیل",
            subtitle = cleanHost(r.url),
            compact = true
        )

        Column(Modifier.padding(16.dp)) {
            PremiumCard(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(20.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    ScoreRing(r.score, Modifier.size(128.dp))
                    Spacer(Modifier.width(18.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("امتیاز کلی سئو", style = MaterialTheme.typography.titleLarge)
                        Spacer(Modifier.height(7.dp))
                        Surface(
                            shape = RoundedCornerShape(50.dp),
                            color = scoreColor(r.score).copy(alpha = 0.10f)
                        ) {
                            Text(
                                text = scoreLabel(r.score),
                                color = scoreColor(r.score),
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                        Spacer(Modifier.height(9.dp))
                        Text(
                            text = "با رفع موارد مهم، ساختار فنی سایت را بهتر کنید.",
                            color = Muted,
                            fontSize = 12.sp,
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            Spacer(Modifier.height(14.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(9.dp)
            ) {
                SummaryTile("بحرانی", r.critical, Danger, Modifier.weight(1f))
                SummaryTile("هشدار", r.warnings, Warning, Modifier.weight(1f))
                SummaryTile("تأیید", r.passed, Green, Modifier.weight(1f))
            }

            Spacer(Modifier.height(24.dp))
            SectionTitle("شاخص‌های اصلی")
            Spacer(Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                FeatureTile(Icons.Rounded.Speed, "${r.metrics.responseMs}ms".toFaDigits(), "پاسخ", Blue, Modifier.weight(1f))
                FeatureTile(Icons.Rounded.Smartphone, r.metrics.mobileScore.toString().toFaDigits(), "موبایل", Teal, Modifier.weight(1f))
                FeatureTile(Icons.Rounded.Link, r.metrics.linkScore.toString().toFaDigits(), "لینک", Green, Modifier.weight(1f))
            }

            Spacer(Modifier.height(22.dp))
            PrimaryActionButton(
                text = "مشاهده مشکلات و راه‌حل‌ها",
                icon = Icons.Rounded.FactCheck,
                onClick = onIssues,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(9.dp))
            OutlineActionButton(
                text = "ساخت و اشتراک گزارش PDF",
                icon = Icons.Rounded.PictureAsPdf,
                onClick = onShare,
                modifier = Modifier.fillMaxWidth()
            )
            TextButton(onClick = onBack, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text("بازگشت", color = Muted)
            }
        }
    }
}

@Composable
fun IssuesScreen(r: SeoReport, onBack: () -> Unit) {
    var filter by remember { mutableStateOf<Severity?>(null) }
    val list = remember(r.issues, filter) {
        r.issues.filter { filter == null || it.severity == filter }
    }

    Column(Modifier.fillMaxSize().background(Bg)) {
        PremiumHeader(
            title = "مشکلات و پیشنهادها",
            subtitle = "${r.critical.toString().toFaDigits()} بحرانی • ${r.warnings.toString().toFaDigits()} هشدار",
            compact = true
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PremiumFilter("همه", filter == null) { filter = null }
            PremiumFilter("بحرانی", filter == Severity.CRITICAL) { filter = Severity.CRITICAL }
            PremiumFilter("هشدار", filter == Severity.WARNING) { filter = Severity.WARNING }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(list) { issue -> IssueCard(issue) }
            item {
                TextButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
                    Text("بازگشت", color = Muted)
                }
            }
        }
    }
}

@Composable
fun HistoryScreen(
    history: List<HistoryItem>,
    onHome: () -> Unit,
    onAbout: () -> Unit
) {
    Scaffold(
        containerColor = Bg,
        bottomBar = {
            SeoBottomBar(
                selected = 1,
                onHome = onHome,
                onHistory = {},
                onAbout = onAbout
            )
        }
    ) { inner ->
        Column(Modifier.padding(inner).fillMaxSize().background(Bg)) {
            PremiumHeader(
                title = "گزارش‌ها و تاریخچه",
                subtitle = "تحلیل‌های ذخیره‌شده روی همین دستگاه",
                compact = true
            )
            if (history.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            Modifier.size(72.dp).clip(CircleShape).background(Blue.copy(alpha = 0.09f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Rounded.Assessment, null, tint = Blue, modifier = Modifier.size(34.dp))
                        }
                        Spacer(Modifier.height(12.dp))
                        Text("هنوز گزارشی ذخیره نشده", fontWeight = FontWeight.Bold)
                        Text("بعد از اولین تحلیل، گزارش اینجا نمایش داده می‌شود.", color = Muted, fontSize = 12.sp)
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(history) { RecentSiteCard(it) }
                }
            }
        }
    }
}

@Composable
fun AboutScreen(
    onHome: () -> Unit,
    onHistory: () -> Unit
) {
    Scaffold(
        containerColor = Bg,
        bottomBar = {
            SeoBottomBar(
                selected = 2,
                onHome = onHome,
                onHistory = onHistory,
                onAbout = {}
            )
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
                .background(Bg)
                .verticalScroll(rememberScrollState())
        ) {
            PremiumHeader(
                title = "درباره سئومتر",
                subtitle = "یک ابزار ساده برای بررسی سئوی فنی",
                showLogo = true,
                compact = true
            )
            Column(Modifier.padding(16.dp)) {
                InfoBlock(
                    icon = Icons.Rounded.PrivacyTip,
                    title = "حریم خصوصی",
                    body = "سئومتر حساب کاربری، تبلیغات و ردیاب ندارد. آدرس سایت فقط برای دریافت اطلاعات عمومی همان سایت استفاده می‌شود."
                )
                InfoBlock(
                    icon = Icons.Rounded.Security,
                    title = "دسترسی‌ها",
                    body = "برنامه فقط برای تحلیل سایت به اینترنت و وضعیت شبکه نیاز دارد و دسترسی حساس دیگری درخواست نمی‌کند."
                )
                InfoBlock(
                    icon = Icons.Rounded.Info,
                    title = "دقت گزارش",
                    body = "امتیاز سئو یک شاخص داخلی برای اولویت‌بندی مشکلات Technical و On-page است و تضمین رتبه گوگل نیست."
                )
                InfoBlock(
                    icon = Icons.Rounded.Person,
                    title = "طراح",
                    body = "امید قدسی زاده • نسخه ۱.۱.۰"
                )
            }
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(text = text, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black)
}

@Composable
private fun RecentSiteCard(item: HistoryItem) {
    PremiumCard(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 15.dp, vertical = 14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Blue.copy(alpha = 0.10f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Rounded.Public, null, tint = Blue)
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                LtrText(
                    text = cleanHost(item.url),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14
                )
                Spacer(Modifier.height(2.dp))
                LtrText(formatDate(item.createdAt), color = Muted, fontSize = 11)
            }
            ScorePill(item.score)
        }
    }
}

@Composable
private fun ScanStep(title: String, done: Boolean, active: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(
                    when {
                        done -> Green.copy(alpha = 0.12f)
                        active -> Blue.copy(alpha = 0.12f)
                        else -> SurfaceSoft
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            when {
                done -> Icon(Icons.Rounded.Check, null, tint = Green, modifier = Modifier.size(18.dp))
                active -> CircularProgressIndicator(modifier = Modifier.size(17.dp), strokeWidth = 2.dp, color = Blue)
                else -> Box(Modifier.size(8.dp).clip(CircleShape).background(Border))
            }
        }
        Spacer(Modifier.width(11.dp))
        Text(title, fontWeight = if (active) FontWeight.Bold else FontWeight.Medium, color = if (done || active) Text else Muted)
    }
}

@Composable
private fun StepDivider() {
    HorizontalDivider(color = Border, thickness = 1.dp)
}

@Composable
private fun ScoreRing(score: Int, modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            progress = { score / 100f },
            modifier = Modifier.fillMaxSize(),
            color = scoreColor(score),
            trackColor = Color(0xFFE8EEF7),
            strokeWidth = 11.dp
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(score.toString().toFaDigits(), fontSize = 35.sp, fontWeight = FontWeight.Black, color = Navy)
            Text("از ۱۰۰", color = Muted, fontSize = 11.sp)
        }
    }
}

@Composable
private fun PremiumFilter(text: String, selected: Boolean, onClick: () -> Unit) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(text, fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium) },
        shape = RoundedCornerShape(14.dp),
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = Lavender,
            selectedLabelColor = Navy,
            containerColor = Color.White,
            labelColor = Muted
        )
    )
}

@Composable
private fun IssueCard(issue: SeoIssue) {
    val color = when (issue.severity) {
        Severity.CRITICAL -> Danger
        Severity.WARNING -> Warning
        Severity.PASSED -> Green
    }
    val icon = when (issue.severity) {
        Severity.PASSED -> Icons.Rounded.CheckCircle
        Severity.WARNING -> Icons.Rounded.WarningAmber
        Severity.CRITICAL -> Icons.Rounded.Error
    }

    PremiumCard(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.Top) {
            Box(
                Modifier.size(40.dp).clip(RoundedCornerShape(14.dp)).background(color.copy(alpha = 0.10f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, null, tint = color, modifier = Modifier.size(22.dp))
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(issue.title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(4.dp))
                Text(issue.detail, color = Muted, fontSize = 12.sp, lineHeight = 20.sp)
            }
        }

        if (issue.severity != Severity.PASSED && issue.fix.isNotBlank()) {
            Spacer(Modifier.height(12.dp))
            Surface(
                color = Blue.copy(alpha = 0.06f),
                shape = RoundedCornerShape(15.dp)
            ) {
                Row(Modifier.padding(12.dp), verticalAlignment = Alignment.Top) {
                    Icon(Icons.Rounded.AutoFixHigh, null, tint = Blue, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(7.dp))
                    Text("راه‌حل: ${issue.fix}", color = Navy, fontSize = 12.sp, lineHeight = 20.sp)
                }
            }
        }
    }
}

@Composable
private fun InfoBlock(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, body: String) {
    PremiumCard(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.Top) {
            Box(
                Modifier.size(42.dp).clip(RoundedCornerShape(14.dp)).background(Blue.copy(alpha = 0.09f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, null, tint = Blue, modifier = Modifier.size(21.dp))
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(4.dp))
                Text(body, color = Muted, lineHeight = 22.sp, fontSize = 13.sp)
            }
        }
    }
    Spacer(Modifier.height(10.dp))
}

private fun scoreColor(score: Int): Color = when {
    score >= 80 -> Green
    score >= 60 -> Warning
    else -> Danger
}

private fun scoreLabel(score: Int): String = when {
    score >= 85 -> "وضعیت عالی"
    score >= 70 -> "وضعیت خوب"
    score >= 55 -> "قابل بهبود"
    else -> "نیازمند بهبود جدی"
}

private fun cleanHost(url: String): String {
    return url
        .removePrefix("https://")
        .removePrefix("http://")
        .trimEnd('/')
}

private fun formatDate(ms: Long): String {
    return SimpleDateFormat("yyyy/MM/dd  HH:mm", Locale.getDefault())
        .format(Date(ms))
        .toFaDigits()
}
