package ru.c47harsis.config

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import mu.KotlinLogging
import java.net.URL

private val log = KotlinLogging.logger {}
private val mappersByType: Map<FileType, ObjectMapper> = mapOf(
    FileType.JSON to ObjectMapper(),
    FileType.YAML to ObjectMapper(YAMLFactory())
)

class ConfigReader(filename: String) {

    private val fileUrl: URL = this::class.java.classLoader.getResource(filename) ?: run {
        val message = "File with name $filename not found"
        log.error(message)
        throw IllegalArgumentException(message)
    }

    private val data: JsonNode = run {
        val type = FileType.of(filename)

        val mapper = mappersByType[type] ?: run {
            val message = "Unsupported type of file $fileUrl"
            log.error { message }
            throw IllegalArgumentException(message)
        }

        mapper.readTree(fileUrl) ?: run {
            val message = "Invalid format of file $fileUrl"
            log.error { message }
            throw IllegalArgumentException(message)
        }
    }

    fun readValue(key: String): String? {
        val value = getValue(key) ?: run {
            log.info("Can not find key $key in $fileUrl")
            return null
        }

        if (!value.isValueNode) {
            log.error("Value of $key is not plain")
            return null
        }

        return value.asText()
    }

    fun readValues(key: String): List<String>? {
        val value = getValue(key) ?: run {
            log.error("Invalid key: $key")
            return null
        }

        if (!value.isArray) {
            log.error("Value of $key is not an array")
            return null
        }

        val result: MutableList<String> = ArrayList(value.size())
        for ((id, node) in value.elements().withIndex()) {
            if (!node.isValueNode) {
                log.error { "Value of $key with index $id is not plain" }
                return null
            }
            result.add(node.asText())
        }

        return result
    }

    private fun getValue(keys: String): JsonNode? {
        var value: JsonNode = data
        keys.split(".").forEach { key ->
            value = value[key] ?: return null
        }
        return value
    }
}