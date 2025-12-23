package util;

public class StyleHelper {
    // Color Palette
    public static final String PRIMARY_COLOR = "#2196F3";
    public static final String PRIMARY_DARK = "#1976D2";
    public static final String PRIMARY_LIGHT = "#E3F2FD";
    public static final String SUCCESS_COLOR = "#4CAF50";
    public static final String SUCCESS_HOVER = "#45A049";
    public static final String DANGER_COLOR = "#F44336";
    public static final String DANGER_HOVER = "#E53935";
    public static final String WARNING_COLOR = "#FF9800";
    public static final String BACKGROUND = "#F5F7FA";
    public static final String CARD_BG = "#FFFFFF";
    public static final String TEXT_PRIMARY = "#263238";
    public static final String TEXT_SECONDARY = "#546E7A";
    public static final String BORDER_COLOR = "#E0E0E0";
    
    // Common Styles
    public static String getPageStyle() {
        return "-fx-background-color: " + BACKGROUND + ";";
    }
    
    public static String getTitleStyle() {
        return "-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: " + PRIMARY_COLOR + ";";
    }
    
    public static String getSubtitleStyle() {
        return "-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";";
    }
    
    public static String getCardStyle() {
        return "-fx-background-color: " + CARD_BG + "; " +
               "-fx-background-radius: 10; " +
               "-fx-border-color: " + BORDER_COLOR + "; " +
               "-fx-border-width: 1; " +
               "-fx-border-radius: 10; " +
               "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 10, 0, 0, 2);";
    }
    
    public static String getFormFieldStyle() {
        return "-fx-font-size: 13px; " +
               "-fx-border-color: " + BORDER_COLOR + "; " +
               "-fx-border-radius: 6; " +
               "-fx-background-radius: 6; " +
               "-fx-padding: 8;";
    }
    
    public static String getLabelStyle() {
        return "-fx-font-size: 13px; -fx-text-fill: " + TEXT_SECONDARY + "; -fx-font-weight: 500;";
    }
    
    public static String getPrimaryButtonStyle() {
        return "-fx-background-color: " + PRIMARY_COLOR + "; " +
               "-fx-text-fill: white; " +
               "-fx-font-weight: bold; " +
               "-fx-font-size: 13px; " +
               "-fx-padding: 10 20; " +
               "-fx-cursor: hand; " +
               "-fx-background-radius: 6;";
    }
    
    public static String getPrimaryButtonHoverStyle() {
        return "-fx-background-color: " + PRIMARY_DARK + "; " +
               "-fx-text-fill: white; " +
               "-fx-font-weight: bold; " +
               "-fx-font-size: 13px; " +
               "-fx-padding: 10 20; " +
               "-fx-cursor: hand; " +
               "-fx-background-radius: 6;";
    }
    
    public static String getSuccessButtonStyle() {
        return "-fx-background-color: " + SUCCESS_COLOR + "; " +
               "-fx-text-fill: white; " +
               "-fx-font-weight: bold; " +
               "-fx-font-size: 13px; " +
               "-fx-padding: 10 20; " +
               "-fx-cursor: hand; " +
               "-fx-background-radius: 6;";
    }
    
    public static String getSuccessButtonHoverStyle() {
        return "-fx-background-color: " + SUCCESS_HOVER + "; " +
               "-fx-text-fill: white; " +
               "-fx-font-weight: bold; " +
               "-fx-font-size: 13px; " +
               "-fx-padding: 10 20; " +
               "-fx-cursor: hand; " +
               "-fx-background-radius: 6;";
    }
    
    public static String getDangerButtonStyle() {
        return "-fx-background-color: " + DANGER_COLOR + "; " +
               "-fx-text-fill: white; " +
               "-fx-font-weight: 600; " +
               "-fx-font-size: 12px; " +
               "-fx-padding: 6 15; " +
               "-fx-cursor: hand; " +
               "-fx-background-radius: 5;";
    }
    
    public static String getDangerButtonHoverStyle() {
        return "-fx-background-color: " + DANGER_HOVER + "; " +
               "-fx-text-fill: white; " +
               "-fx-font-weight: 600; " +
               "-fx-font-size: 12px; " +
               "-fx-padding: 6 15; " +
               "-fx-cursor: hand; " +
               "-fx-background-radius: 5;";
    }
    
    public static String getTableStyle() {
        return "-fx-background-color: white; " +
               "-fx-border-color: " + BORDER_COLOR + "; " +
               "-fx-border-width: 1; " +
               "-fx-border-radius: 8; " +
               "-fx-background-radius: 8;";
    }
    
    public static String getSeparatorStyle() {
        return "-fx-background-color: " + BORDER_COLOR + ";";
    }
    
    public static String getInfoBoxStyle() {
        return "-fx-background-color: " + PRIMARY_LIGHT + "; " +
               "-fx-border-color: " + PRIMARY_COLOR + "; " +
               "-fx-border-width: 1; " +
               "-fx-border-radius: 8; " +
               "-fx-background-radius: 8; " +
               "-fx-padding: 15;";
    }
}
