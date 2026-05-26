package com.automates.utils;

/**
 * General-purpose string utility methods for the AutoMATEs framework.
 *
 * @author Banoth Mahesh Kumar
 */
public final class StringUtils {

    private StringUtils() { /* utility class */ }

    /**
     * Returns {@code true} if {@code s} is null or contains only whitespace.
     */
    public static boolean isBlank(String s) {
        return s == null || s.strip().isEmpty();
    }

    /**
     * Truncates {@code s} to at most {@code maxLen} characters, appending "…" if truncated.
     */
    public static String truncate(String s, int maxLen) {
        if (s == null) return "";
        return s.length() <= maxLen ? s : s.substring(0, maxLen - 1) + "…";
    }

    /**
     * Repeats {@code ch} {@code n} times — useful for table/border rendering.
     */
    public static String repeat(char ch, int n) {
        return String.valueOf(ch).repeat(Math.max(0, n));
    }

    /**
     * Pads {@code s} with trailing spaces to exactly {@code width} characters.
     */
    public static String padRight(String s, int width) {
        return String.format("%-" + width + "s", s == null ? "" : s);
    }
}
