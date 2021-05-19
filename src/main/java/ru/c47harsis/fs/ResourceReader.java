package ru.c47harsis.fs;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;
import java.util.EnumMap;
import java.util.Map;

@Slf4j
public abstract class ResourceReader {
    private static final Map<FileType, ObjectMapper> mappersByType = new EnumMap<>(FileType.class);

    static {
        mappersByType.put(FileType.JSON, new ObjectMapper());
        mappersByType.put(FileType.YAML, new ObjectMapper(new YAMLFactory()));
    }

    private final URL fileUrl;
    protected JsonNode data;

    public ResourceReader(String filename) {
        this.fileUrl = Thread.currentThread().getContextClassLoader().getResource(filename);
        if (this.fileUrl == null) {
            log.error("File with name {} not found", filename);
            throw new IllegalArgumentException("File with name " + filename + " not found");
        }
    }

    protected JsonNode getData() {
        if (data != null) {
            return data;
        }
        FileType type = FileType.of(fileUrl.getFile());
        ObjectMapper objectMapper = mappersByType.get(type);
        try {
            data = objectMapper.readTree(fileUrl);
        } catch (IOException e) {
            log.error("Invalid format of file {}", fileUrl.getFile());
        }
        return data;
    }

    public String getFilename() {
        return fileUrl.getFile();
    }
}
