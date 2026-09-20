package ir.seometer.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ir.seometer.app.ui.theme.*

@Composable fun GradientHeader(title:String,subtitle:String=""){
    Box(Modifier.fillMaxWidth().background(Brush.linearGradient(listOf(Navy,Navy2,Blue))).padding(24.dp)){Column(Modifier.padding(top=18.dp)){Text(title,color=Color.White,style=MaterialTheme.typography.headlineMedium,fontWeight=FontWeight.Bold); if(subtitle.isNotBlank())Text(subtitle,color=Color.White.copy(.75f),modifier=Modifier.padding(top=6.dp))}}
}
@Composable fun MetricCard(icon:ImageVector,label:String,value:String,color:Color){Card(shape=RoundedCornerShape(20.dp),colors=CardDefaults.cardColors(containerColor=Color.White),elevation=CardDefaults.cardElevation(2.dp)){Column(Modifier.padding(14.dp),horizontalAlignment=Alignment.CenterHorizontally){Icon(icon,null,tint=color);Spacer(Modifier.height(8.dp));Text(value,fontWeight=FontWeight.Bold);Text(label,color=Muted,style=MaterialTheme.typography.bodySmall)}}}
@Composable fun ScorePill(score:Int){val c=when{score>=80->Green;score>=60->Color(0xFFF59E0B);else->Color(0xFFEF4444)};Surface(color=c.copy(.12f),shape=RoundedCornerShape(50)){Text(score.toString(),color=c,fontWeight=FontWeight.Bold,modifier=Modifier.padding(horizontal=12.dp,vertical=6.dp))}}
