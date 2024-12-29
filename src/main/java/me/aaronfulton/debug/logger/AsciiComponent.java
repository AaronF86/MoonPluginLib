package me.aaronfulton.debug.logger;

public class AsciiComponent {

    public static final String ANSI_RESET = "\u001B[0m";
    private final StringBuilder builder = new StringBuilder();
    private Color currentColor;
    private BackgroundColor currentBackground;
    private Format currentFormat;
    // Constructor that initializes the component with text
    private AsciiComponent(String text) {
        builder.append(text);
    }

    // Factory method to create an AsciiComponent
    public static AsciiComponent text(String text) {
        return new AsciiComponent(text);
    }

    // Method to set text color
    public AsciiComponent color(Color color) {
        this.currentColor = color;
        return this;
    }

    // Method to set background color
    public AsciiComponent bgcolor(BackgroundColor bgColor) {
        this.currentBackground = bgColor;
        return this;
    }

    // Method to set text formatting
    public AsciiComponent format(Format format) {
        this.currentFormat = format;
        return this;
    }

    // Method to add another AsciiComponent
    public AsciiComponent add(AsciiComponent component) {
        builder.append(component.currentColor != null ? component.currentColor.getCode() : "")
                .append(component.currentBackground != null ? component.currentBackground.getCode() : "")
                .append(component.currentFormat != null ? component.currentFormat.getCode() : "")
                .append(component.builder.toString());
        return this;
    }

    // Method to reset formatting
    public AsciiComponent reset() {
        this.currentColor = null;
        this.currentBackground = null;
        this.currentFormat = null;
        builder.append("\u001B[0m");
        return this;
    }

    // Method to build the final string with escape codes
    public String build() {
        String colorCode = currentColor != null ? currentColor.getCode() : "";
        String backgroundCode = currentBackground != null ? currentBackground.getCode() : "";
        String formatCode = currentFormat != null ? currentFormat.getCode() : "";
        return colorCode + backgroundCode + formatCode + builder.toString() + "\u001B[0m";
    }

    // Enum for text colors
    public enum Color {
        BLACK("\u001B[30m"),
        RED("\u001B[31m"),
        GREEN("\u001B[32m"),
        YELLOW("\u001B[33m"),
        BLUE("\u001B[34m"),
        PURPLE("\u001B[35m"),
        CYAN("\u001B[36m"),
        WHITE("\u001B[37m");

        private final String code;

        Color(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    // Enum for background colors
    public enum BackgroundColor {
        BLACK("\u001B[40m"),
        RED("\u001B[41m"),
        GREEN("\u001B[42m"),
        YELLOW("\u001B[43m"),
        BLUE("\u001B[44m"),
        PURPLE("\u001B[45m"),
        CYAN("\u001B[46m"),
        WHITE("\u001B[47m");

        private final String code;

        BackgroundColor(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    // Enum for text formatting
    public enum Format {
        BOLD("\u001B[1m"),
        ITALIC("\u001B[3m"),
        UNDERLINE("\u001B[4m"),
        BLINK("\u001B[5m"),
        REVERSE("\u001B[7m"),
        INVISIBLE("\u001B[8m");

        private final String code;

        Format(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }
}
