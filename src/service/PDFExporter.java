package service;

import model.*;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.awt.Color;

public class PDFExporter {
    private DataManager dataManager;
    
    public PDFExporter(DataManager dataManager) {
        this.dataManager = dataManager;
    }
    
    /**
     * Export hasil ranking ke PDF menggunakan iText
     */
    public void exportToPDF(Map<String, Double> ranking, Map<String, Map<String, Double>> matrixNormalisasi, File file) throws Exception {
        java.util.List<Kriteria> kriteriaList = dataManager.getAllKriteria();
        java.util.List<Alternatif> alternatifList = dataManager.getAllAlternatif();
        
        Document document = new Document(PageSize.A4);
        
        try {
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            
            // Title
            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD, new Color(33, 150, 243));
            Paragraph title = new Paragraph("LAPORAN HASIL PERHITUNGAN SAW\nSISTEM PENDUKUNG KEPUTUSAN", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);
            
            // Tanggal
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
            Font normalFont = new Font(Font.HELVETICA, 10, Font.NORMAL);
            Paragraph tanggal = new Paragraph("Tanggal: " + sdf.format(new Date()), normalFont);
            tanggal.setSpacingAfter(20);
            document.add(tanggal);
            
            // === DAFTAR KRITERIA ===
            Font sectionFont = new Font(Font.HELVETICA, 14, Font.BOLD, new Color(33, 150, 243));
            Paragraph kriteriaTitle = new Paragraph("DAFTAR KRITERIA", sectionFont);
            kriteriaTitle.setSpacingBefore(10);
            kriteriaTitle.setSpacingAfter(10);
            document.add(kriteriaTitle);
            
            PdfPTable kriteriaTable = new PdfPTable(4);
            kriteriaTable.setWidthPercentage(100);
            kriteriaTable.setWidths(new float[]{1, 3, 1.5f, 1.5f});
            
            // Header
            Font headerFont = new Font(Font.HELVETICA, 10, Font.BOLD, Color.WHITE);
            PdfPCell headerCell;
            
            String[] kriteriaHeaders = {"Kode", "Nama Kriteria", "Bobot", "Jenis"};
            for (String header : kriteriaHeaders) {
                headerCell = new PdfPCell(new Phrase(header, headerFont));
                headerCell.setBackgroundColor(new Color(33, 150, 243));
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerCell.setPadding(8);
                kriteriaTable.addCell(headerCell);
            }
            
            // Data
            Font dataFont = new Font(Font.HELVETICA, 9, Font.NORMAL);
            for (Kriteria krit : kriteriaList) {
                kriteriaTable.addCell(createCell(krit.getKode(), dataFont, Element.ALIGN_CENTER));
                kriteriaTable.addCell(createCell(krit.getNama(), dataFont, Element.ALIGN_LEFT));
                kriteriaTable.addCell(createCell(String.format("%.2f", krit.getBobot()), dataFont, Element.ALIGN_CENTER));
                kriteriaTable.addCell(createCell(krit.getJenis(), dataFont, Element.ALIGN_CENTER));
            }
            
            document.add(kriteriaTable);
            
            // === DAFTAR ALTERNATIF ===
            Paragraph alternatifTitle = new Paragraph("DAFTAR ALTERNATIF", sectionFont);
            alternatifTitle.setSpacingBefore(20);
            alternatifTitle.setSpacingAfter(10);
            document.add(alternatifTitle);
            
            PdfPTable alternatifTable = new PdfPTable(2);
            alternatifTable.setWidthPercentage(100);
            alternatifTable.setWidths(new float[]{1, 4});
            
            String[] altHeaders = {"Kode", "Nama Alternatif"};
            for (String header : altHeaders) {
                headerCell = new PdfPCell(new Phrase(header, headerFont));
                headerCell.setBackgroundColor(new Color(33, 150, 243));
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerCell.setPadding(8);
                alternatifTable.addCell(headerCell);
            }
            
            for (Alternatif alt : alternatifList) {
                alternatifTable.addCell(createCell(alt.getKode(), dataFont, Element.ALIGN_CENTER));
                alternatifTable.addCell(createCell(alt.getNama(), dataFont, Element.ALIGN_LEFT));
            }
            
            document.add(alternatifTable);
            
            // === NILAI ASLI ===
            Paragraph nilaiAsliTitle = new Paragraph("NILAI ASLI ALTERNATIF", sectionFont);
            nilaiAsliTitle.setSpacingBefore(20);
            nilaiAsliTitle.setSpacingAfter(10);
            document.add(nilaiAsliTitle);
            
            PdfPTable nilaiAsliTable = new PdfPTable(kriteriaList.size() + 1);
            nilaiAsliTable.setWidthPercentage(100);
            
            headerCell = new PdfPCell(new Phrase("Alternatif", headerFont));
            headerCell.setBackgroundColor(new Color(33, 150, 243));
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setPadding(8);
            nilaiAsliTable.addCell(headerCell);
            
            for (Kriteria krit : kriteriaList) {
                headerCell = new PdfPCell(new Phrase(krit.getKode(), headerFont));
                headerCell.setBackgroundColor(new Color(33, 150, 243));
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerCell.setPadding(8);
                nilaiAsliTable.addCell(headerCell);
            }
            
            for (Alternatif alt : alternatifList) {
                nilaiAsliTable.addCell(createCell(alt.getKode(), dataFont, Element.ALIGN_CENTER));
                for (Kriteria krit : kriteriaList) {
                    nilaiAsliTable.addCell(createCell(String.format("%.2f", alt.getNilai(krit.getKode())), dataFont, Element.ALIGN_CENTER));
                }
            }
            
            document.add(nilaiAsliTable);
            
            // === MATRIX NORMALISASI ===
            Paragraph normalisasiTitle = new Paragraph("MATRIX NORMALISASI", sectionFont);
            normalisasiTitle.setSpacingBefore(20);
            normalisasiTitle.setSpacingAfter(10);
            document.add(normalisasiTitle);
            
            PdfPTable normalisasiTable = new PdfPTable(kriteriaList.size() + 1);
            normalisasiTable.setWidthPercentage(100);
            
            headerCell = new PdfPCell(new Phrase("Alternatif", headerFont));
            headerCell.setBackgroundColor(new Color(33, 150, 243));
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setPadding(8);
            normalisasiTable.addCell(headerCell);
            
            for (Kriteria krit : kriteriaList) {
                headerCell = new PdfPCell(new Phrase(krit.getKode(), headerFont));
                headerCell.setBackgroundColor(new Color(33, 150, 243));
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerCell.setPadding(8);
                normalisasiTable.addCell(headerCell);
            }
            
            for (Alternatif alt : alternatifList) {
                normalisasiTable.addCell(createCell(alt.getKode(), dataFont, Element.ALIGN_CENTER));
                for (Kriteria krit : kriteriaList) {
                    Double nilai = matrixNormalisasi.get(alt.getKode()).get(krit.getKode());
                    normalisasiTable.addCell(createCell(String.format("%.4f", nilai), dataFont, Element.ALIGN_CENTER));
                }
            }
            
            document.add(normalisasiTable);
            
            // === HASIL RANKING ===
            Paragraph rankingTitle = new Paragraph("HASIL RANKING", sectionFont);
            rankingTitle.setSpacingBefore(20);
            rankingTitle.setSpacingAfter(10);
            document.add(rankingTitle);
            
            PdfPTable rankingTable = new PdfPTable(4);
            rankingTable.setWidthPercentage(100);
            rankingTable.setWidths(new float[]{1, 1.5f, 3, 2});
            
            String[] rankHeaders = {"Peringkat", "Kode", "Nama Alternatif", "Nilai Preferensi"};
            for (String header : rankHeaders) {
                headerCell = new PdfPCell(new Phrase(header, headerFont));
                headerCell.setBackgroundColor(new Color(76, 175, 80));
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerCell.setPadding(8);
                rankingTable.addCell(headerCell);
            }
            
            java.util.List<Map.Entry<String, Double>> sortedRanking = new ArrayList<>(ranking.entrySet());
            sortedRanking.sort((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()));
            
            Font rankFont = new Font(Font.HELVETICA, 9, Font.BOLD);
            int rank = 1;
            for (Map.Entry<String, Double> entry : sortedRanking) {
                Alternatif alt = dataManager.getAlternatifByKode(entry.getKey());
                
                PdfPCell rankCell = createCell(String.valueOf(rank), rankFont, Element.ALIGN_CENTER);
                if (rank == 1) {
                    rankCell.setBackgroundColor(new Color(255, 235, 59));
                }
                rankingTable.addCell(rankCell);
                
                rankingTable.addCell(createCell(entry.getKey(), dataFont, Element.ALIGN_CENTER));
                rankingTable.addCell(createCell(alt.getNama(), dataFont, Element.ALIGN_LEFT));
                rankingTable.addCell(createCell(String.format("%.4f", entry.getValue()), dataFont, Element.ALIGN_CENTER));
                
                rank++;
            }
            
            document.add(rankingTable);
            
            // Footer
            Paragraph footer = new Paragraph("\n\nMetode: Simple Additive Weighting (SAW)", new Font(Font.HELVETICA, 9, Font.ITALIC));
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);
            
        } catch (Exception e) {
            throw new Exception("Gagal membuat PDF: " + e.getMessage(), e);
        } finally {
            if (document != null && document.isOpen()) {
                document.close();
            }
        }
    }
    
    private PdfPCell createCell(String content, Font font, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setHorizontalAlignment(alignment);
        cell.setPadding(5);
        return cell;
    }
    
    /**
     * Generate default filename based on current timestamp
     */
    public static String generateFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return "Hasil_SAW_" + sdf.format(new Date()) + ".pdf";
    }
}
