package controller;

import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import model.Kriteria;
import service.DataManager;
import util.StyleHelper;

public class KriteriaController {
    private DataManager dataManager;
    private TableView<Kriteria> table;
    private ObservableList<Kriteria> data;
    
    public KriteriaController() {
        this.dataManager = DataManager.getInstance();
        this.data = FXCollections.observableArrayList(dataManager.getAllKriteria());
    }
    
    public VBox getView() {
        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(30));
        vbox.setStyle(StyleHelper.getPageStyle());
        
        // Header
        VBox headerBox = new VBox(8);
        Label title = new Label("Kelola Kriteria");
        title.setStyle(StyleHelper.getTitleStyle());
        Label subtitle = new Label("Tambahkan dan kelola kriteria penilaian untuk metode SAW");
        subtitle.setStyle("-fx-font-size: 13px; -fx-text-fill: " + StyleHelper.TEXT_SECONDARY + ";");
        headerBox.getChildren().addAll(title, subtitle);
        
        // Form Card
        VBox formCard = new VBox(15);
        formCard.setPadding(new Insets(25));
        formCard.setStyle(StyleHelper.getCardStyle());
        
        Label formTitle = new Label("Tambah Kriteria Baru");
        formTitle.setStyle(StyleHelper.getSubtitleStyle());
        
        GridPane form = createForm();
        formCard.getChildren().addAll(formTitle, form);
        
        // Table Card
        VBox tableCard = new VBox(15);
        tableCard.setPadding(new Insets(25));
        tableCard.setStyle(StyleHelper.getCardStyle());
        
        Label tableTitle = new Label("Daftar Kriteria");
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
        
        // Kode Kriteria
        Label lblKode = new Label("Kode Kriteria");
        lblKode.setStyle(StyleHelper.getLabelStyle());
        TextField txtKode = new TextField();
        txtKode.setPromptText("Contoh: C1, C2, C3");
        txtKode.setStyle(StyleHelper.getFormFieldStyle());
        txtKode.setPrefWidth(250);
        
        // Nama Kriteria
        Label lblNama = new Label("Nama Kriteria");
        lblNama.setStyle(StyleHelper.getLabelStyle());
        TextField txtNama = new TextField();
        txtNama.setPromptText("Contoh: Pengalaman Kerja");
        txtNama.setStyle(StyleHelper.getFormFieldStyle());
        txtNama.setPrefWidth(250);
        
        // Bobot
        Label lblBobot = new Label("Bobot");
        lblBobot.setStyle(StyleHelper.getLabelStyle());
        TextField txtBobot = new TextField();
        txtBobot.setPromptText("Contoh: 0.3 (Total = 1)");
        txtBobot.setStyle(StyleHelper.getFormFieldStyle());
        txtBobot.setPrefWidth(250);
        
        // Jenis
        Label lblJenis = new Label("Jenis Kriteria");
        lblJenis.setStyle(StyleHelper.getLabelStyle());
        ComboBox<String> cbJenis = new ComboBox<>();
        cbJenis.getItems().addAll("benefit", "cost");
        cbJenis.setValue("benefit");
        cbJenis.setStyle(StyleHelper.getFormFieldStyle());
        cbJenis.setPrefWidth(250);
        
        // Button
        Button btnTambah = new Button("✓ Tambah Kriteria");
        btnTambah.setStyle(StyleHelper.getSuccessButtonStyle());
        btnTambah.setOnMouseEntered(e -> btnTambah.setStyle(StyleHelper.getSuccessButtonHoverStyle()));
        btnTambah.setOnMouseExited(e -> btnTambah.setStyle(StyleHelper.getSuccessButtonStyle()));
        btnTambah.setOnAction(e -> {
            try {
                if (txtKode.getText().isEmpty() || txtNama.getText().isEmpty() || txtBobot.getText().isEmpty()) {
                    showAlert("Error", "Semua field harus diisi!", Alert.AlertType.ERROR);
                    return;
                }
                
                double bobot = Double.parseDouble(txtBobot.getText());
                if (bobot < 0 || bobot > 1) {
                    showAlert("Error", "Bobot harus antara 0 dan 1!", Alert.AlertType.ERROR);
                    return;
                }
                
                Kriteria kriteria = new Kriteria(
                    txtKode.getText(),
                    txtNama.getText(),
                    bobot,
                    cbJenis.getValue()
                );
                
                dataManager.addKriteria(kriteria);
                data.add(kriteria);
                
                txtKode.clear();
                txtNama.clear();
                txtBobot.clear();
                cbJenis.setValue("benefit");
                
                showAlert("Sukses", "Kriteria berhasil ditambahkan!", Alert.AlertType.INFORMATION);
            } catch (NumberFormatException ex) {
                showAlert("Error", "Bobot harus berupa angka desimal (contoh: 0.3)!", Alert.AlertType.ERROR);
            }
        });
        
