package controller;

import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.*;
import service.*;
import util.StyleHelper;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class HasilController {
    private DataManager dataManager;
    private SAWCalculator calculator;
    private PDFExporter pdfExporter;
    private Map<String, Double> lastRanking;
    private Map<String, Map<String, Double>> lastMatrixNormalisasi;
    
    public HasilController() {
        this.dataManager = DataManager.getInstance();
        this.calculator = new SAWCalculator(dataManager);
        this.pdfExporter = new PDFExporter(dataManager);
    }
    
    public VBox getView() {
        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(30));
        vbox.setStyle(StyleHelper.getPageStyle());
        
        // Header
        VBox headerBox = new VBox(8);
        Label title = new Label("Hasil Perhitungan & Ranking");
        title.setStyle(StyleHelper.getTitleStyle());
        Label subtitle = new Label("Hasil perhitungan metode SAW dengan ranking alternatif terbaik");
        subtitle.setStyle("-fx-font-size: 13px; -fx-text-fill: " + StyleHelper.TEXT_SECONDARY + ";");
        headerBox.getChildren().addAll(title, subtitle);
        
        // Button Card
        HBox buttonCard = new HBox(15);
        buttonCard.setPadding(new Insets(20));
        buttonCard.setAlignment(Pos.CENTER);
        buttonCard.setStyle(StyleHelper.getCardStyle());
        
        Button btnHitung = new Button("📊 Hitung Ranking SAW");
        btnHitung.setStyle(StyleHelper.getPrimaryButtonStyle() + " -fx-font-size: 15px; -fx-padding: 15 30;");
        btnHitung.setOnMouseEntered(e -> btnHitung.setStyle(StyleHelper.getPrimaryButtonHoverStyle() + " -fx-font-size: 15px; -fx-padding: 15 30;"));
        btnHitung.setOnMouseExited(e -> btnHitung.setStyle(StyleHelper.getPrimaryButtonStyle() + " -fx-font-size: 15px; -fx-padding: 15 30;"));
        
        Button btnExport = new Button("📄 Export ke PDF");
        btnExport.setStyle(StyleHelper.getSuccessButtonStyle() + " -fx-font-size: 15px; -fx-padding: 15 30;");
        btnExport.setOnMouseEntered(e -> btnExport.setStyle(StyleHelper.getSuccessButtonHoverStyle() + " -fx-font-size: 15px; -fx-padding: 15 30;"));
        btnExport.setOnMouseExited(e -> btnExport.setStyle(StyleHelper.getSuccessButtonStyle() + " -fx-font-size: 15px; -fx-padding: 15 30;"));
        btnExport.setDisable(true);
        
        buttonCard.getChildren().addAll(btnHitung, btnExport);
        
        TableView<RankingItem> tableRanking = new TableView<>();
        tableRanking.setPlaceholder(new Label("Klik tombol 'Hitung Ranking SAW' untuk melihat hasil"));
        
        TableView<MatrixItem> tableNormalisasi = new TableView<>();
        tableNormalisasi.setPlaceholder(new Label("Matrix normalisasi akan ditampilkan setelah perhitungan"));
        
        btnHitung.setOnAction(e -> {
            List<Kriteria> kriteriaList = dataManager.getAllKriteria();
            List<Alternatif> alternatifList = dataManager.getAllAlternatif();
            
            if (kriteriaList.isEmpty() || alternatifList.isEmpty()) {
                showAlert("Error", "Data kriteria atau alternatif masih kosong!", Alert.AlertType.ERROR);
                return;
            }
            
            // Validasi apakah semua alternatif sudah dinilai
            boolean allRated = true;
            for (Alternatif alt : alternatifList) {
                for (Kriteria krit : kriteriaList) {
                    if (alt.getNilai(krit.getKode()) == 0) {
                        allRated = false;
                        break;
                    }
                }
                if (!allRated) break;
            }
            
            if (!allRated) {
                Alert warning = new Alert(Alert.AlertType.WARNING);
                warning.setTitle("Peringatan");
                warning.setHeaderText(null);
                warning.setContentText("Beberapa alternatif belum dinilai. Hasil mungkin tidak akurat.\nLanjutkan perhitungan?");
                warning.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        hitungDanTampilkan(tableRanking, tableNormalisasi);
                        btnExport.setDisable(false);
                    }
                });
            } else {
                hitungDanTampilkan(tableRanking, tableNormalisasi);
                btnExport.setDisable(false);
            }
        });
        
        btnExport.setOnAction(e -> {
            exportToPDF();
        });
        
        // Setup tables
        setupTableRanking(tableRanking);
        setupTableNormalisasi(tableNormalisasi);
        
        // Ranking Card
        VBox rankingCard = new VBox(15);
        rankingCard.setPadding(new Insets(25));
        rankingCard.setStyle(StyleHelper.getCardStyle());
        
        Label lblRanking = new Label("🏆 Ranking Alternatif");
        lblRanking.setStyle(StyleHelper.getSubtitleStyle());
        rankingCard.getChildren().addAll(lblRanking, tableRanking);
        
        // Matrix Card
        VBox matrixCard = new VBox(15);
        matrixCard.setPadding(new Insets(25));
        matrixCard.setStyle(StyleHelper.getCardStyle());
        
        Label lblNormalisasi = new Label("📋 Matrix Normalisasi");
        lblNormalisasi.setStyle(StyleHelper.getSubtitleStyle());
        matrixCard.getChildren().addAll(lblNormalisasi, tableNormalisasi);
        
        vbox.getChildren().addAll(headerBox, buttonCard, rankingCard, matrixCard);
        
        return vbox;
    }
    
    private void setupTableRanking(TableView<RankingItem> table) {
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        TableColumn<RankingItem, Integer> colRank = new TableColumn<>("Peringkat");
        colRank.setCellValueFactory(new PropertyValueFactory<>("rank"));
        colRank.setPrefWidth(100);
        colRank.setStyle("-fx-alignment: CENTER;");
        
        TableColumn<RankingItem, String> colKode = new TableColumn<>("Kode");
        colKode.setCellValueFactory(new PropertyValueFactory<>("kode"));
        colKode.setPrefWidth(100);
        
        TableColumn<RankingItem, String> colNama = new TableColumn<>("Nama Alternatif");
        colNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colNama.setPrefWidth(250);
        
        TableColumn<RankingItem, Double> colNilai = new TableColumn<>("Nilai Preferensi");
        colNilai.setCellValueFactory(new PropertyValueFactory<>("nilai"));
        colNilai.setPrefWidth(150);
        colNilai.setCellFactory(column -> new TableCell<RankingItem, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("%.4f", item));
                }
            }
        });
        
        table.getColumns().addAll(colRank, colKode, colNama, colNilai);
    }
    
    private void setupTableNormalisasi(TableView<MatrixItem> table) {
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        TableColumn<MatrixItem, String> colAlt = new TableColumn<>("Alternatif");
        colAlt.setCellValueFactory(new PropertyValueFactory<>("alternatif"));
        colAlt.setPrefWidth(150);
        
        // Kolom kriteria akan ditambahkan dinamis saat perhitungan
        table.getColumns().add(colAlt);
    }
    
    private void hitungDanTampilkan(TableView<RankingItem> tableRanking, TableView<MatrixItem> tableNormalisasi) {
        lastRanking = calculator.calculate();
        lastMatrixNormalisasi = calculator.getMatrixNormalisasi();
        Map<String, Double> ranking = lastRanking;
        Map<String, Map<String, Double>> matrixNormalisasi = lastMatrixNormalisasi;
        
        // Tampilkan ranking
        List<RankingItem> items = ranking.entrySet().stream()
            .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
            .map(entry -> {
                Alternatif alt = dataManager.getAlternatifByKode(entry.getKey());
                return new RankingItem(entry.getKey(), 
                    alt != null ? alt.getNama() : "", 
                    entry.getValue());
            })
            .collect(Collectors.toList());
        
        // Add ranking number
        for (int i = 0; i < items.size(); i++) {
            items.get(i).setRank(i + 1);
        }
        
        tableRanking.setItems(FXCollections.observableArrayList(items));
        
        // Tampilkan matrix normalisasi
        tampilkanMatrixNormalisasi(tableNormalisasi, matrixNormalisasi);
        
        showAlert("Sukses", "Perhitungan SAW berhasil dilakukan!", Alert.AlertType.INFORMATION);
    }
    
    private void tampilkanMatrixNormalisasi(TableView<MatrixItem> table, Map<String, Map<String, Double>> matrix) {
        // Clear existing columns except first
        while (table.getColumns().size() > 1) {
            table.getColumns().remove(1);
        }
        
        List<Kriteria> kriteriaList = dataManager.getAllKriteria();
        
        // Add columns for each criteria
        for (Kriteria krit : kriteriaList) {
            TableColumn<MatrixItem, Double> col = new TableColumn<>(krit.getKode());
            col.setPrefWidth(100);
            col.setCellValueFactory(cellData -> {
                Double value = cellData.getValue().getNilaiKriteria(krit.getKode());
                return new javafx.beans.property.SimpleObjectProperty<>(value);
            });
            col.setCellFactory(column -> new TableCell<MatrixItem, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(String.format("%.4f", item));
                    }
                }
            });
            table.getColumns().add(col);
        }
        
        // Add data
        List<MatrixItem> matrixItems = new ArrayList<>();
        for (Alternatif alt : dataManager.getAllAlternatif()) {
            MatrixItem item = new MatrixItem(alt.getKode() + " - " + alt.getNama());
            if (matrix.containsKey(alt.getKode())) {
                item.setNilaiMap(matrix.get(alt.getKode()));
            }
            matrixItems.add(item);
        }
        
        table.setItems(FXCollections.observableArrayList(matrixItems));
    }
    
    private void exportToPDF() {
        if (lastRanking == null || lastMatrixNormalisasi == null) {
            showAlert("Error", "Belum ada data untuk diexport. Silakan hitung ranking terlebih dahulu.", Alert.AlertType.ERROR);
            return;
        }
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Hasil ke PDF");
        fileChooser.setInitialFileName(PDFExporter.generateFileName().replace(".txt", ".pdf"));
        
        // Set extension filter untuk PDF saja
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
            "PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);
        
        // Set initial directory
        File initialDir = new File(System.getProperty("user.home") + "/Documents");
        if (!initialDir.exists()) {
            initialDir = new File(System.getProperty("user.home"));
        }
        fileChooser.setInitialDirectory(initialDir);
        
        // Show save dialog
        Stage stage = new Stage();
        File file = fileChooser.showSaveDialog(stage);
        
        if (file != null) {
            try {
                String filePath = file.getAbsolutePath();
                // Ensure .pdf extension
                if (!filePath.toLowerCase().endsWith(".pdf")) {
                    filePath += ".pdf";
                    file = new File(filePath);
                }
                
                pdfExporter.exportToPDF(lastRanking, lastMatrixNormalisasi, file);
                
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Sukses");
                success.setHeaderText("Export Berhasil!");
                success.setContentText("Hasil perhitungan berhasil disimpan ke PDF:\n" + file.getAbsolutePath());
                success.showAndWait();
            } catch (Exception ex) {
                showAlert("Error", "Gagal menyimpan file PDF: " + ex.getMessage(), Alert.AlertType.ERROR);
                ex.printStackTrace();
            }
        }
    }
    
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    // Inner classes
    public static class RankingItem {
        private int rank;
        private String kode;
        private String nama;
        private double nilai;
        
        public RankingItem(String kode, String nama, double nilai) {
            this.kode = kode;
            this.nama = nama;
            this.nilai = nilai;
        }
        
        public int getRank() { return rank; }
        public void setRank(int rank) { this.rank = rank; }
        public String getKode() { return kode; }
        public String getNama() { return nama; }
        public double getNilai() { return nilai; }
    }
    
    public static class MatrixItem {
        private String alternatif;
        private Map<String, Double> nilaiMap;
        
        public MatrixItem(String alternatif) {
            this.alternatif = alternatif;
            this.nilaiMap = new HashMap<>();
        }
        
        public String getAlternatif() { return alternatif; }
        
        public void setNilaiMap(Map<String, Double> nilaiMap) {
            this.nilaiMap = nilaiMap;
        }
        
        public Double getNilaiKriteria(String kodeKriteria) {
            return nilaiMap.getOrDefault(kodeKriteria, 0.0);
        }
    }
}
