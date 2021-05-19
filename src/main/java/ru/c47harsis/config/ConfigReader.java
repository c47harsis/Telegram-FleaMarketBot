package ru.c47harsis.config;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import ru.c47harsis.fs.ResourceReader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class ConfigReader extends ResourceReader {
    public ConfigReader(String filename) {
        super(filename);
    }

    public String readValue(String key) {
        JsonNode value = getValue(key);
        if (value == null) {
            log.info("Can not find key {} in {}", key, getFilename());
            return null;
        }
        if (!value.isValueNode()) {
            log.error("Value of {} is not plain", key);
            return null;
        }
        return value.asText();
    }

    public List<String> readValues(String key) {
        JsonNode value = getValue(key);
        if (value == null) {
            log.error("Invalid key: {}", key);
            return null;
        }
        if (!value.isArray()) {
            log.error("Value of {} is not an array", key);
            return null;
        }
        List<String> result = new ArrayList<>(value.size());
        Iterator<JsonNode> iter = value.elements();
        int id = 0;
        while (iter.hasNext()) {
            JsonNode node = iter.next();
            id++;
            if (!node.isValueNode()) {
                log.error("Value of {} with index {} is not plain", key, id);
                return null;
            }
            result.add(node.asText());
        }
        return result;
    }

    private JsonNode getValue(String key) {
        JsonNode data = getData();
        String[] split = key.split("\\.");
        int i = 0;
        while (data != null && i < split.length) {
            data = data.get(split[i]);
            i++;
        }
        return data;
    }
}