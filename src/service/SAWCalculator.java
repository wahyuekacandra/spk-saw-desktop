package service;

import model.*;
import java.util.*;

public class SAWCalculator {
    private DataManager dataManager;
    
    public SAWCalculator(DataManager dataManager) {
        this.dataManager = dataManager;
    }
    
    /**
     * Menghitung ranking menggunakan metode SAW (Simple Additive Weighting)
     * @return Map dengan kode alternatif sebagai key dan nilai preferensi sebagai value
     */
    public Map<String, Double> calculate() {
        List<Kriteria> kriteriaList = dataManager.getAllKriteria();
        List<Alternatif> alternatifList = dataManager.getAllAlternatif();
        
        if (kriteriaList.isEmpty() || alternatifList.isEmpty()) {
            return new HashMap<>();
        }
        
        // Normalisasi Matrix
        Map<String, Map<String, Double>> matrixNormalisasi = normalisasi(kriteriaList, alternatifList);
        
        // Hitung Ranking dengan formula SAW
        Map<String, Double> ranking = new HashMap<>();
        
        for (Alternatif alt : alternatifList) {
            double totalNilai = 0;
            
            for (Kriteria krit : kriteriaList) {
                double nilaiNormalisasi = matrixNormalisasi.get(alt.getKode()).get(krit.getKode());
                totalNilai += nilaiNormalisasi * krit.getBobot();
            }
            
            ranking.put(alt.getKode(), totalNilai);
        }
        
        return ranking;
    }
    
    /**
     * Melakukan normalisasi matrix sesuai dengan jenis kriteria (benefit/cost)
     */
    private Map<String, Map<String, Double>> normalisasi(List<Kriteria> kriteriaList, List<Alternatif> alternatifList) {
        Map<String, Map<String, Double>> result = new HashMap<>();
        
        for (Kriteria krit : kriteriaList) {
            // Cari max/min untuk setiap kriteria
            double maxMin = 0;
            boolean isFirst = true;
            
            for (Alternatif alt : alternatifList) {
                double nilai = alt.getNilai(krit.getKode());
                
                if (isFirst) {
                    maxMin = nilai;
                    isFirst = false;
                } else {
                    if (krit.getJenis().equalsIgnoreCase("benefit")) {
                        maxMin = Math.max(maxMin, nilai);
                    } else {
                        maxMin = Math.min(maxMin, nilai);
                    }
                }
            }
            
            // Normalisasi
            for (Alternatif alt : alternatifList) {
                result.putIfAbsent(alt.getKode(), new HashMap<>());
                double nilai = alt.getNilai(krit.getKode());
                double nilaiNormalisasi;
                
                if (maxMin == 0) {
                    nilaiNormalisasi = 0;
                } else if (krit.getJenis().equalsIgnoreCase("benefit")) {
                    nilaiNormalisasi = nilai / maxMin;
                } else {
                    nilaiNormalisasi = maxMin / nilai;
                }
                
                result.get(alt.getKode()).put(krit.getKode(), nilaiNormalisasi);
            }
        }
        
        return result;
    }
    
    /**
     * Mendapatkan matrix normalisasi untuk ditampilkan
     */
    public Map<String, Map<String, Double>> getMatrixNormalisasi() {
        List<Kriteria> kriteriaList = dataManager.getAllKriteria();
        List<Alternatif> alternatifList = dataManager.getAllAlternatif();
        return normalisasi(kriteriaList, alternatifList);
    }
}
