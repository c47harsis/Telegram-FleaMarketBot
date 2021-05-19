package ru.c47harsis.fs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum FileType {
    YAML("yaml", "yml"),
    JSON("json");

    private final static Pattern PATTERN = Pattern.compile(".*\\.(.+)$");
    private final String[] extensions;

    FileType(String... extensions) {
        this.extensions = extensions;
    }

    public static FileType of(String filename) {
        String extension = getExtension(filename);
        if (extension == null) {
            return null;
        }
        for (FileType type : FileType.values()) {
            for (String ext : type.extensions) {
                if (ext.equalsIgnoreCase(extension)) {
                    return type;
                }
            }
        }
        return null;
    }

    private static String getExtension(String filename) {
        Matcher matcher = PATTERN.matcher(filename);
        if (!matcher.matches()) {
            return null;
        }
        return matcher.group(1);
    }
}
