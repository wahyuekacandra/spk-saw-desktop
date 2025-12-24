import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import controller.*;
import java.io.InputStream;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        // Set Application Icon
        try {
            InputStream iconStream = getClass().getResourceAsStream("/icons/app-icon.png");
            if (iconStream != null) {
                Image icon = new Image(iconStream);
                primaryStage.getIcons().add(icon);
                System.out.println("Icon berhasil dimuat");
            } else {
                System.out.println("Icon tidak ditemukan di resources, menggunakan icon default");
            }
        } catch (Exception e) {
            System.out.println("Error loading icon: " + e.getMessage());
        }
        // Create TabPane
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        
        // Create Controllers
        KriteriaController kriteriaController = new KriteriaController();
        AlternatifController alternatifController = new AlternatifController();
        PenilaianController penilaianController = new PenilaianController();
        HasilController hasilController = new HasilController();
        
        // Create Tabs with Controllers
        Tab tabKriteria = new Tab("Kriteria");
        tabKriteria.setContent(kriteriaController.getView());
        
        Tab tabAlternatif = new Tab("Alternatif");
        tabAlternatif.setContent(alternatifController.getView());
        
        Tab tabPenilaian = new Tab("Penilaian");
        tabPenilaian.setContent(penilaianController.getView());
        
        Tab tabHasil = new Tab("Hasil & Ranking");
        tabHasil.setContent(hasilController.getView());
        
        // Create Team Tab
        Tab tabTim = new Tab("Tentang Tim");
        tabTim.setContent(createTeamView());
        
        // Refresh view when tab is selected
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab == tabPenilaian) {
                tabPenilaian.setContent(new PenilaianController().getView());
            } else if (newTab == tabHasil) {
                tabHasil.setContent(new HasilController().getView());
            } else if (newTab == tabAlternatif) {
                tabAlternatif.setContent(new AlternatifController().getView());
            } else if (newTab == tabKriteria) {
                tabKriteria.setContent(new KriteriaController().getView());
            }
        });
        
        // Add tabs to TabPane
        tabPane.getTabs().addAll(tabKriteria, tabAlternatif, tabPenilaian, tabHasil, tabTim);
        
        // Create main container
        VBox root = new VBox();
        
        // Header
        HBox header = new HBox();
        header.setPadding(new Insets(15));
        header.setStyle("-fx-background-color: #2196F3;");
        
        Label titleLabel = new Label("Sistem Pendukung Keputusan - Metode SAW");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        Label subtitleLabel = new Label("(Simple Additive Weighting)");
        subtitleLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: white; -fx-padding: 5 0 0 10;");
        
        header.getChildren().addAll(titleLabel, subtitleLabel);
        
        root.getChildren().addAll(header, tabPane);
        VBox.setVgrow(tabPane, Priority.ALWAYS);
        
        // Create Scene
        Scene scene = new Scene(root, 1000, 700);
        
        // Set Stage
        primaryStage.setTitle("Sistem Pendukung Keputusan");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.show();
        
        // Show welcome dialog
        showWelcomeDialog();
    }
    
    private VBox createTeamView() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background: #f5f7fa; -fx-background-color: #f5f7fa;");
        
        VBox container = new VBox(30);
        container.setPadding(new Insets(40));
        container.setStyle("-fx-background-color: #f5f7fa;");
        container.setAlignment(javafx.geometry.Pos.TOP_CENTER);
        
        // Header Section
        VBox headerBox = new VBox(15);
        headerBox.setAlignment(javafx.geometry.Pos.CENTER);
        headerBox.setPadding(new Insets(0, 0, 20, 0));
        
        Label titleLabel = new Label("Tim Pengembang");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2196F3;");
        
        Label subtitleLabel = new Label("Sistem Pendukung Keputusan - Metode SAW");
        subtitleLabel.setStyle("-fx-font-size: 15px; -fx-text-fill: #607D8B;");
        
        Separator separator = new Separator();
        separator.setMaxWidth(500);
        separator.prefWidthProperty().bind(container.widthProperty().multiply(0.5));
        separator.setStyle("-fx-background-color: #CFD8DC;");
        
        headerBox.getChildren().addAll(titleLabel, subtitleLabel, separator);
        
        // Team members container
        VBox teamBox = new VBox(15);
        teamBox.setAlignment(javafx.geometry.Pos.CENTER);
        teamBox.setPadding(new Insets(30));
        teamBox.prefWidthProperty().bind(container.widthProperty().multiply(0.7));
        teamBox.setMaxWidth(800);
        teamBox.setStyle("-fx-background-color: white; " +
                        "-fx-background-radius: 12; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 12, 0, 0, 2);");
        
        Label teamTitle = new Label("Anggota Kelompok");
        teamTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2196F3; -fx-padding: 0 0 10 0;");
        
        // Create member items
        VBox member1 = createMemberItem("1", "Wahyu Eka Candra");
        VBox member2 = createMemberItem("2", "Muhammad Khaesaar Juniardi");
        VBox member3 = createMemberItem("3", "Nanda Kamila Azzahra");
        
        teamBox.getChildren().addAll(teamTitle, member1, member2, member3);
        
        // Footer
        Label footerLabel = new Label("© 2025 - Semester 6 - Sistem Pendukung Keputusan");
        footerLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #90A4AE; -fx-padding: 10 0 0 0;");
        
        container.getChildren().addAll(headerBox, teamBox, footerLabel);
        
        scrollPane.setContent(container);
        
        VBox wrapper = new VBox(scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        
        return wrapper;
    }
    
    private VBox createMemberItem(String number, String name) {
        VBox item = new VBox(8);
        item.setPadding(new Insets(15, 20, 15, 20));
        item.setStyle("-fx-background-color: #FAFAFA; " +
                     "-fx-background-radius: 8; " +
                     "-fx-border-color: #E0E0E0; " +
                     "-fx-border-width: 1; " +
                     "-fx-border-radius: 8;");
        
        HBox contentBox = new HBox(15);
        contentBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        
        // Number badge
        Label numberLabel = new Label(number);
        numberLabel.setStyle("-fx-background-color: #2196F3; " +
                           "-fx-text-fill: white; " +
                           "-fx-font-size: 16px; " +
                           "-fx-font-weight: bold; " +
                           "-fx-padding: 6 12 6 12; " +
                           "-fx-background-radius: 6; " +
                           "-fx-min-width: 35px; " +
                           "-fx-alignment: center;");
        
        // Name
        Label nameLabel = new Label(name);
        nameLabel.setStyle("-fx-font-size: 15px; " +
                          "-fx-font-weight: 500; " +
                          "-fx-text-fill: #37474F;");
        
        contentBox.getChildren().addAll(numberLabel, nameLabel);
        item.getChildren().add(contentBox);
        
        // Hover effect
        item.setOnMouseEntered(e -> {
            item.setStyle("-fx-background-color: #E3F2FD; " +
                         "-fx-background-radius: 8; " +
                         "-fx-border-color: #2196F3; " +
                         "-fx-border-width: 1; " +
                         "-fx-border-radius: 8;");
        });
        
        item.setOnMouseExited(e -> {
            item.setStyle("-fx-background-color: #FAFAFA; " +
                         "-fx-background-radius: 8; " +
                         "-fx-border-color: #E0E0E0; " +
                         "-fx-border-width: 1; " +
                         "-fx-border-radius: 8;");
        });
        
        return item;
    }
    
    private void showWelcomeDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Selamat Datang");
        alert.setHeaderText("Sistem Pendukung Keputusan - Metode SAW");
        alert.setContentText(
            "Aplikasi ini menggunakan metode Simple Additive Weighting (SAW) " +
            "untuk membantu pengambilan keputusan.\n\n" +
            "Langkah penggunaan:\n" +
            "1. Tambahkan Kriteria (bobot & jenis)\n" +
            "2. Tambahkan Alternatif\n" +
            "3. Input Penilaian untuk setiap alternatif\n" +
            "4. Lihat Hasil & Ranking\n\n" +
            "Selamat menggunakan!"
        );
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
