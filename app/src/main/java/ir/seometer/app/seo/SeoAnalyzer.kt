package ir.seometer.app.seo

import ir.seometer.app.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.net.URI
import java.util.concurrent.TimeUnit

class SeoAnalyzer {
    private val client = OkHttpClient.Builder()
        .followRedirects(true).connectTimeout(12, TimeUnit.SECONDS).readTimeout(15, TimeUnit.SECONDS).build()

    suspend fun analyze(input: String, onProgress: (Int, String) -> Unit): SeoReport = withContext(Dispatchers.IO) {
        val base = normalize(input)
        val issues = mutableListOf<SeoIssue>()
        fun add(t:String,d:String,f:String,s:Severity)=issues.add(SeoIssue(t,d,f,s))
        fun pass(t:String)=add(t,"این مورد به‌درستی تنظیم شده است.","نیازی به تغییر نیست.",Severity.PASSED)

        onProgress(6,"دریافت صفحه اصلی")
        val fetch = fetchPage(base)
        val doc = fetch.doc ?: throw IllegalStateException("امکان دریافت سایت وجود ندارد. کد پاسخ: ${fetch.code}")
        val finalUrl = fetch.url
        val uri = URI(finalUrl)
        val root = "${uri.scheme}://${uri.host}"

        onProgress(15,"عنوان، متا و ساختار محتوا")
        val title = doc.title().trim()
        if(title.isBlank()) add("عنوان صفحه وجود ندارد","تگ title یافت نشد.","یک عنوان واضح و مرتبط برای صفحه تعریف کنید.",Severity.CRITICAL)
        else if(title.length !in 25..65) add("طول عنوان قابل بهبود است","طول عنوان ${title.length} کاراکتر است.","عنوان را کوتاه، واضح و توصیفی نگه دارید.",Severity.WARNING) else pass("عنوان صفحه مناسب است")

        val desc = meta(doc,"description")
        if(desc.isBlank()) add("متا دیسکریپشن ناقص","توضیحات متا یافت نشد.","یک توضیح متای دقیق و مرتبط بنویسید.",Severity.CRITICAL)
        else if(desc.length !in 70..170) add("طول متا دیسکریپشن قابل بهبود است","طول فعلی ${desc.length} کاراکتر است.","توضیح متا را خلاصه و توصیفی نگه دارید.",Severity.WARNING) else pass("متا دیسکریپشن مناسب است")

        val h1 = doc.select("h1")
        if(h1.isEmpty()) add("H1 وجود ندارد","در صفحه تگ H1 پیدا نشد.","یک H1 یکتا برای موضوع اصلی صفحه قرار دهید.",Severity.CRITICAL)
        else if(h1.size>1) add("تگ H1 تکراری","${h1.size} تگ H1 در صفحه وجود دارد.","در هر صفحه یک H1 اصلی و مشخص استفاده کنید.",Severity.WARNING) else pass("ساختار H1 مناسب است")
        val textWords = doc.body()?.text()?.split(Regex("\\s+"))?.count{it.length>1} ?: 0
        if(textWords<180) add("محتوای صفحه کم است","حدود $textWords واژه متنی شناسایی شد.","در صورت نیاز کاربر، محتوای مفید و منحصربه‌فرد بیشتری اضافه کنید.",Severity.WARNING) else pass("حجم محتوای متنی مناسب است")
        if(textWords>500 && doc.select("h2").isEmpty()) add("ساختار زیرعنوان ضعیف است","صفحه طولانی است اما H2 ندارد.","محتوا را با H2/H3 منطقی بخش‌بندی کنید.",Severity.WARNING)

        onProgress(28,"تصاویر و تجربه موبایل")
        val imgs=doc.select("img"); val missingAlt=imgs.count{it.attr("alt").isBlank()}
        if(missingAlt>0) add("$missingAlt تصویر بدون ALT","برخی تصاویر متن جایگزین ندارند.","برای تصاویر محتوایی ALT توصیفی اضافه کنید.",if(missingAlt>=5) Severity.CRITICAL else Severity.WARNING) else pass("ALT تصاویر کامل است")
        val lazy=imgs.count{it.attr("loading").equals("lazy",true)}
        if(imgs.size>=8 && lazy < imgs.size/3) add("Lazy Loading تصاویر کم است","از ${imgs.size} تصویر فقط $lazy مورد lazy هستند.","برای تصاویر خارج از بخش اولیه صفحه loading=lazy را در نظر بگیرید.",Severity.WARNING) else pass("بارگذاری تصاویر مناسب است")
        val viewport=doc.selectFirst("meta[name=viewport]")
        if(viewport==null) add("Viewport موبایل یافت نشد","تگ viewport وجود ندارد.","meta viewport را برای نمایش صحیح موبایل اضافه کنید.",Severity.CRITICAL) else pass("نمایش موبایل تنظیم شده است")

        onProgress(40,"ایندکس، canonical و شبکه‌های اجتماعی")
        val canonical=doc.selectFirst("link[rel=canonical]")?.attr("href").orEmpty()
        if(canonical.isBlank()) add("Canonical وجود ندارد","لینک canonical یافت نشد.","canonical صفحه را مشخص کنید.",Severity.WARNING)
        else if(!canonical.startsWith("http")) add("Canonical نسبی است","Canonical به‌صورت URL کامل نیست.","از URL مطلق HTTPS استفاده کنید.",Severity.WARNING) else pass("Canonical تنظیم شده است")
        val robots=meta(doc,"robots").lowercase()
        if("noindex" in robots) add("صفحه Noindex است","موتور جستجو اجازه ایندکس این صفحه را ندارد.","اگر عمدی نیست، noindex را حذف کنید.",Severity.CRITICAL) else pass("صفحه قابل ایندکس است")
        if("nofollow" in robots) add("Meta robots روی nofollow است","دنبال‌کردن لینک‌های صفحه محدود شده است.","اگر عمدی نیست، nofollow را حذف کنید.",Severity.WARNING)
        if(doc.select("script[type=application/ld+json]").isEmpty()) add("Schema یافت نشد","JSON-LD در صفحه دیده نشد.","Structured Data مرتبط با نوع صفحه اضافه کنید.",Severity.WARNING) else pass("Schema وجود دارد")
        if(doc.selectFirst("html[lang]")==null) add("زبان صفحه مشخص نیست","ویژگی lang روی html وجود ندارد.","برای صفحات فارسی lang=fa را تنظیم کنید.",Severity.WARNING) else pass("زبان صفحه مشخص شده است")
        if(doc.selectFirst("meta[charset]")==null && doc.selectFirst("meta[http-equiv=content-type]")==null) add("Charset مشخص نیست","تعریف صریح charset یافت نشد.","UTF-8 را در head تعریف کنید.",Severity.WARNING) else pass("Charset مشخص است")
        val ogRequired=listOf("og:title","og:description","og:image").count{doc.selectFirst("meta[property=\"$it\"]")!=null}
        if(ogRequired<3) add("Open Graph ناقص است","$ogRequired مورد از 3 تگ اصلی Open Graph یافت شد.","og:title، og:description و og:image را کامل کنید.",Severity.WARNING) else pass("Open Graph کامل است")
        if(doc.selectFirst("meta[name=twitter:card]")==null) add("Twitter Card یافت نشد","کارت اشتراک شبکه اجتماعی تعریف نشده است.","twitter:card و متادیتای مرتبط را اضافه کنید.",Severity.WARNING)
        if(doc.select("link[rel~=icon]").isEmpty()) add("Favicon یافت نشد","آیکون سایت در head شناسایی نشد.","favicon استاندارد معرفی کنید.",Severity.WARNING) else pass("Favicon موجود است")

        onProgress(55,"لینک‌ها و منابع صفحه")
        val links=doc.select("a[href]")
        val emptyAnchors=links.count{it.text().isBlank() && it.select("img[alt]").isEmpty()}
        if(emptyAnchors>0) add("$emptyAnchors لینک بدون متن قابل فهم","برخی لینک‌ها anchor text واضح ندارند.","متن یا alt توصیفی برای لینک‌ها قرار دهید.",Severity.WARNING) else pass("متن لینک‌ها مناسب است")
        val scripts=doc.select("script[src]").size; if(scripts>25) add("تعداد فایل JavaScript زیاد است","$scripts فایل اسکریپت خارجی/داخلی لود می‌شود.","اسکریپت‌های غیرضروری را حذف یا ادغام کنید.",Severity.WARNING)
        val css=doc.select("link[rel=stylesheet]").size; if(css>12) add("تعداد فایل CSS زیاد است","$css stylesheet شناسایی شد.","CSSهای غیرضروری را کاهش دهید.",Severity.WARNING)
        if(finalUrl.length>115) add("URL طولانی است","URL صفحه ${finalUrl.length} کاراکتر دارد.","URL کوتاه و خوانا نگه دارید.",Severity.WARNING)

        onProgress(66,"robots.txt و نقشه سایت")
        val robotsOk=exists("$root/robots.txt")
        if(!robotsOk) add("robots.txt یافت نشد","فایل robots.txt در ریشه دامنه پیدا نشد.","یک robots.txt معتبر بسازید.",Severity.WARNING) else pass("robots.txt موجود است")
        val sitemapOk=exists("$root/sitemap.xml") || exists("$root/wp-sitemap.xml")
        if(!sitemapOk) add("نقشه سایت یافت نشد","sitemap.xml یا wp-sitemap.xml پیدا نشد.","نقشه سایت XML ایجاد و معرفی کنید.",Severity.CRITICAL) else pass("نقشه سایت موجود است")

        onProgress(75,"امنیت و سرعت پاسخ")
        if(uri.scheme!="https") add("HTTPS فعال نیست","سایت با HTTP تحلیل شد.","گواهی SSL نصب و ریدایرکت HTTPS را فعال کنید.",Severity.CRITICAL) else pass("HTTPS فعال است")
        if(fetch.ms>2500) add("پاسخ اولیه کند است","دریافت HTML حدود ${fetch.ms}ms طول کشید.","کش، سرور و حجم خروجی اولیه را بررسی کنید.",Severity.WARNING) else pass("زمان پاسخ اولیه مناسب است")
        if(fetch.bytes>1_500_000) add("HTML صفحه حجیم است","حجم HTML حدود ${fetch.bytes/1024} کیلوبایت است.","خروجی HTML را سبک‌تر کنید.",Severity.WARNING) else pass("حجم HTML مناسب است")
        val mixed=doc.select("img[src^=http://],script[src^=http://],link[href^=http://]").size
        if(uri.scheme=="https"&&mixed>0) add("محتوای ناامن Mixed Content","$mixed منبع HTTP داخل صفحه HTTPS دیده شد.","تمام منابع را با HTTPS بارگذاری کنید.",Severity.WARNING) else pass("Mixed Content مشاهده نشد")

        onProgress(82,"خزش چند صفحه و بررسی تکراری‌ها")
        val candidates = links.mapNotNull { runCatching { URI(it.absUrl("href")) }.getOrNull() }
            .filter { it.host==uri.host && (it.scheme=="http"||it.scheme=="https") }
            .map { it.toString().substringBefore('#') }.distinct().filter { it!=finalUrl }.take(5)
        val titles=mutableListOf(title); val descs=mutableListOf(desc); var crawled=1; var broken=0
        for((idx,u) in candidates.withIndex()){
            onProgress(82 + idx*2,"بررسی صفحه ${idx+2} از ${candidates.size+1}")
            val f=fetchPage(u); crawled++
            if(f.code>=400 || f.doc==null){broken++; continue}
            val d=f.doc; val t=d.title().trim(); val md=meta(d,"description")
            if(t.isNotBlank()) titles.add(t); if(md.isNotBlank()) descs.add(md)
            if(d.select("h1").isEmpty()) add("H1 مفقود در یک صفحه داخلی",u,"برای این صفحه H1 یکتا قرار دهید.",Severity.WARNING)
            if("noindex" in meta(d,"robots").lowercase()) add("صفحه داخلی Noindex",u,"اگر صفحه باید در گوگل باشد noindex را بازبینی کنید.",Severity.WARNING)
        }
        val dupTitles=titles.groupingBy{it}.eachCount().filter{it.key.isNotBlank()&&it.value>1}.size
        if(dupTitles>0) add("عنوان تکراری بین صفحات","$dupTitles عنوان تکراری در نمونه خزش دیده شد.","برای هر صفحه title یکتا بنویسید.",Severity.CRITICAL) else if(crawled>1) pass("عنوان صفحات نمونه یکتا است")
        val dupDesc=descs.groupingBy{it}.eachCount().filter{it.key.isNotBlank()&&it.value>1}.size
        if(dupDesc>0) add("متا دیسکریپشن تکراری","$dupDesc توضیح متای تکراری در نمونه خزش دیده شد.","برای صفحات مهم توضیح متای یکتا بنویسید.",Severity.WARNING)
        if(broken>0) add("$broken صفحه داخلی خطادار","در خزش نمونه، پاسخ ناموفق دریافت شد.","لینک‌های داخلی منتهی به خطا را اصلاح کنید.",Severity.CRITICAL)

        onProgress(96,"محاسبه امتیاز نهایی")
        val critical=issues.count{it.severity==Severity.CRITICAL}; val warnings=issues.count{it.severity==Severity.WARNING}; val passed=issues.count{it.severity==Severity.PASSED}
        val score=(100-critical*6-warnings*2).coerceIn(0,100)
        val metrics=SeoMetrics(crawled,links.size,imgs.size,missingAlt,fetch.ms,if(viewport!=null)100 else 45,if(desc.isNotBlank()&&title.isNotBlank())90 else 55,if(broken==0)90 else 55)
        onProgress(100,"تحلیل کامل شد")
        SeoReport(finalUrl,score,critical,warnings,passed,issues,metrics)
    }

    private data class Fetch(val url:String,val code:Int,val ms:Long,val bytes:Int,val doc:Document?)
    private fun fetchPage(url:String):Fetch=try{
        val t=System.currentTimeMillis(); client.newCall(Request.Builder().url(url).header("User-Agent","SeoMeter/1.0 (+Android SEO audit)").build()).execute().use { r ->
            val body=r.body?.string().orEmpty(); Fetch(r.request.url.toString(),r.code,System.currentTimeMillis()-t,body.toByteArray().size,if(body.isNotBlank()) Jsoup.parse(body,r.request.url.toString()) else null)
        }
    }catch(_:Exception){Fetch(url,599,0,0,null)}
    private fun meta(doc:Document,name:String)=doc.selectFirst("meta[name=$name]")?.attr("content")?.trim().orEmpty()
    private fun normalize(s:String):String { val t=s.trim(); return if(t.startsWith("http://")||t.startsWith("https://")) t else "https://$t" }
    private fun exists(url:String):Boolean=try{ client.newCall(Request.Builder().url(url).header("User-Agent","SeoMeter/1.0").build()).execute().use{it.isSuccessful} }catch(_:Exception){false}
}
