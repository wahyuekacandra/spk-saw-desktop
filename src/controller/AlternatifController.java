package controller;

import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import model.Alternatif;
import service.DataManager;
import util.StyleHelper;

public class AlternatifController {
    private DataManager dataManager;
    private TableView<Alternatif> table;
    private ObservableList<Alternatif> data;
    
    public AlternatifController() {
        this.dataManager = DataManager.getInstance();
        this.data = FXCollections.observableArrayList(dataManager.getAllAlternatif());
    }
    
    public VBox getView() {
        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(30));
        vbox.setStyle(StyleHelper.getPageStyle());
        
        // Header
        VBox headerBox = new VBox(8);
        Label title = new Label("Kelola Alternatif");
        title.setStyle(StyleHelper.getTitleStyle());
        Label subtitle = new Label("Tambahkan dan kelola alternatif (kandidat/pilihan) yang akan dinilai");
        subtitle.setStyle("-fx-font-size: 13px; -fx-text-fill: " + StyleHelper.TEXT_SECONDARY + ";");
        headerBox.getChildren().addAll(title, subtitle);
        
        // Form Card
        VBox formCard = new VBox(15);
        formCard.setPadding(new Insets(25));
        formCard.setStyle(StyleHelper.getCardStyle());
        
        Label formTitle = new Label("Tambah Alternatif Baru");
        formTitle.setStyle(StyleHelper.getSubtitleStyle());
        
        GridPane form = createForm();
        formCard.getChildren().addAll(formTitle, form);
        
        // Table Card
        VBox tableCard = new VBox(15);
        tableCard.setPadding(new Insets(25));
        tableCard.setStyle(StyleHelper.getCardStyle());
        
        Label tableTitle = new Label("Daftar Alternatif");
        tableTitle.setStyle(StyleHelper.getSubtitleStyle());
        
        table = createTable();
        tableCard.getChildren().addAll(tableTitle, table);
        
        vbox.getChildren().addAll(headerBox, formCard, tableCard);
        
        return vbox;
    }
    
    private GridPane createForm() {
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(10, 0, 0, 0));
        
        // Kode Alternatif
        Label lblKode = new Label("Kode Alternatif");
        lblKode.setStyle(StyleHelper.getLabelStyle());
        TextField txtKode = new TextField();
        txtKode.setPromptText("Contoh: A1, A2, A3");
        txtKode.setStyle(StyleHelper.getFormFieldStyle());
        txtKode.setPrefWidth(250);
        
        // Nama Alternatif
        Label lblNama = new Label("Nama Alternatif");
        lblNama.setStyle(StyleHelper.getLabelStyle());
        TextField txtNama = new TextField();
        txtNama.setPromptText("Contoh: John Doe");
        txtNama.setStyle(StyleHelper.getFormFieldStyle());
        txtNama.setPrefWidth(400);
        
        // Button
        Button btnTambah = new Button("✓ Tambah Alternatif");
        btnTambah.setStyle(StyleHelper.getSuccessButtonStyle());
        btnTambah.setOnMouseEntered(e -> btnTambah.setStyle(StyleHelper.getSuccessButtonHoverStyle()));
        btnTambah.setOnMouseExited(e -> btnTambah.setStyle(StyleHelper.getSuccessButtonStyle()));
        btnTambah.setOnAction(e -> {
            if (txtKode.getText().isEmpty() || txtNama.getText().isEmpty()) {
                showAlert("Error", "Semua field harus diisi!", Alert.AlertType.ERROR);
                return;
            }
            
            Alternatif alternatif = new Alternatif(txtKode.getText(), txtNama.getText());
            dataManager.addAlternatif(alternatif);
            data.add(alternatif);
            
            txtKode.clear();
            txtNama.clear();
            
            showAlert("Sukses", "Alternatif berhasil ditambahkan!", Alert.AlertType.INFORMATION);
        });
        
        // Layout
        grid.add(lblKode, 0, 0);
        grid.add(txtKode, 1, 0);
        grid.add(lblNama, 2, 0);
        grid.add(txtNama, 3, 0);
        
        HBox buttonBox = new HBox();
        buttonBox.getChildren().add(btnTambah);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        grid.add(buttonBox, 3, 1);
        
        return grid;
    }
    
    private TableView<Alternatif> createTable() {
        TableView<Alternatif> table = new TableView<>();
        table.setItems(data);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setStyle(StyleHelper.getTableStyle());
        table.setPrefHeight(300);
        
        TableColumn<Alternatif, String> colKode = new TableColumn<>("Kode");
        colKode.setCellValueFactory(new PropertyValueFactory<>("kode"));
        colKode.setPrefWidth(150);
        colKode.setStyle("-fx-alignment: CENTER;");
        
        TableColumn<Alternatif, String> colNama = new TableColumn<>("Nama Alternatif");
        colNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colNama.setPrefWidth(400);
        
        TableColumn<Alternatif, Void> colAksi = new TableColumn<>("Aksi");
        colAksi.setPrefWidth(100);
        colAksi.setStyle("-fx-alignment: CENTER;");
        colAksi.setCellFactory(param -> new TableCell<>() {
            private final Button btnHapus = new Button("Hapus");
            
            {
                btnHapus.setStyle(StyleHelper.getDangerButtonStyle());
                btnHapus.setOnMouseEntered(e -> btnHapus.setStyle(StyleHelper.getDangerButtonHoverStyle()));
                btnHapus.setOnMouseExited(e -> btnHapus.setStyle(StyleHelper.getDangerButtonStyle()));
                btnHapus.setOnAction(e -> {
                    Alternatif alt = getTableView().getItems().get(getIndex());
                    
                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                    confirm.setTitle("Konfirmasi Hapus");
                    confirm.setHeaderText(null);
                    confirm.setContentText("Apakah Anda yakin ingin menghapus alternatif '" + alt.getNama() + "'?");
                    
                    confirm.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            dataManager.deleteAlternatif(alt.getKode());
                            data.remove(alt);
                            showAlert("Sukses", "Alternatif berhasil dihapus!", Alert.AlertType.INFORMATION);
                        }
                    });
                });
            }
            
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btnHapus);
            }
        });
        
        table.getColumns().addAll(colKode, colNama, colAksi);
        return table;
    }
    
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
