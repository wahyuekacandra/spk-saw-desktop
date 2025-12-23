package model;

public class Kriteria {
    private String kode;
    private String nama;
    private double bobot;
    private String jenis; // "benefit" atau "cost"
    
    public Kriteria() {
        // Default constructor for JSON deserialization
    }
    
    public Kriteria(String kode, String nama, double bobot, String jenis) {
        this.kode = kode;
        this.nama = nama;
        this.bobot = bobot;
        this.jenis = jenis;
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
    
    public double getBobot() { 
        return bobot; 
    }
    
    public void setBobot(double bobot) { 
        this.bobot = bobot; 
    }
    
    public String getJenis() { 
        return jenis; 
    }
    
    public void setJenis(String jenis) { 
        this.jenis = jenis; 
    }
    
    @Override
    public String toString() {
        return kode + " - " + nama + " (" + bobot + ", " + jenis + ")";
    }
}
