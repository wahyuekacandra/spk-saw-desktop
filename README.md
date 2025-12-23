# SPK-SAW Desktop Application

![Java](https://img.shields.io/badge/Java-17+-blue.svg)
![JavaFX](https://img.shields.io/badge/JavaFX-17-orange.svg)
![License](https://img.shields.io/badge/License-MIT-green.svg)
![Platform](https://img.shields.io/badge/Platform-Windows-lightgrey.svg)

Aplikasi desktop **Sistem Pendukung Keputusan (SPK)** menggunakan metode **Simple Additive Weighting (SAW)** untuk membantu pengambilan keputusan multi-kriteria.

## 📋 Deskripsi

Aplikasi SPK-SAW Desktop adalah solusi berbasis Java yang mengotomatisasi perhitungan metode SAW untuk pengambilan keputusan. Dengan interface yang user-friendly dan fitur export yang lengkap, aplikasi ini cocok untuk berbagai kasus penggunaan seperti seleksi karyawan, pemilihan supplier, evaluasi kinerja, dan lainnya.

## ✨ Fitur Utama

- ✅ **Manajemen Kriteria** - Tambah, edit, hapus kriteria dengan bobot dan tipe (Benefit/Cost)
- ✅ **Manajemen Alternatif** - Kelola alternatif yang akan dinilai
- ✅ **Input Penilaian** - Interface tabel untuk input nilai setiap alternatif terhadap kriteria
- ✅ **Perhitungan Otomatis** - Normalisasi dan kalkulasi SAW otomatis
- ✅ **Ranking Hasil** - Menampilkan ranking alternatif dari yang terbaik
- ✅ **Export PDF** - Export hasil ke dokumen PDF yang profesional
- ✅ **Export Excel** - Export data ke format Excel untuk analisis lanjutan
- ✅ **Data Persistence** - Penyimpanan data otomatis dalam format JSON
- ✅ **Modern UI** - Interface JavaFX yang clean dan responsive

## 🛠️ Teknologi

- **Java 17+** - Programming language
- **JavaFX 17** - GUI Framework
- **Gson 2.10.1** - JSON serialization
- **iText 2.1.7** - PDF generation
- **Launch4j** - Windows executable wrapper
- **Inno Setup** - Windows installer creator

## 📋 Requirements

### Development
- JDK 17 or higher
- JavaFX SDK 17
- Windows 10/11

### Runtime (End Users)
- Java 11 or higher
- Windows 10/11

## 🚀 Getting Started

### Clone Repository
```bash
git clone https://github.com/wahyuekacandra/spk-saw-desktop.git
cd spk-saw-desktop
```

### Build Project
```bash
build.bat
```

### Run Application
```bash
run.bat
```
atau double-click `SPK-SAW.vbs` untuk mode silent (no terminal window)

## 📦 Distribution

Project ini menyediakan 3 opsi distribusi:

### 1. Single EXE Package (Recommended)
```bash
create-single-exe.bat
```
- Native Windows EXE
- Size: ~50MB
- Requires Java 11+ on target PC

### 2. Standalone (Self-Contained)
```bash
create-standalone.bat
```
- Bundled with Java Runtime
- Size: ~150-200MB
- No Java installation required
- Works on ANY Windows 10/11

### 3. Portable Package
```bash
create-portable.bat
```
- No installation needed
- Run from USB drive
- Size: ~50MB
- Requires Java 11+ on target PC

### Create Installer
```bash
create-installer-single-exe.bat
```
Creates professional Windows installer (requires Inno Setup)

## 📁 Project Structure

```
spk-saw-desktop/
├── src/                    # Source code
│   ├── Main.java          # Main application
│   ├── controller/        # Controllers (MVC)
│   ├── model/            # Data models
│   ├── service/          # Business logic
│   └── util/             # Utility classes
├── lib/                   # External libraries
│   ├── gson-2.10.1.jar
│   └── itext-2.1.7.jar
├── resources/            # Application resources
│   └── icons/           # App icons
├── data/                 # Application data
│   └── data.json        # JSON data storage
├── output/              # Export results location
├── build.bat            # Build script
├── run.bat              # Run script
└── README.md            # This file
```

## 💡 Use Cases

1. **Rekrutmen Karyawan** - Seleksi kandidat berdasarkan pendidikan, pengalaman, skill
2. **Pemilihan Supplier** - Evaluasi vendor berdasarkan harga, kualitas, delivery
3. **Seleksi Beasiswa** - Ranking mahasiswa berdasarkan IPK, prestasi, ekonomi
4. **Evaluasi Kinerja** - Penilaian karyawan berdasarkan produktivitas, kualitas kerja
5. **Pemilihan Lokasi** - Seleksi lokasi bisnis berdasarkan biaya, aksesibilitas
6. **Seleksi Produk** - Perbandingan produk berdasarkan harga, fitur, support

## 📖 How to Use

1. **Kelola Kriteria** - Tentukan kriteria penilaian dengan bobot (total 100%)
2. **Kelola Alternatif** - Input alternatif yang akan dinilai
3. **Input Penilaian** - Masukkan nilai setiap alternatif untuk setiap kriteria
4. **Lihat Hasil** - Sistem akan menampilkan ranking otomatis
5. **Export** - Export hasil ke PDF atau Excel

## 🔧 Configuration

### JavaFX Path
Edit `build.bat` dan `run.bat` jika JavaFX SDK berada di lokasi berbeda:
```batch
set JAVAFX_PATH=C:\javafx-sdk-17\lib
```

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Authors

**SPK Team**
- Semester 6 - Sistem Pendukung Keputusan
- Universitas [Your University]

## 🙏 Acknowledgments

- JavaFX Team for the excellent GUI framework
- Google Gson for JSON processing
- iText for PDF generation
- Launch4j for Windows executable creation

## 📞 Support

For support, email your.email@example.com or open an issue on GitHub.

## 🔮 Future Development

- [ ] Multi-method support (TOPSIS, AHP, PROMETHEE)
- [ ] Database integration (MySQL/PostgreSQL)
- [ ] Multi-user support
- [ ] Cloud synchronization
- [ ] Mobile version (Android/iOS)
- [ ] Advanced reporting with charts
- [ ] Template system
- [ ] REST API
- [ ] Multi-language support

---

**Made with ❤️ for Decision Support Systems**
