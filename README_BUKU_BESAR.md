# JM Motoshop Buku Besar v2.2.0

APK difokuskan untuk kontrol Buku Besar Harian.

## Perubahan V2.2.0
- Status bar/notifikasi Android tetap terlihat; APK tidak fullscreen/edge-to-edge.
- Header 42px dan footer 20px.
- Tanggal default otomatis = hari ini (Asia/Jakarta). Jika aplikasi tetap terbuka melewati tengah malam, halaman hari ini otomatis berganti.
- Kalender memakai UI HTML sendiri: tanggal terpilih background hijau stabilo dan font hitam.
- Saldo Awal dikunci Rp100.000 dan tidak dapat diedit dari APK maupun dimanipulasi lewat POST.
- Pengeluaran tetap 1..N dengan `Keterangan + Nominal + Tambah` dan total otomatis.
- Selisih kekurangan tampil merah dengan format `- Rp xx.xxx`; kelebihan tampil merah `+ Rp xx.xxx`; balance `Rp 0`.
- Dashboard Bulanan menampilkan tanggal 1 sampai akhir bulan, Total Omzet, Pengeluaran, Setoran, Total Selisih, dan ringkasan setiap hari.
- Tap satu hari di Dashboard Bulanan untuk membuka/mengedit rekap hari tersebut.

## Rumus APK
- Total Omzet = Cash + Transfer/QRIS + Bon/Piutang
- Cash Sebelum Setor = Rp100.000 + Cash - Total Pengeluaran
- Cash Seharusnya = Cash Sebelum Setor - Setoran
- Selisih = Cash Fisik - Cash Seharusnya

## Update server dari V2.1
Replace:
- `server/app/index.php`
- `server/assets/app.css`
- `server/action.php`
- `server/index.php` (agar Tutup Kas web juga memakai saldo awal Rp100.000)

Kemudian jalankan sekali:
- `server/migration_v2_2.sql`

Migration tidak mengubah data historis. Hanya mengubah default `daily_closing.opening_cash` menjadi Rp100.000 untuk konsistensi database.

## Database
Tidak perlu tabel baru. Struktur lama memang sudah mendukung:
- 3 rekap sales per hari di `sales`
- 1..N pengeluaran per hari di `expenses`
- 1 record tutup kas per tanggal di `daily_closing`

Dashboard bulanan membaca ketiga tabel tersebut secara langsung.

## Android / GitHub
Upload folder `android/` dan `.github/` dari paket repo-ready ke root repository GitHub, lalu jalankan Actions `Build Android APK`.
