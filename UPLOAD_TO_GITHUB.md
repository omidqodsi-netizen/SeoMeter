# آپلود پروژه سئومتر در GitHub

1. فایل ZIP را Extract کنید.
2. **محتویات داخل پوشه SeoMeter-CLEAN** را در ریشه Repository آپلود کنید، نه خود پوشه مادر.
3. حتماً پوشه `.github` نیز آپلود شود. مسیر مهم:
   `.github/workflows/main.yml`
4. بعد از Commit روی branch `main`، اکشن `Build Debug APK` خودکار اجرا می‌شود.
5. بعد از سبز شدن Build، در پایین همان Run از بخش Artifacts فایل `SeoMeter-debug-apk` را دانلود کنید.
6. APK تست داخل ZIP آرتیفکت با نام `app-debug.apk` است.

## ساخت Release امضاشده
چهار Secret زیر را در Settings > Secrets and variables > Actions بسازید:
- `SEOMETER_KEYSTORE_BASE64`
- `SEOMETER_STORE_PASSWORD`
- `SEOMETER_KEY_ALIAS`
- `SEOMETER_KEY_PASSWORD`

سپس Workflow با نام `Build Signed Release APK` را دستی اجرا کنید.