        // Layout
        grid.add(lblKode, 0, 0);
        grid.add(txtKode, 1, 0);
        grid.add(lblNama, 2, 0);
        grid.add(txtNama, 3, 0);
        
        grid.add(lblBobot, 0, 1);
        grid.add(txtBobot, 1, 1);
        grid.add(lblJenis, 2, 1);
        grid.add(cbJenis, 3, 1);
        
        HBox buttonBox = new HBox();
        buttonBox.getChildren().add(btnTambah);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        grid.add(buttonBox, 3, 2);
        
        return grid;
    }
    
    private TableView<Kriteria> createTable() {
        TableView<Kriteria> table = new TableView<>();
        table.setItems(data);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setStyle(StyleHelper.getTableStyle());
        table.setPrefHeight(300);
        
        TableColumn<Kriteria, String> colKode = new TableColumn<>("Kode");
        colKode.setCellValueFactory(new PropertyValueFactory<>("kode"));
        colKode.setPrefWidth(100);
        colKode.setStyle("-fx-alignment: CENTER;");
        
        TableColumn<Kriteria, String> colNama = new TableColumn<>("Nama Kriteria");
        colNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colNama.setPrefWidth(300);
        
        TableColumn<Kriteria, Double> colBobot = new TableColumn<>("Bobot");
        colBobot.setCellValueFactory(new PropertyValueFactory<>("bobot"));
        colBobot.setPrefWidth(100);
        colBobot.setStyle("-fx-alignment: CENTER;");
        
        TableColumn<Kriteria, String> colJenis = new TableColumn<>("Jenis");
        colJenis.setCellValueFactory(new PropertyValueFactory<>("jenis"));
        colJenis.setPrefWidth(120);
        colJenis.setStyle("-fx-alignment: CENTER;");
        colJenis.setCellFactory(column -> new TableCell<Kriteria, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("-fx-alignment: CENTER;");
                } else {
                    setText(item);
                    if (item.equals("benefit")) {
                        setStyle("-fx-text-fill: " + StyleHelper.SUCCESS_COLOR + "; -fx-font-weight: bold; -fx-alignment: CENTER;");
                    } else {
                        setStyle("-fx-text-fill: " + StyleHelper.WARNING_COLOR + "; -fx-font-weight: bold; -fx-alignment: CENTER;");
                    }
                }
            }
        });
        
        TableColumn<Kriteria, Void> colAksi = new TableColumn<>("Aksi");
        colAksi.setPrefWidth(100);
        colAksi.setStyle("-fx-alignment: CENTER;");
        colAksi.setCellFactory(param -> new TableCell<>() {
            private final Button btnHapus = new Button("Hapus");
            
            {
                btnHapus.setStyle(StyleHelper.getDangerButtonStyle());
                btnHapus.setOnMouseEntered(e -> btnHapus.setStyle(StyleHelper.getDangerButtonHoverStyle()));
                btnHapus.setOnMouseExited(e -> btnHapus.setStyle(StyleHelper.getDangerButtonStyle()));
                btnHapus.setOnAction(e -> {
                    Kriteria kriteria = getTableView().getItems().get(getIndex());
                    
                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                    confirm.setTitle("Konfirmasi Hapus");
                    confirm.setHeaderText(null);
                    confirm.setContentText("Apakah Anda yakin ingin menghapus kriteria '" + kriteria.getNama() + "'?");
                    
                    confirm.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            dataManager.deleteKriteria(kriteria.getKode());
                            data.remove(kriteria);
                            showAlert("Sukses", "Kriteria berhasil dihapus!", Alert.AlertType.INFORMATION);
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
        
        table.getColumns().addAll(colKode, colNama, colBobot, colJenis, colAksi);
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
