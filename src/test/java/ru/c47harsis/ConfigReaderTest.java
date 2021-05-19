package ru.c47harsis;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.c47harsis.config.ConfigReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ConfigReaderTest {
    private static final String VALID_YAML = "valid_settings.yml";

    private ConfigReader reader;

    private void setup(String filename) {
        reader = new ConfigReader(filename);
    }

    @Test
    @DisplayName("Should read all values correctly from valid *.yml file")
    void testValidYaml() {
        setup(VALID_YAML);
        assertEquals("testSingle", reader.readValue("single"));
        assertEquals("testComplexAdditionalOneField1", reader.readValue("complex.additional.field1"));
        assertEquals("testComplexAdditionalOneField2", reader.readValue("complex.additional.field2"));
        assertEquals(Lists.newArrayList("testComplexMultiple0", "testComplexMultiple1"), reader.readValues("complex.multiple"));
    }

    @Test
    @DisplayName("Should return null if trying to read value of undefined key")
    void testUndefinedKey() {
        setup(VALID_YAML);
        assertNull(reader.readValue("undefined"));
        assertNull(reader.readValues("undefined.key"));
    }

    @Test
    @DisplayName("Should return null if trying to read plain value as multiple")
    void testPlainValue() {
        setup(VALID_YAML);
        assertNull(reader.readValues("single"));
    }

    @Test
    @DisplayName("Should return null if trying to read multiple values as plain")
    void testMultipleValue() {
        setup(VALID_YAML);
        assertNull(reader.readValue("complex.multiple"));
    }
}
