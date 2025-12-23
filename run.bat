@echo off
REM Run Script untuk SPK-SAW Desktop App (Silent Mode)

REM Ganti path ini sesuai lokasi JavaFX SDK di komputer Anda
set JAVAFX_PATH=C:\javafx-sdk-17\lib

REM Jalankan aplikasi tanpa menampilkan console window
start "" javaw --module-path "%JAVAFX_PATH%" --add-modules javafx.controls --enable-native-access=javafx.graphics -cp "bin;lib\*" Main
