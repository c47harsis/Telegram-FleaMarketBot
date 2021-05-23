package ru.c47harsis.config

import mu.KotlinLogging
import java.util.*

private val log = KotlinLogging.logger {}

enum class Config(var value: String? = null, val required: Boolean = true) {
    BOT_TOKEN,
    BOT_USERNAME;
//    DB_HOST,
//    DB_PORT,
//    DB_NAME,
//    DB_USERNAME,
//    DB_PASSWORD;

    companion object {
        private const val CONFIG_FILE = "settings.yml"

        init {
            val configReader = ConfigReader(CONFIG_FILE)
            for (property in values()) {
                var value = System.getenv(property.name)
                if (value != null) {
                    property.value = value
                    log.debug { "Property ${property.name} set to ${property.value} from environment" }
                    continue
                }
                val propertyKey: String = property.name.lowercase(Locale.ROOT).replace('_', '.')
                value = configReader.readValue(propertyKey)
                if (value != null) {
                    property.value = value
                    log.debug { "Property ${property.name} set to ${property.value} from $CONFIG_FILE" }
                    continue
                }
                if (property.value == null && property.required) {
                    val ex = IllegalStateException("Invalid config: can not fill required property ${property.name}")
                    log.error { ex.message }
                    throw ex
                }
            }
        }
    }
}