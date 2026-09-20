package ir.seometer.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.seometer.app.R
import ir.seometer.app.ui.theme.*

@Composable
fun BrandLogo(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.seometer_logo),
        contentDescription = "سئومتر",
        modifier = modifier.clip(RoundedCornerShape(24.dp))
    )
}

@Composable
fun PremiumHeader(
    title: String,
    subtitle: String = "",
    showLogo: Boolean = false,
    compact: Boolean = false
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.horizontalGradient(
                    listOf(Navy, Color(0xFF08376D), Color(0xFF10A8F4))
                )
            )
            .padding(
                start = 20.dp,
                end = 20.dp,
                top = if (compact) 20.dp else 28.dp,
                bottom = if (compact) 22.dp else 28.dp
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = Color.White,
                    style = if (compact) MaterialTheme.typography.headlineMedium else MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Black
                )
                if (subtitle.isNotBlank()) {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = subtitle,
                        color = Color.White.copy(alpha = 0.72f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            if (showLogo) {
                Spacer(Modifier.width(14.dp))
                BrandLogo(Modifier.size(if (compact) 48.dp else 58.dp))
            }
        }
    }
}

@Composable
fun PremiumCard(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(18.dp),
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier.padding(contentPadding),
            content = content
        )
    }
}

@Composable
fun PrimaryActionButton(
    text: String,
    enabled: Boolean = true,
    icon: ImageVector? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.height(54.dp),
        shape = RoundedCornerShape(17.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Blue,
            contentColor = Color.White,
            disabledContainerColor = Color(0xFFE2E8F2),
            disabledContentColor = Color(0xFF9BA7B7)
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = if (enabled) 2.dp else 0.dp)
    ) {
        if (icon != null) {
            Icon(icon, null, modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(8.dp))
        }
        Text(text, fontWeight = FontWeight.Bold, fontSize = 15.sp)
    }
}

@Composable
fun OutlineActionButton(
    text: String,
    icon: ImageVector? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(52.dp),
        shape = RoundedCornerShape(17.dp),
        border = BorderStroke(1.dp, Border),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Blue)
    ) {
        if (icon != null) {
            Icon(icon, null, modifier = Modifier.size(19.dp))
            Spacer(Modifier.width(8.dp))
        }
        Text(text, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun FeatureTile(
    icon: ImageVector,
    value: String,
    label: String,
    tint: Color,
    modifier: Modifier = Modifier
) {
    PremiumCard(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 15.dp)
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(13.dp))
                .background(tint.copy(alpha = 0.11f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, null, tint = tint, modifier = Modifier.size(21.dp))
        }
        Spacer(Modifier.height(9.dp))
        Text(
            text = value,
            fontSize = 19.sp,
            fontWeight = FontWeight.Black,
            color = Text,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Muted,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun SummaryTile(
    label: String,
    value: Int,
    tint: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        color = tint.copy(alpha = 0.10f)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(value.toString().toFaDigits(), color = tint, fontSize = 27.sp, fontWeight = FontWeight.Black)
            Spacer(Modifier.height(2.dp))
            Text(label, color = tint, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun ScorePill(score: Int) {
    val c = when {
        score >= 80 -> Green
        score >= 60 -> Warning
        else -> Danger
    }
    Surface(color = c.copy(alpha = 0.12f), shape = RoundedCornerShape(50.dp)) {
        Text(
            text = score.toString().toFaDigits(),
            color = c,
            fontWeight = FontWeight.Black,
            modifier = Modifier.padding(horizontal = 13.dp, vertical = 7.dp)
        )
    }
}

@Composable
fun LtrText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Text,
    fontSize: Int = 14,
    fontWeight: FontWeight = FontWeight.Normal
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Text(
            text = text,
            modifier = modifier,
            color = color,
            fontSize = fontSize.sp,
            fontWeight = fontWeight,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
fun SeoBottomBar(
    selected: Int,
    onHome: () -> Unit,
    onHistory: () -> Unit,
    onAbout: () -> Unit
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp,
        modifier = Modifier.height(76.dp)
    ) {
        NavigationBarItem(
            selected = selected == 0,
            onClick = onHome,
            icon = { Icon(Icons.Rounded.Home, null) },
            label = { Text("خانه") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Blue,
                selectedTextColor = Blue,
                indicatorColor = Blue.copy(alpha = 0.10f),
                unselectedIconColor = Muted,
                unselectedTextColor = Muted
            )
        )
        NavigationBarItem(
            selected = selected == 1,
            onClick = onHistory,
            icon = { Icon(Icons.Rounded.Assessment, null) },
            label = { Text("گزارش‌ها") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Blue,
                selectedTextColor = Blue,
                indicatorColor = Blue.copy(alpha = 0.10f),
                unselectedIconColor = Muted,
                unselectedTextColor = Muted
            )
        )
        NavigationBarItem(
            selected = selected == 2,
            onClick = onAbout,
            icon = { Icon(Icons.Rounded.Info, null) },
            label = { Text("درباره") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Blue,
                selectedTextColor = Blue,
                indicatorColor = Blue.copy(alpha = 0.10f),
                unselectedIconColor = Muted,
                unselectedTextColor = Muted
            )
        )
    }
}

fun String.toFaDigits(): String {
    val en = "0123456789"
    val fa = "۰۱۲۳۴۵۶۷۸۹"
    var out = this
    en.forEachIndexed { i, c -> out = out.replace(c, fa[i]) }
    return out
}
