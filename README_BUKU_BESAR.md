# JM Motoshop Bekasi — APK Buku Besar v2.1.0

APK disederhanakan menjadi satu halaman Buku Besar Harian.

## Field APK
- Tanggal
- Saldo Awal
- Penjualan Cash
- Transfer / QRIS
- Bon / Piutang
- Total Omzet (otomatis)
- Pengeluaran 1..N: Keterangan + Nominal, tombol `+ Tambah`
- Total Pengeluaran (otomatis)
- Cash Sebelum Setor (otomatis)
- Setoran
- Cash Seharusnya (otomatis)
- Cash Fisik
- Selisih (otomatis)
- Catatan

Rumus:
- Total Omzet = Cash + Transfer/QRIS + Bon/Piutang
- Cash Sebelum Setor = Saldo Awal + Cash - Total Pengeluaran
- Cash Seharusnya = Cash Sebelum Setor - Setoran
- Selisih = Cash Fisik - Cash Seharusnya

## Server
Upload/replace minimal:
- `server/app/index.php`
- `server/assets/app.css`
- `server/action.php`

Tidak ada migration database baru. Data memakai tabel existing `sales`, `expenses`, dan `daily_closing`.
Rekap APK ditandai dengan prefix `APKBOOK-YYYYMMDD-*` dan category `BUKU BESAR APK`.

## GitHub APK
Repo harus berisi `.github/` dan `android/` dari paket ini.
Buat secret `JM_APP_TOKEN` sama dengan `app_access_token` pada `server/config.php`.
Actions > Build Android APK > Run workflow.
Artifact: `JM-Motoshop-Buku-Besar-APK`.

## Status bar / fullscreen
APK tidak lagi dimaksudkan untuk fullscreen. Android 15 system-bar inset ditangani agar header muncul tepat di bawah status bar/notifikasi HP.
