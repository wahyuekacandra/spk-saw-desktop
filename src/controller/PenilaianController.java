package controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.*;
import service.DataManager;
import util.StyleHelper;
import java.util.List;

public class PenilaianController {
    private DataManager dataManager;
    private VBox fieldsBox;
    
    public PenilaianController() {
        this.dataManager = DataManager.getInstance();
    }
    
    public VBox getView() {
        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(30));
        vbox.setStyle(StyleHelper.getPageStyle());
        
        // Header
        VBox headerBox = new VBox(8);
        Label title = new Label("Input Penilaian");
        title.setStyle(StyleHelper.getTitleStyle());
        Label subtitle = new Label("Berikan nilai untuk setiap kriteria pada alternatif yang dipilih");
        subtitle.setStyle("-fx-font-size: 13px; -fx-text-fill: " + StyleHelper.TEXT_SECONDARY + ";");
        headerBox.getChildren().addAll(title, subtitle);
        
        List<Alternatif> alternatifList = dataManager.getAllAlternatif();
        List<Kriteria> kriteriaList = dataManager.getAllKriteria();
        
        if (alternatifList.isEmpty()) {
            VBox warningCard = new VBox(15);
            warningCard.setPadding(new Insets(25));
            warningCard.setStyle("-fx-background-color: #FFF3E0; -fx-border-color: " + StyleHelper.WARNING_COLOR + "; -fx-border-width: 1; -fx-border-radius: 8; -fx-background-radius: 8;");
            Label warningIcon = new Label("⚠️");
            warningIcon.setStyle("-fx-font-size: 32px;");
            Label warning = new Label("Belum ada alternatif. Silakan tambahkan alternatif terlebih dahulu di tab 'Alternatif'.");
            warning.setStyle("-fx-text-fill: " + StyleHelper.WARNING_COLOR + "; -fx-font-weight: bold; -fx-font-size: 14px;");
            warningCard.getChildren().addAll(warningIcon, warning);
            warningCard.setAlignment(Pos.CENTER);
            vbox.getChildren().addAll(headerBox, warningCard);
            return vbox;
        }
        
        if (kriteriaList.isEmpty()) {
            VBox warningCard = new VBox(15);
            warningCard.setPadding(new Insets(25));
            warningCard.setStyle("-fx-background-color: #FFF3E0; -fx-border-color: " + StyleHelper.WARNING_COLOR + "; -fx-border-width: 1; -fx-border-radius: 8; -fx-background-radius: 8;");
            Label warningIcon = new Label("⚠️");
            warningIcon.setStyle("-fx-font-size: 32px;");
            Label warning = new Label("Belum ada kriteria. Silakan tambahkan kriteria terlebih dahulu di tab 'Kriteria'.");
            warning.setStyle("-fx-text-fill: " + StyleHelper.WARNING_COLOR + "; -fx-font-weight: bold; -fx-font-size: 14px;");
            warningCard.getChildren().addAll(warningIcon, warning);
            warningCard.setAlignment(Pos.CENTER);
            vbox.getChildren().addAll(headerBox, warningCard);
            return vbox;
        }
        
        // Selector Card
        VBox selectorCard = new VBox(15);
        selectorCard.setPadding(new Insets(25));
        selectorCard.setStyle(StyleHelper.getCardStyle());
        
        Label selectorTitle = new Label("Pilih Alternatif");
        selectorTitle.setStyle(StyleHelper.getSubtitleStyle());
        
        HBox selectorBox = new HBox(15);
        selectorBox.setAlignment(Pos.CENTER_LEFT);
        
        Label lblPilih = new Label("Alternatif:");
        lblPilih.setStyle(StyleHelper.getLabelStyle());
        
        ComboBox<String> cbAlternatif = new ComboBox<>();
        cbAlternatif.setPromptText("-- Pilih Alternatif --");
        cbAlternatif.setPrefWidth(400);
        cbAlternatif.setStyle(StyleHelper.getFormFieldStyle());
        for (Alternatif alt : alternatifList) {
            cbAlternatif.getItems().add(alt.getKode() + " - " + alt.getNama());
        }
        
        selectorBox.getChildren().addAll(lblPilih, cbAlternatif);
        selectorCard.getChildren().addAll(selectorTitle, selectorBox);
        
