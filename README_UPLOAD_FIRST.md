# JM Motoshop APK - Repository Ready v2.0.2

PENTING: GitHub repository harus berisi FOLDER `android`, bukan hanya file workflow.

Struktur minimal:

.github/workflows/android-build.yml
android/settings.gradle
android/build.gradle
android/gradle.properties
android/app/build.gradle
android/app/src/main/AndroidManifest.xml
android/app/src/main/java/com/jofano/jmmotoshop/MainActivity.java
android/app/src/main/res/...

Setelah semua file masuk ke branch main, buka Actions > Build Android APK > Run workflow.

JM_APP_TOKEN tidak lagi membuat BUILD gagal jika belum ada. Build tetap menghasilkan APK.
Agar APK bisa membuka backend tanpa login, buat Repository Secret `JM_APP_TOKEN`
dan samakan nilainya dengan `app_access_token` pada config server PHP.
