package ir.seometer.app.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.seometer.app.model.*
import ir.seometer.app.ui.components.*
import ir.seometer.app.ui.theme.*
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@Composable fun SplashScreen(onDone:()->Unit){
    LaunchedEffect(Unit){delay(1400);onDone()}
    Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Navy,Color(0xFF083D8C),Color(0xFF0B74E5)))),contentAlignment=Alignment.Center){
        Column(horizontalAlignment=Alignment.CenterHorizontally){
            Box(Modifier.size(126.dp).clip(RoundedCornerShape(32.dp)).background(Brush.linearGradient(listOf(Blue,Teal,Green))),contentAlignment=Alignment.Center){Icon(Icons.Rounded.Speed,null,tint=Color.White,modifier=Modifier.size(72.dp))}
            Spacer(Modifier.height(22.dp));Text("سئومتر",fontSize=38.sp,fontWeight=FontWeight.Black,color=Color.White);Text("تحلیل هوشمند سئو سایت",color=Color.White.copy(.78f));Spacer(Modifier.height(50.dp));LinearProgressIndicator(modifier=Modifier.width(110.dp),color=Teal,trackColor=Color.White.copy(.12f));Spacer(Modifier.height(38.dp));Text("طراح: امید قدسی زاده",color=Color.White.copy(.55f),fontSize=12.sp)
        }
    }
}

@Composable fun HomeScreen(history:List<HistoryItem>,onAnalyze:(String)->Unit,onHistory:()->Unit,onAbout:()->Unit){
    var url by remember{mutableStateOf("")}
    Column(Modifier.fillMaxSize().background(Bg).verticalScroll(rememberScrollState())){
        GradientHeader("سلام 👋","سایتت را تحلیل کن، دقیق‌تر رشد کن")
        Column(Modifier.padding(16.dp)){
            Card(shape=RoundedCornerShape(26.dp),colors=CardDefaults.cardColors(Color.White),elevation=CardDefaults.cardElevation(4.dp)){Column(Modifier.padding(18.dp)){
                Text("آدرس سایت خود را وارد کنید",fontWeight=FontWeight.Bold);Spacer(Modifier.height(10.dp));OutlinedTextField(value=url,onValueChange={url=it},leadingIcon={Icon(Icons.Rounded.Language,null)},placeholder={Text("example.com")},singleLine=true,keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Uri),shape=RoundedCornerShape(16.dp),modifier=Modifier.fillMaxWidth());Spacer(Modifier.height(12.dp));Button(onClick={onAnalyze(url)},enabled=url.isNotBlank(),shape=RoundedCornerShape(16.dp),modifier=Modifier.fillMaxWidth().height(52.dp)){Icon(Icons.Rounded.Analytics,null);Spacer(Modifier.width(8.dp));Text("شروع تحلیل",fontWeight=FontWeight.Bold)}
            }}
            Spacer(Modifier.height(18.dp));Text("چه چیزهایی بررسی می‌شود؟",fontWeight=FontWeight.Bold);Spacer(Modifier.height(10.dp));Row(horizontalArrangement=Arrangement.spacedBy(9.dp),modifier=Modifier.fillMaxWidth()){MetricCard(Icons.Rounded.Title,"عنوان و متا","SEO",Blue);MetricCard(Icons.Rounded.Link,"لینک‌ها","URL",Teal);MetricCard(Icons.Rounded.Image,"تصاویر","ALT",Green)}
            Spacer(Modifier.height(22.dp));Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween,verticalAlignment=Alignment.CenterVertically){Text("سایت‌های اخیر",fontWeight=FontWeight.Bold);TextButton(onClick=onHistory){Text("مشاهده همه")}}
            history.take(4).forEach{h->Card(Modifier.fillMaxWidth().padding(vertical=5.dp),shape=RoundedCornerShape(18.dp),colors=CardDefaults.cardColors(Color.White)){Row(Modifier.padding(14.dp),verticalAlignment=Alignment.CenterVertically){Icon(Icons.Rounded.Public,null,tint=Blue);Spacer(Modifier.width(10.dp));Column(Modifier.weight(1f)){Text(h.url.replace("https://","").replace("http://",""),fontWeight=FontWeight.SemiBold);Text(date(h.createdAt),color=Muted,fontSize=11.sp)};ScorePill(h.score)}}}
            Spacer(Modifier.height(8.dp));OutlinedButton(onClick=onAbout,shape=RoundedCornerShape(16.dp),modifier=Modifier.fillMaxWidth()){Icon(Icons.Rounded.Info,null);Spacer(Modifier.width(6.dp));Text("درباره برنامه و حریم خصوصی")}
        }
    }
}

