# Babagi Food-Sharing
Babagi merupakan aplikasi food sharing untuk menghubungkan restoran yang memiliki sisa makanan berlebih dengan pengguna yang ingin membeli atau mendapatkan makanan secara gratis. Menggunakan Babagi pengguna dapat menemukan informasi berbagai menu makanan sisa yang masih layak makan di restoran-restoran terdekat dari rumahnya secara online

# Fitur Utama
- **Informasi Menu**: Babagi menyediakan informasi menu makanan yang akan didonasikan oleh pihak restoran secar gratis atau dengan harga yang lebih murah
- **Detail Menu**: Informasi terkait menu yang disertai waktu pembagian, lokasi restoran, dan bahan pembuatan makanan

# Penggunaan Aplikasi
1. **Register**: Ketika pertama kali membuka aplikais Babagi, akan muncul splash screen. Apabila baru pertama kali menggunakan aplikasi Babagi makan dapat menekan tombol "Daftar". Lalu Masukkan semua informasi yang tertera dan tombol Kirim.
![image 11](https://github.com/zoelramadhan/Babagi-Food-Sharing/assets/113816321/c793b249-1b3f-4de7-a034-cea78518eb2f)
![image 11](https://github.com/zoelramadhan/Babagi-Food-Sharing/assets/113816321/3622f8b0-0b78-4084-9311-22c28f0a5fb7)


3. **Login**: Apabila telah memiliki akun, makan pada splash screen langsung klik tombol "masuk" untuk diarahkan ke halaman login Babagi.
![image 11](https://github.com/zoelramadhan/Babagi-Food-Sharing/assets/113816321/535d7511-6693-46b2-97e5-f54778d92a82)


5. **Home** : Halaman beranda akan menampilkan seluruh daftar menu makanan yang akan didonasikan dalam bentu card.
![image 7](https://github.com/zoelramadhan/Babagi-Food-Sharing/assets/113816321/4dc8f303-7b4d-4e38-8233-557b0f4ae9be)

6. **Menu Detail** : Menampilkan detail makanan yang didonasikan ke pengguna berupa nama makanan, deskripsi, gambar, dan ingredients
![image 9](https://github.com/zoelramadhan/Babagi-Food-Sharing/assets/113816321/443fd3e0-8e6f-4b01-ad13-b9f31c8b3f1b)


7. **Cari Menu** : Mencari makanan berdasarkan nama makanan   .                                                                                                                           .
![image 3](https://github.com/zoelramadhan/Babagi-Food-Sharing/assets/113816321/9027c9ba-991a-4b34-94c1-1e0d75089dc3) ![image 8](https://github.com/zoelramadhan/Babagi-Food-Sharing/assets/113816321/84b51c1d-65c9-44ec-95a9-1e38b77908cd)

9. **Profile** : Halaman profil agar pengguna dapat memperbarui nama penggunanya di aplikasi, emailnya, hingga password akunnya.
![image 2](https://github.com/zoelramadhan/Babagi-Food-Sharing/assets/113816321/71f86b70-ca5f-4a33-bfe5-012d3da4d597) ![image 2 (1)](https://github.com/zoelramadhan/Babagi-Food-Sharing/assets/113816321/4fb76441-024c-42c5-a851-2dae47518e94) ![image 9](https://github.com/zoelramadhan/Babagi-Food-Sharing/assets/113816321/e3e9b9fc-6216-46ea-8ca8-fb1bf1285c4b)


10. Loading & Error State
![image 4](https://github.com/zoelramadhan/Babagi-Food-Sharing/assets/113816321/2d4007e5-38cc-4578-ad6e-5d18eb5b6ef7) ![image 5](https://github.com/zoelramadhan/Babagi-Food-Sharing/assets/113816321/1ab83b04-8585-4b74-a8da-d7e630cc2fe4)


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
