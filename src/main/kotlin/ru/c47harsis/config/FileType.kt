package ru.c47harsis.config

enum class FileType(vararg val extensions: String) {
    YAML("yaml", "yml"), JSON("json");

    companion object {
        private val pattern = ".*\\.(.+)$".toRegex()

        fun of(filename: String): FileType? {
            val extension = getExtension(filename) ?: return null
            return values().find {
                it.extensions.any { ext ->
                    ext.equals(extension, ignoreCase = true)
                }
            }
        }

        private fun getExtension(filename: String) = pattern.find(filename)?.groups?.get(1)?.value
    }
}