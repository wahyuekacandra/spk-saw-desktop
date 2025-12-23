package model;

import java.util.HashMap;
import java.util.Map;

public class Alternatif {
    private String kode;
    private String nama;
    private Map<String, Double> nilai; // kode kriteria -> nilai
    
    public Alternatif() {
        // Default constructor for JSON deserialization
        this.nilai = new HashMap<>();
    }
    
    public Alternatif(String kode, String nama) {
        this.kode = kode;
        this.nama = nama;
        this.nilai = new HashMap<>();
    }
    
    public void setNilai(String kodeKriteria, double nilai) {
        this.nilai.put(kodeKriteria, nilai);
    }
    
    public double getNilai(String kodeKriteria) {
        return this.nilai.getOrDefault(kodeKriteria, 0.0);
    }
    
    // Getters and Setters
    public String getKode() { 
        return kode; 
    }
    
    public void setKode(String kode) { 
        this.kode = kode; 
    }
    
    public String getNama() { 
        return nama; 
    }
    
    public void setNama(String nama) { 
        this.nama = nama; 
    }
    
    public Map<String, Double> getAllNilai() { 
        return nilai; 
    }
    
    public void setAllNilai(Map<String, Double> nilai) {
        this.nilai = nilai;
    }
    
    @Override
    public String toString() {
        return kode + " - " + nama;
    }
}
