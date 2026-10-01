# JM Motoshop Admin APK v1.0.0

APK Admin khusus untuk membuka web Admin JM Motoshop di dalam aplikasi Android.

## Tujuan
- Tidak perlu membuka Chrome/browser.
- Login tetap menggunakan user Admin web yang sudah ada.
- Password Admin tidak ditanam di source APK.
- Cookie/session WebView dipertahankan selama session server masih berlaku.
- Bisa dipasang bersamaan dengan APK operasional karena package berbeda.

## App
- Nama: JM Motoshop Admin
- Package: `com.jofano.jmmotoshop.admin`
- URL: `https://jofano.com/jm-bengkel/`
- Android minSdk: 24
- targetSdk / compileSdk: 35
- Java: 17

## Fitur native
- Status bar/notifikasi HP tetap terlihat.
- Tidak fullscreen.
- Progress bar tipis saat halaman sedang loading.
- Tombol Back Android mengikuti history halaman WebView.
- Cookie/session login disimpan oleh WebView.
- Download dari web menggunakan Android Download Manager dan membawa cookie login.
- Dark mode WebView dimatikan agar warna dashboard web konsisten.

## Cara upload ke repository Jofano25/JM
Extract ZIP ini, lalu upload ke root repository:

```text
android-admin/
.github/workflows/admin-build.yml
```

Jangan menimpa folder `android/` lama karena itu APK operasional.

Setelah upload:
1. GitHub -> Actions.
2. Pilih **Build JM Motoshop Admin APK**.
3. Klik **Run workflow**.
4. Setelah sukses download artifact `JM-Motoshop-Admin-APK`.
5. File install: `JM-Motoshop-Admin.apk`.

## Catatan
APK ini hanya wrapper aman untuk web Admin. Jadi saat Dashboard, Absensi, Buku Besar, atau menu web diperbarui di server, APK Admin otomatis menggunakan tampilan terbaru tanpa rebuild APK.
