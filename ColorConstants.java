package org.anglewyrm.labodoom;

public class ColorConstants {
    // ANSI escape codes for dark red (false)
    public static final String FALSE_COLOR = "\u001B[31m"; // Regular red
    public static final String DARK_RED = "\u001B[31m";   // Alias for clarity

    // ANSI escape codes for light green (true)
    public static final String TRUE_COLOR = "\u001B[92m";  // Bright green
    public static final String LIGHT_GREEN = "\u001B[92m"; // Alias for clarity

    // Reset color code
    public static final String RESET_COLOR = "\u001B[0m";
}