@Composable fun ScanScreen(progress:Int,text:String,error:String?,onDone:()->Unit,onRetry:()->Unit){
    LaunchedEffect(progress){if(progress>=100){delay(500);onDone()}}
    Column(Modifier.fillMaxSize().background(Bg)){GradientHeader("در حال تحلیل سایت...","این فرایند روی دستگاه شما انجام می‌شود");Box(Modifier.weight(1f).fillMaxWidth(),contentAlignment=Alignment.Center){
        if(error!=null) Card(Modifier.padding(22.dp),shape=RoundedCornerShape(24.dp)){Column(Modifier.padding(24.dp),horizontalAlignment=Alignment.CenterHorizontally){Icon(Icons.Rounded.ErrorOutline,null,tint=MaterialTheme.colorScheme.error,modifier=Modifier.size(50.dp));Spacer(Modifier.height(12.dp));Text(error,fontWeight=FontWeight.Bold);Spacer(Modifier.height(14.dp));Button(onClick=onRetry){Text("بازگشت و تلاش دوباره")}}}
        else Column(horizontalAlignment=Alignment.CenterHorizontally){Box(contentAlignment=Alignment.Center){CircularProgressIndicator(progress={progress/100f},modifier=Modifier.size(160.dp),strokeWidth=13.dp,color=Teal,trackColor=Color(0xFFE5EEF8));Text("$progress٪",fontSize=34.sp,fontWeight=FontWeight.Black,color=Navy)};Spacer(Modifier.height(24.dp));Text(text,fontWeight=FontWeight.Bold);Spacer(Modifier.height(6.dp));Text("صفحه را نبندید",color=Muted)}
    }}
}

@Composable fun ResultsScreen(r:SeoReport,onIssues:()->Unit,onShare:()->Unit,onBack:()->Unit){
    Column(Modifier.fillMaxSize().background(Bg).verticalScroll(rememberScrollState())){GradientHeader("نتایج تحلیل",r.url);Column(Modifier.padding(16.dp)){
        Card(shape=RoundedCornerShape(26.dp),colors=CardDefaults.cardColors(Color.White),elevation=CardDefaults.cardElevation(3.dp)){Column(Modifier.padding(18.dp),horizontalAlignment=Alignment.CenterHorizontally){Text("امتیاز کلی سئو",fontWeight=FontWeight.Bold);Spacer(Modifier.height(10.dp));Box(contentAlignment=Alignment.Center){CircularProgressIndicator(progress={r.score/100f},modifier=Modifier.size(140.dp),strokeWidth=12.dp,color=if(r.score>=80)Green else if(r.score>=60)Color(0xFFF59E0B) else Color(0xFFEF4444),trackColor=Color(0xFFE8EEF7));Column(horizontalAlignment=Alignment.CenterHorizontally){Text(r.score.toString(),fontSize=42.sp,fontWeight=FontWeight.Black);Text("از 100",color=Muted)}};Spacer(Modifier.height(14.dp));Text(if(r.score>=80)"وضعیت عالی" else if(r.score>=60)"وضعیت خوب؛ قابل بهبود" else "نیازمند بهبود جدی",fontWeight=FontWeight.Bold)} }
        Spacer(Modifier.height(14.dp));Row(horizontalArrangement=Arrangement.spacedBy(8.dp),modifier=Modifier.fillMaxWidth()){SummaryBox("بحرانی",r.critical,Color(0xFFEF4444),Modifier.weight(1f));SummaryBox("هشدار",r.warnings,Color(0xFFF59E0B),Modifier.weight(1f));SummaryBox("تأیید",r.passed,Green,Modifier.weight(1f))}
        Spacer(Modifier.height(18.dp));Text("شاخص‌های اصلی",fontWeight=FontWeight.Bold);Spacer(Modifier.height(10.dp));Row(horizontalArrangement=Arrangement.spacedBy(8.dp)){MetricCard(Icons.Rounded.Speed,"پاسخ","${r.metrics.responseMs}ms",Blue);MetricCard(Icons.Rounded.Smartphone,"موبایل",r.metrics.mobileScore.toString(),Teal);MetricCard(Icons.Rounded.Link,"لینک",r.metrics.linkScore.toString(),Green)}
        Spacer(Modifier.height(18.dp));Button(onClick=onIssues,shape=RoundedCornerShape(16.dp),modifier=Modifier.fillMaxWidth().height(52.dp)){Text("مشاهده مشکلات و راه‌حل‌ها")};Spacer(Modifier.height(8.dp));OutlinedButton(onClick=onShare,shape=RoundedCornerShape(16.dp),modifier=Modifier.fillMaxWidth()){Icon(Icons.Rounded.PictureAsPdf,null);Spacer(Modifier.width(7.dp));Text("ساخت و اشتراک گزارش PDF")};TextButton(onClick=onBack,modifier=Modifier.align(Alignment.CenterHorizontally)){Text("بازگشت")}
    }}
}

@Composable private fun SummaryBox(label:String,n:Int,color:Color,modifier:Modifier=Modifier){Surface(modifier,shape=RoundedCornerShape(18.dp),color=color.copy(.1f)){Column(Modifier.padding(14.dp),horizontalAlignment=Alignment.CenterHorizontally){Text(n.toString(),fontSize=24.sp,fontWeight=FontWeight.Black,color=color);Text(label,color=color,fontSize=12.sp)}}}

