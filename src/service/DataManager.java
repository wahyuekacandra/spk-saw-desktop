package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.*;
import java.io.*;
import java.util.*;

public class DataManager {
    private static final String DATA_FILE = "data/data.json";
    private static DataManager instance;
    private Gson gson;
    private DataStore dataStore;
    private boolean dataLoaded = false;
    
    private DataManager() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        loadData();
    }
    
    public static synchronized DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }
    
    private void loadData() {
        File dataFile = new File(DATA_FILE);
        try {
            if (dataFile.exists()) {
                try (FileReader reader = new FileReader(dataFile)) {
                    dataStore = gson.fromJson(reader, DataStore.class);
                    if (dataStore == null) {
                        dataStore = new DataStore();
                    }
                    dataLoaded = true;
                    System.out.println("Data berhasil dimuat dari: " + dataFile.getAbsolutePath());
                }
            } else {
                dataStore = new DataStore();
                if (!dataLoaded) {
                    System.out.println("File data tidak ditemukan, membuat data baru di: " + dataFile.getAbsolutePath());
                    dataLoaded = true;
                }
                // Buat file data awal
                saveData();
            }
        } catch (IOException e) {
            e.printStackTrace();
            dataStore = new DataStore();
        }
    }
    
    public void saveData() {
        try {
            new File("data").mkdirs();
            try (FileWriter writer = new FileWriter(DATA_FILE)) {
                gson.toJson(dataStore, writer);
                System.out.println("Data berhasil disimpan!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Gagal menyimpan data!");
        }
    }
    
    // Kriteria Methods
    public void addKriteria(Kriteria kriteria) {
        dataStore.kriteriaList.add(kriteria);
        saveData();
    }
    
    public void updateKriteria(String kode, Kriteria kriteriaBaru) {
        for (int i = 0; i < dataStore.kriteriaList.size(); i++) {
            if (dataStore.kriteriaList.get(i).getKode().equals(kode)) {
                dataStore.kriteriaList.set(i, kriteriaBaru);
                saveData();
                break;
            }
        }
    }
    
    public void deleteKriteria(String kode) {
        dataStore.kriteriaList.removeIf(k -> k.getKode().equals(kode));
        saveData();
    }
    
    public List<Kriteria> getAllKriteria() {
        return dataStore.kriteriaList;
    }
    
    public Kriteria getKriteriaByKode(String kode) {
        return dataStore.kriteriaList.stream()
            .filter(k -> k.getKode().equals(kode))
            .findFirst()
            .orElse(null);
    }
    
    // Alternatif Methods
    public void addAlternatif(Alternatif alternatif) {
        dataStore.alternatifList.add(alternatif);
        saveData();
    }
    
    public void updateAlternatif(String kode, Alternatif alternatifBaru) {
        for (int i = 0; i < dataStore.alternatifList.size(); i++) {
            if (dataStore.alternatifList.get(i).getKode().equals(kode)) {
                dataStore.alternatifList.set(i, alternatifBaru);
                saveData();
                break;
            }
        }
    }
    
    public void deleteAlternatif(String kode) {
        dataStore.alternatifList.removeIf(a -> a.getKode().equals(kode));
        saveData();
    }
    
    public List<Alternatif> getAllAlternatif() {
        return dataStore.alternatifList;
    }
    
    public Alternatif getAlternatifByKode(String kode) {
        return dataStore.alternatifList.stream()
            .filter(a -> a.getKode().equals(kode))
            .findFirst()
            .orElse(null);
    }
    
    // Inner class for JSON structure
    private static class DataStore {
        List<Kriteria> kriteriaList = new ArrayList<>();
        List<Alternatif> alternatifList = new ArrayList<>();
    }
}
