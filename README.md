# سئومتر (SeoMeter)

اپلیکیشن اندرویدی تحلیل Technical / On-page SEO بدون API پولی.

**طراح:** امید قدسی زاده  
**Package:** `ir.seometer.app`  
**نسخه:** `1.0.0`  
**Min Android:** 8.0 (API 26)

## امکانات نسخه 1.0

- رابط کاربری کامل فارسی و RTL با ترکیب Navy / Blue / Teal / Green
- تحلیل URL بدون OpenAI، Gemini یا API پولی
- تحلیل عنوان، Meta Description، H1/H2، حجم محتوای متنی
- ALT تصاویر و Lazy Loading
- viewport موبایل
- canonical و meta robots / noindex / nofollow
- Schema JSON-LD
- lang و charset
- Open Graph و Twitter Card
- favicon
- لینک‌های بدون Anchor مناسب
- تعداد CSS و JavaScript
- طول URL
- robots.txt و sitemap.xml / wp-sitemap.xml
- HTTPS و Mixed Content
- زمان پاسخ اولیه و حجم HTML
- خزش نمونه چند صفحه داخلی
- تشخیص عنوان و Meta Description تکراری در نمونه خزش
- شناسایی صفحات داخلی خطادار در نمونه خزش
- امتیاز سئو 0 تا 100
- دسته‌بندی مشکلات بحرانی / هشدار / تأیید شده
- راه‌حل فارسی برای هر خطا
- تاریخچه تحلیل‌ها فقط روی گوشی
- تولید گزارش PDF و Share Sheet اندروید
- صفحه حریم خصوصی داخل خود برنامه
- بدون تبلیغ، Analytics، حساب کاربری یا دسترسی حساس

> امتیاز سئومتر یک شاخص داخلی برای اولویت‌بندی مشکلات Technical/On-page است و تضمین رتبه گوگل نیست.

## ساختار پروژه

- Kotlin
- Jetpack Compose + Material 3
- OkHttp برای درخواست‌های HTTP
- Jsoup برای تحلیل HTML
- SharedPreferences برای تاریخچه محلی
- Android PdfDocument برای گزارش PDF

## خروجی سریع APK با GitHub Actions

1. یک Repository جدید در GitHub بسازید؛ مثلاً `SeoMeter`.
2. تمام فایل‌های این پوشه را در ریشه Repository آپلود کنید.
3. وارد تب **Actions** شوید.
4. Workflow با نام **Build Debug APK** را اجرا کنید.
5. پس از سبز شدن Build، پایین صفحه از بخش **Artifacts** فایل `SeoMeter-debug-apk` را دانلود کنید.
6. داخل آن `app-debug.apk` قرار دارد. این نسخه برای تست است.

### ساخت APK ریلیز امضاشده برای بازار

یک Keystore را فقط یک بار بسازید و برای تمام آپدیت‌های بعدی نگه دارید:

```bash
keytool -genkeypair -v -keystore seometer-release.jks -keyalg RSA -keysize 2048 -validity 10000 -alias seometer
```

روی Windows PowerShell فایل Keystore را Base64 کنید:

```powershell
[Convert]::ToBase64String([IO.File]::ReadAllBytes("seometer-release.jks")) | Set-Content keystore-base64.txt
```

سپس در GitHub به مسیر زیر بروید:

`Repository > Settings > Secrets and variables > Actions > New repository secret`

این چهار Secret را بسازید:

- `SEOMETER_KEYSTORE_BASE64` = محتوای کامل `keystore-base64.txt`
- `SEOMETER_STORE_PASSWORD` = رمز keystore
- `SEOMETER_KEY_ALIAS` = `seometer`
- `SEOMETER_KEY_PASSWORD` = رمز alias

سپس:

`Actions > Build Signed Release APK > Run workflow`

بعد از اتمام Build، Artifact با نام `SeoMeter-release-apk` را دانلود کنید. فایل `app-release.apk` همان فایل مناسب انتشار است.

## نکات مهم برای کافه‌بازار

این نسخه عمداً محافظه‌کارانه طراحی شده است:

- هیچ لینک تبلیغاتی یا صفحه پرداخت خارجی ندارد.
- هیچ WebView برای هدایت کاربر به سرویس بیرونی ندارد.
- فقط دسترسی‌های `INTERNET` و `ACCESS_NETWORK_STATE` دارد.
- مکان، مخاطبین، SMS، دوربین، فایل‌های کاربر و Advertising ID درخواست نمی‌شوند.
- خرید درون‌برنامه‌ای در نسخه 1.0 وجود ندارد؛ اگر برنامه را پولی می‌فروشید قیمت را از پنل بازار تعیین کنید.
- اگر بعداً قابلیت پولی داخل برنامه اضافه شد، پرداخت باید بر اساس سیاست جاری بازار پیاده‌سازی شود.
- اطلاعات تحلیل به سرور سئومتر ارسال نمی‌شود؛ درخواست‌ها مستقیماً برای سایت واردشده توسط کاربر ارسال می‌شوند.

قبل از انتشار نهایی، قوانین جاری بازار را دوباره در پنل توسعه‌دهندگان بررسی کنید؛ قوانین مارکت ممکن است تغییر کنند.

## افزایش نسخه برای آپدیت بعدی

در `app/build.gradle.kts` این دو مقدار را تغییر دهید:

```kotlin
versionCode = 2
versionName = "1.1.0"
```

`versionCode` در هر انتشار باید بیشتر از نسخه قبلی باشد.

## نکته امنیتی Keystore

Keystore را داخل GitHub Commit نکنید. اگر Keystore انتشار گم شود، انتشار آپدیت با همان امضا مشکل‌ساز خواهد شد. یک نسخه پشتیبان امن نگه دارید.
