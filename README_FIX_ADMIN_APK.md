# JM Motoshop Admin APK v1.0.1 - Runtime Fix

Repo Jofano25/JM sudah dicek. Peletakan source dan workflow sudah benar dan build v1.0.0 berhasil.

Patch ini fokus pada APK yang tidak mau terbuka / langsung tertutup:

- MainActivity dibuat lebih sederhana dan aman.
- Inisialisasi WebView dibungkus startup fallback agar app tidak langsung close bila WebView gagal.
- Jika WebView Android bermasalah, app menampilkan pesan diagnostic.
- Jika server tidak dapat dibuka, app menampilkan halaman error + tombol Coba Lagi.
- Ditambahkan ACCESS_NETWORK_STATE.
- Hardware acceleration dipastikan aktif.
- User-Agent diberi penanda JM-Motoshop-Admin/1.0.1.
- Mixed-content compatibility diaktifkan untuk resource lama yang mungkin masih HTTP.
- Version code menjadi 2 / versionName 1.0.1.

## Replace file di GitHub

Replace file berikut:

1. android-admin/app/build.gradle
2. android-admin/app/src/main/AndroidManifest.xml
3. android-admin/app/src/main/java/com/jofano/jmmotoshop/admin/MainActivity.java
4. android-admin/app/src/main/res/values/styles.xml
5. android-admin/app/src/main/res/values-v35/styles.xml

Workflow admin-build.yml tidak perlu diubah karena build sebelumnya sudah sukses.

Setelah upload, jalankan ulang:
Actions > Build JM Motoshop Admin APK > Run workflow

Download artifact, EXTRACT ZIP artifact, lalu install file JM-Motoshop-Admin.apk di dalamnya.

Jika v1.0.1 masih menampilkan pesan bahwa WebView tidak dapat dimulai, update/enable Android System WebView atau Google Chrome di HP.
