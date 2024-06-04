# Babagi Food-Sharing
Babagi merupakan aplikasi food sharing untuk menghubungkan restoran yang memiliki sisa makanan berlebih dengan pengguna yang ingin membeli atau mendapatkan makanan secara gratis. Menggunakan Babagi pengguna dapat menemukan informasi berbagai menu makanan sisa yang masih layak makan di restoran-restoran terdekat dari rumahnya secara online

# Fitur Utama
- **Informasi Menu**: Babagi menyediakan informasi menu makanan yang akan didonasikan oleh pihak restoran secar gratis atau dengan harga yang lebih murah
- **Detail Menu**: Informasi terkait menu yang disertai waktu pembagian, lokasi restoran, dan bahan pembuatan makanan

# Penggunaan Aplikasi
1. **Register**: Ketika pertama kali membuka aplikais Babagi, akan muncul splash screen. Apabila baru pertama kali menggunakan aplikasi Babagi makan dapat menekan tombol "Daftar". Lalu Masukkan semua informasi yang tertera dan tombol Kirim.
2. **Login**: Apabila telah memiliki akun, makan pada splash screen langsung klik tombol "masuk" untuk diarahkan ke halaman login Babagi.
3. **Home** : Halaman beranda akan menampilkan seluruh daftar menu makanan yang akan didonasikan dalam bentu card.
4. **Menu Detail** : Menampilkan detail makanan yang didonasikan ke pengguna berupa nama makanan, deskripsi, gambar, dan ingredients
5. **Cari Menu** : Mencari makanan berdasarkan nama makanan.
![image](https://github.com/zoelramadhan/Babagi-Food-Sharing/assets/113816321/95821e05-d2c6-4cde-90ca-55815c45c3c7)
7. **Profile** : Halaman profil agar pengguna dapat memperbarui nama penggunanya di aplikasi, emailnya, hingga password akunnya.
![image](https://github.com/zoelramadhan/Babagi-Food-Sharing/assets/113816321/7ecc3d65-004c-4d02-b1eb-d3659a2c63ca) ![image](https://github.com/zoelramadhan/Babagi-Food-Sharing/assets/113816321/a35e0da9-fe5d-4fc1-af38-691ab64c8316)
8. Loading & Error State
![image](https://github.com/zoelramadhan/Babagi-Food-Sharing/assets/113816321/553c6d10-4e17-45e3-85a1-f89e0cb131d2)![image](https://github.com/zoelramadhan/Babagi-Food-Sharing/assets/113816321/9d74c11b-d0bd-428e-a210-3370790f58d5)





# Pedoman Teknis
**1. Register and Login:**
- Data pengguna yang melakukan registrasi akan disimpan di lokal database SQLite
- Pada saat pengguna selesai registrasi maupun pada saat login, maka akan muncul dua informasi yang disimpan pada SharedPreference, Yaitu:
        - "is_Logged_in" dengan tipe data boolean. Berfungsi agar ketika pengguna sudah Login pada Babagi lalu menutup aplikasi, maka saat dibuka kembali, pengguna langsung diarahkan ke halaman utama Babagi (tidak memerlukan login kembali).
        -  "user_id" dengan tipe data int. Berfungsi untuk menyimpan memori id user yang sedang login pada Babagi.
  
**2. Home:****
  - Implementasi menggunakan RecyclerView untuk menampilkan daftar makanan, CardView untuk tampilan card, dan Picasso untuk memuat gambar makanannya dari API.

**3. Pencarian Menu Makanan**
- Implementasi pengelolaan hasil pencarian melalui metode .searchMenu(String query) yang ada pada SearchMenuAdapter. Menggunakan fungsi bawaan Java untuk mengelolal persamaan text yang diinput dengan hasil filter data.

**4. Profile**
- Menggunakan SQLite yang sudah dikonfigurasikan melalui class DbConfig. Implementasi memperbarui data pengguna melalui metode DbConfig.udpateProfil(int userId, String name, String email, String password).
- Ketika tombol "Logout" diklik, maka infomrasi pada SharedPreference akan diperbarui.
  - "user_id" -> null
  - "is_logged_in" -> false

# Penggunaan Teknologi 
- Android Studio Java: Pengembangan aplikasi Android
- Retrofit: Koneksi jaringan dan pengambilan data dari API.
- Picasso: Pemuatan dan penanganan gambar
- SQLite: Penyimpanan data lokal
- Shared Preferences: Penyimpanan data kecil yang persisten
- CardView: Menampilkan data dalam bentuk kartu dengan tata letak yang rapih.

# Authors
muhammadzoelramadhan

# Referensi
Vegan Recipes API
Figma Community
Segari
StackoverFlow
Google
GIF Load
