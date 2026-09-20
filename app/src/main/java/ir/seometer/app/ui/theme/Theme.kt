package ir.seometer.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Navy=Color(0xFF071A35); val Navy2=Color(0xFF0B1F3B); val Blue=Color(0xFF0EA5FF); val Teal=Color(0xFF14D8C4); val Green=Color(0xFF22C55E); val Bg=Color(0xFFF5F9FF); val Text=Color(0xFF10243F); val Muted=Color(0xFF6C7B90)
private val Light=lightColorScheme(primary=Blue,secondary=Teal,tertiary=Green,background=Bg,surface=Color.White,onPrimary=Color.White,onBackground=Text,onSurface=Text,error=Color(0xFFEF4444))
@Composable fun SeoMeterTheme(content:@Composable()->Unit){MaterialTheme(colorScheme=Light,typography=Typography(),content=content)}