@Composable fun IssuesScreen(r:SeoReport,onBack:()->Unit){
    var filter by remember{mutableStateOf<Severity?>(null)}
    Column(Modifier.fillMaxSize().background(Bg)){GradientHeader("مشکلات و پیشنهادها","${r.critical} بحرانی • ${r.warnings} هشدار");Row(Modifier.padding(12.dp),horizontalArrangement=Arrangement.spacedBy(6.dp)){FilterChip(filter==null,{filter=null},{Text("همه")});FilterChip(filter==Severity.CRITICAL,{filter=Severity.CRITICAL},{Text("بحرانی")});FilterChip(filter==Severity.WARNING,{filter=Severity.WARNING},{Text("هشدار")})};LazyColumn(contentPadding=PaddingValues(12.dp),verticalArrangement=Arrangement.spacedBy(10.dp)){items(r.issues.filter{filter==null||it.severity==filter}.size){i->val item=r.issues.filter{filter==null||it.severity==filter}[i];val c=when(item.severity){Severity.CRITICAL->Color(0xFFEF4444);Severity.WARNING->Color(0xFFF59E0B);Severity.PASSED->Green};Card(shape=RoundedCornerShape(20.dp),colors=CardDefaults.cardColors(Color.White)){Column(Modifier.padding(15.dp)){Row(verticalAlignment=Alignment.CenterVertically){Icon(if(item.severity==Severity.PASSED)Icons.Rounded.CheckCircle else Icons.Rounded.WarningAmber,null,tint=c);Spacer(Modifier.width(8.dp));Text(item.title,fontWeight=FontWeight.Bold,modifier=Modifier.weight(1f))};Spacer(Modifier.height(7.dp));Text(item.detail,color=Muted,fontSize=13.sp);if(item.severity!=Severity.PASSED){Spacer(Modifier.height(8.dp));Surface(color=Blue.copy(.07f),shape=RoundedCornerShape(12.dp)){Text("راه‌حل: ${item.fix}",color=Navy,modifier=Modifier.padding(10.dp),fontSize=12.sp)}}}}}};item{TextButton(onClick=onBack,modifier=Modifier.fillMaxWidth()){Text("بازگشت")}}}
    }
}

@Composable fun HistoryScreen(history:List<HistoryItem>,onBack:()->Unit){Column(Modifier.fillMaxSize().background(Bg)){GradientHeader("گزارش‌ها و تاریخچه","نتیجه تحلیل‌های قبلی روی همین دستگاه");LazyColumn(contentPadding=PaddingValues(14.dp),verticalArrangement=Arrangement.spacedBy(8.dp)){items(history.size){i->val h=history[i];Card(shape=RoundedCornerShape(18.dp),colors=CardDefaults.cardColors(Color.White)){Row(Modifier.padding(14.dp),verticalAlignment=Alignment.CenterVertically){Icon(Icons.Rounded.Public,null,tint=Blue);Spacer(Modifier.width(10.dp));Column(Modifier.weight(1f)){Text(h.url,fontWeight=FontWeight.Bold);Text(date(h.createdAt),color=Muted,fontSize=11.sp)};ScorePill(h.score)}}};item{TextButton(onClick=onBack,modifier=Modifier.fillMaxWidth()){Text("بازگشت")}}}}

@Composable fun AboutScreen(onBack:()->Unit){Column(Modifier.fillMaxSize().background(Bg).verticalScroll(rememberScrollState())){GradientHeader("درباره سئومتر","تحلیل فنی سئو بدون API پولی");Column(Modifier.padding(18.dp)){Text("سئومتر",fontSize=28.sp,fontWeight=FontWeight.Black);Text("نسخه 1.0.0",color=Muted);Spacer(Modifier.height(18.dp));InfoBlock("حریم خصوصی","سئومتر حساب کاربری، تبلیغات، ردیاب و سرویس تحلیل ثالث ندارد. آدرس سایتی که وارد می‌کنید فقط برای دریافت عمومی HTML همان سایت استفاده می‌شود و گزارش روی دستگاه شما ساخته و ذخیره می‌شود.");InfoBlock("دسترسی‌ها","برنامه فقط به اینترنت و وضعیت شبکه نیاز دارد. دسترسی مکان، مخاطبین، پیامک، دوربین یا حافظه عمومی درخواست نمی‌شود.");InfoBlock("دقت گزارش","امتیاز سئو یک شاخص داخلی برای اولویت‌بندی مشکلات Technical/On-page است و تضمین‌کننده رتبه در موتور جستجو نیست.");InfoBlock("طراح","امید قدسی زاده");TextButton(onClick=onBack,modifier=Modifier.fillMaxWidth()){Text("بازگشت")}}}}
@Composable private fun InfoBlock(t:String,b:String){Card(Modifier.fillMaxWidth().padding(bottom=10.dp),shape=RoundedCornerShape(18.dp),colors=CardDefaults.cardColors(Color.White)){Column(Modifier.padding(15.dp)){Text(t,fontWeight=FontWeight.Bold);Spacer(Modifier.height(6.dp));Text(b,color=Muted,lineHeight=22.sp)}}}
private fun date(ms:Long)=SimpleDateFormat("yyyy/MM/dd HH:mm",Locale.getDefault()).format(Date(ms))