        fieldsBox = new VBox(20);
        fieldsBox.setPadding(new Insets(25));
        fieldsBox.setStyle(StyleHelper.getCardStyle());
        
        cbAlternatif.setOnAction(e -> {
            fieldsBox.getChildren().clear();
            String selected = cbAlternatif.getValue();
            if (selected != null) {
                String kodeAlt = selected.split(" - ")[0];
                Alternatif alt = dataManager.getAlternatifByKode(kodeAlt);
                
                if (alt != null) {
                    Label subtitle2 = new Label("📊 Masukkan Nilai Kriteria");
                    subtitle2.setStyle(StyleHelper.getSubtitleStyle());
                    fieldsBox.getChildren().add(subtitle2);
                    
                    Label infoLabel = new Label("Alternatif: " + alt.getNama());
                    infoLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: " + StyleHelper.TEXT_PRIMARY + "; -fx-font-weight: 600; -fx-padding: 0 0 10 0;");
                    fieldsBox.getChildren().add(infoLabel);
                    
                    GridPane criteriaGrid = new GridPane();
                    criteriaGrid.setHgap(20);
                    criteriaGrid.setVgap(15);
                    criteriaGrid.setPadding(new Insets(15, 0, 15, 0));
                    
                    int row = 0;
                    for (Kriteria krit : kriteriaList) {
                        VBox criteriaBox = new VBox(5);
                        
                        Label label = new Label(krit.getNama());
                        label.setStyle("-fx-font-size: 13px; -fx-text-fill: " + StyleHelper.TEXT_PRIMARY + "; -fx-font-weight: 600;");
                        
                        Label typeLabel = new Label("Jenis: " + krit.getJenis() + " | Bobot: " + krit.getBobot());
                        typeLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: " + StyleHelper.TEXT_SECONDARY + ";");
                        
                        criteriaBox.getChildren().addAll(label, typeLabel);
                        
                        TextField textField = new TextField();
                        textField.setText(String.valueOf(alt.getNilai(krit.getKode())));
                        textField.setId(krit.getKode());
                        textField.setPromptText("Masukkan nilai");
                        textField.setPrefWidth(200);
                        textField.setStyle(StyleHelper.getFormFieldStyle());
                        
                        criteriaGrid.add(criteriaBox, 0, row);
                        criteriaGrid.add(textField, 1, row);
                        row++;
                    }
                    
                    fieldsBox.getChildren().add(criteriaGrid);
                    
                    Separator sep = new Separator();
                    sep.setStyle(StyleHelper.getSeparatorStyle());
                    fieldsBox.getChildren().add(sep);
                    
                    HBox buttonBox = new HBox();
                    buttonBox.setAlignment(Pos.CENTER_RIGHT);
                    Button btnSimpan = new Button("💾 Simpan Penilaian");
                    btnSimpan.setStyle(StyleHelper.getSuccessButtonStyle() + " -fx-font-size: 14px; -fx-padding: 12 30;");
                    btnSimpan.setOnMouseEntered(ev -> btnSimpan.setStyle(StyleHelper.getSuccessButtonHoverStyle() + " -fx-font-size: 14px; -fx-padding: 12 30;"));
                    btnSimpan.setOnMouseExited(ev -> btnSimpan.setStyle(StyleHelper.getSuccessButtonStyle() + " -fx-font-size: 14px; -fx-padding: 12 30;"));
                    btnSimpan.setOnAction(ev -> {
                        try {
                            for (var node : criteriaGrid.getChildren()) {
                                if (node instanceof TextField) {
                                    TextField tf = (TextField) node;
                                    alt.setNilai(tf.getId(), Double.parseDouble(tf.getText()));
                                }
                            }
                            dataManager.saveData();
                            showAlert("Sukses", "Penilaian berhasil disimpan untuk " + alt.getNama() + "!", Alert.AlertType.INFORMATION);
                        } catch (NumberFormatException ex) {
                            showAlert("Error", "Semua nilai harus berupa angka!", Alert.AlertType.ERROR);
                        }
                    });
                    
                    buttonBox.getChildren().add(btnSimpan);
                    fieldsBox.getChildren().add(buttonBox);
                }
            }
        });
        
        ScrollPane scrollPane = new ScrollPane(fieldsBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        
        vbox.getChildren().addAll(headerBox, selectorCard, scrollPane);
        
        return vbox;
    }
    
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
