package ru.c47harsis

import com.google.common.collect.Lists
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ru.c47harsis.config.ConfigReader

private const val VALID_YAML = "valid_settings.yml"

class ConfigReaderTest {
    private var reader: ConfigReader = ConfigReader(VALID_YAML)

    @Suppress("SameParameterValue")
    private fun setup(filename: String) {
        reader = ConfigReader(filename)
    }

    @Test
    fun `Should read all values correctly from valid yaml file`() {
        setup(VALID_YAML)
        Assertions.assertEquals("testSingle", reader.readValue("single"))
        Assertions.assertEquals("testComplexAdditionalOneField1", reader.readValue("complex.additional.field1"))
        Assertions.assertEquals("testComplexAdditionalOneField2", reader.readValue("complex.additional.field2"))
        Assertions.assertEquals(
            Lists.newArrayList("testComplexMultiple0", "testComplexMultiple1"),
            reader.readValues("complex.multiple")
        )
    }

    @Test
    fun `Should return null if trying to read value of undefined key`() {
        setup(VALID_YAML)
        Assertions.assertNull(reader.readValue("undefined"))
        Assertions.assertNull(reader.readValues("undefined.key"))
    }

    @Test
    fun `Should return null if trying to read plain value as multiple`() {
        setup(VALID_YAML)
        Assertions.assertNull(reader.readValues("single"))
    }

    @Test
    fun `Should return null if trying to read multiple values as plain`() {
        setup(VALID_YAML)
        Assertions.assertNull(reader.readValue("complex.multiple"))
    }
}