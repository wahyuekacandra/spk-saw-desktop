# 🎯 Sistem Pendukung Keputusan

![Java](https://img.shields.io/badge/Java-17+-blue.svg)
![JavaFX](https://img.shields.io/badge/JavaFX-17-orange.svg)
![License](https://img.shields.io/badge/License-MIT-green.svg)
![Platform](https://img.shields.io/badge/Platform-Windows-lightgrey.svg)
![Build](https://img.shields.io/badge/Build-Passing-brightgreen.svg)

Aplikasi desktop **Sistem Pendukung Keputusan** menggunakan metode **Simple Additive Weighting (SAW)** untuk membantu pengambilan keputusan multi-kriteria. Aplikasi ini menyediakan interface yang user-friendly dengan perhitungan otomatis.

## 📋 Deskripsi

Aplikasi Sistem Pendukung Keputusan adalah solusi berbasis Java yang mengotomatisasi perhitungan metode SAW untuk pengambilan keputusan. Dengan interface yang user-friendly dan fitur export yang lengkap, aplikasi ini cocok untuk berbagai kasus penggunaan seperti seleksi karyawan, pemilihan supplier, evaluasi kinerja, dan lainnya.

## ✨ Fitur Utama

- ✅ **Manajemen Kriteria** - Tambah, edit, hapus kriteria dengan bobot dan tipe (Benefit/Cost)
- ✅ **Manajemen Alternatif** - Kelola alternatif yang akan dinilai
- ✅ **Input Penilaian** - Interface tabel untuk input nilai setiap alternatif terhadap kriteria
- ✅ **Perhitungan Otomatis** - Normalisasi dan kalkulasi SAW otomatis
- ✅ **Ranking Hasil** - Menampilkan ranking alternatif dari yang terbaik
- ✅ **Export PDF & TXT** - Export hasil ke dokumen yang profesional
- ✅ **Data Persistence** - Penyimpanan data otomatis dalam format JSON
- ✅ **Modern UI** - Interface JavaFX yang clean dan responsive
- ✅ **Singleton Pattern** - Efficient data management dengan single instance
- ✅ **MVC Architecture** - Clean separation of concerns
- ✅ **Open Source** - Kode sumber terbuka dan dapat dikembangkan

## 🛠️ Teknologi

- **Java 17+** - Programming language
- **JavaFX 17** - GUI Framework
- **Gson 2.10.1** - JSON serialization
- **iText 2.1.7** - PDF generation

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

### Setup JavaFX
1. Download JavaFX SDK 17 dari [https://gluonhq.com/products/javafx/](https://gluonhq.com/products/javafx/)
2. Extract ke `C:\javafx-sdk-17` (atau edit path di `run.bat`)

### Build Project

Untuk compile aplikasi dari source code:

```bash
mkdir bin
javac -d bin -cp "lib\*" --module-path "C:\javafx-sdk-17\lib" --add-modules javafx.controls -encoding UTF-8 src\Main.java src\controller\*.java src\model\*.java src\service\*.java src\util\*.java
xcopy /E /I /Y resources bin\resources
```

### Run Application

```bash
run.bat
```

Aplikasi akan:
- Load data dari `data/data.json`
- Menampilkan GUI JavaFX
- Siap digunakan untuk input dan perhitungan SAW

---

## 📁 Project Structure

```
spk-saw-desktop/
├── src/                              # Source code (Java)
│   ├── Main.java                     # Application entry point
│   ├── controller/                   # MVC Controllers
│   │   ├── KriteriaController.java   # Kriteria management
│   │   ├── AlternatifController.java # Alternatif management
│   │   ├── PenilaianController.java  # Scoring input
│   │   └── HasilController.java      # Results & SAW calculation
│   ├── model/                        # Data models
│   │   ├── Kriteria.java             # Criteria entity
│   │   └── Alternatif.java           # Alternative entity
│   ├── service/                      # Business logic
│   │   ├── DataManager.java          # ⭐ Singleton data manager
│   │   ├── SAWCalculator.java        # SAW algorithm implementation
│   │   └── PDFExporter.java          # PDF report generator
│   └── util/                         # Utilities
│       └── StyleHelper.java          # UI styling helper
├── data/                             # JSON data storage
│   ├── data.json                     # Persisted data
│   └── README.md                     # Data folder documentation
├── output/                           # Generated reports folder
│   └── README.md                     # Output folder documentation
├── resources/                        # Application assets
│   └── icons/                        # Application icons
│       ├── app-icon.png              # Main icon (PNG)
│       └── app-icon.ico              # Windows icon (ICO)
├── lib/                              # External libraries
│   ├── gson-2.10.1.jar               # JSON serialization
│   ├── itext-2.1.7.jar               # PDF generation
│   └── README.md                     # Library documentation
├── run.bat                           # Development launcher (JavaFX required)
├── .gitignore                        # Git ignore rules
└── README.md                         # This file (Project documentation)
```

## 🔧 Configuration

### JavaFX SDK Path Setup
If your JavaFX SDK is in a different location, update these files:

**Edit file `run.bat`:**
```batch
set JAVAFX_PATH=C:\javafx-sdk-17\lib
```

Update path ini sesuai lokasi JavaFX SDK di komputer Anda.

### Data Storage
- Application data is saved to `data/data.json`
- Auto-creates file if not exists
- Uses **Singleton Pattern** for optimal performance

### Export Location
- Export reports will be saved to `output/` folder
- Export feature available in the application (PDF/TXT format)

---

## 💡 Use Cases

Perfect for decision-making scenarios such as:

| Use Case | Criteria Examples |
|----------|-------------------|
| **👔 Rekrutmen Karyawan** | Pendidikan, Pengalaman, Skill, Attitude |
| **🏭 Pemilihan Supplier** | Harga, Kualitas, Delivery Time, Support |
| **🎓 Seleksi Beasiswa** | IPK, Prestasi, Kondisi Ekonomi, Aktif Organisasi |
| **📊 Evaluasi Kinerja** | Produktivitas, Kualitas Kerja, Kehadiran, Teamwork |
| **📍 Pemilihan Lokasi** | Biaya Sewa, Aksesibilitas, Target Market, Infrastruktur |
| **🛒 Seleksi Produk** | Harga, Fitur, Dukungan, Reviews |

---

## 📖 How to Use

### Step-by-Step Guide:

1. **⚙️ Kelola Kriteria**
   - Tambah kriteria penilaian (misal: Harga, Kualitas, Pengalaman)
   - Tentukan bobot untuk setiap kriteria (total harus 100%)
   - Pilih tipe kriteria: **Benefit** (semakin besar semakin baik) atau **Cost** (semakin kecil semakin baik)

2. **📋 Kelola Alternatif**
   - Input alternatif yang akan dinilai (misal: Kandidat A, B, C)
   - Tambah deskripsi jika diperlukan

3. **✏️ Input Penilaian**
   - Masukkan nilai setiap alternatif untuk setiap kriteria
   - Nilai bisa berupa angka sesuai skala penilaian Anda

4. **🏆 Lihat Hasil SAW**
   - Sistem akan otomatis menghitung:
     - Normalisasi matriks
     - Nilai preferensi setiap alternatif
     - **Ranking otomatis** dari yang terbaik
   - Lihat detail perhitungan lengkap

5. **💾 Export Hasil**
   - Export hasil ranking ke file TXT/PDF
   - Simpan di folder `output/`

---

## 📚 SAW Algorithm

### Simple Additive Weighting (SAW) Method

**Formula Normalisasi:**

- **Benefit Criteria:** 
  $$r_{ij} = \frac{x_{ij}}{\max_i x_{ij}}$$

- **Cost Criteria:**
  $$r_{ij} = \frac{\min_i x_{ij}}{x_{ij}}$$

**Formula Preferensi:**
$$V_i = \sum_{j=1}^{n} w_j r_{ij}$$

Where:
- $V_i$ = Preference value for alternative $i$
- $w_j$ = Weight of criterion $j$
- $r_{ij}$ = Normalized rating
- $x_{ij}$ = Original rating

The alternative with the **highest $V_i$** is the **best choice**.

---

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

### How to Contribute:

1. **Fork** the project
2. Create your feature branch:
   ```bash
   git checkout -b feature/AmazingFeature
   ```
3. Commit your changes:
   ```bash
   git commit -m 'Add some AmazingFeature'
   ```
4. Push to the branch:
   ```bash
   git push origin feature/AmazingFeature
   ```
5. Open a **Pull Request**

---

## 📝 License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

---

## 👥 Authors

**Wahyu Eka Candra**
- 📧 Email: [wahyuekacandra@gmail.com](mailto:wahyuekacandra@gmail.com)
- 🎓 Semester 6 - Sistem Pendukung Keputusan
- 💼 GitHub: [@wahyuekacandra](https://github.com/wahyuekacandra)

---

## 🙏 Acknowledgments

- [JavaFX](https://openjfx.io/) - Modern GUI framework for Java
- [Google Gson](https://github.com/google/gson) - JSON serialization/deserialization
- [iText 2.1.7](https://github.com/ymasory/iText-4.2.0) - PDF generation library

---

## 📞 Support

If you have any questions or issues:

1. **🐛 Bug Reports**: Open an [issue](https://github.com/wahyuekacandra/spk-saw-desktop/issues)
2. **💡 Feature Requests**: Open an [issue](https://github.com/wahyuekacandra/spk-saw-desktop/issues) with `enhancement` label
3. **❓ Questions**: Start a [discussion](https://github.com/wahyuekacandra/spk-saw-desktop/discussions)
4. **📧 Email**: wahyuekacandra@gmail.com

---

## ⭐ Show Your Support

If this project helped you, please give it a ⭐ on [GitHub](https://github.com/wahyuekacandra/spk-saw-desktop)!

---

## 📊 Project Stats

![GitHub repo size](https://img.shields.io/github/repo-size/wahyuekacandra/spk-saw-desktop)
![GitHub last commit](https://img.shields.io/github/last-commit/wahyuekacandra/spk-saw-desktop)
![GitHub issues](https://img.shields.io/github/issues/wahyuekacandra/spk-saw-desktop)
![GitHub stars](https://img.shields.io/github/stars/wahyuekacandra/spk-saw-desktop?style=social)

---

## 🔮 Future Development

- [ ] Build automation scripts (Portable, Single EXE, Standalone, Installer)
- [ ] Multi-method support (TOPSIS, AHP, PROMETHEE)
- [ ] Database integration (MySQL/PostgreSQL)
- [ ] Multi-user support with authentication
- [ ] Cloud synchronization
- [ ] Advanced reporting with charts & graphs
- [ ] Template system for common use cases
- [ ] REST API for integration
- [ ] Multi-language support (English, Indonesian)
- [ ] Dark mode theme

---

<div align="center">

**Made with ❤️ for Decision Support System Course**

**© 2025 Sistem Pendukung Keputusan**

</div>
