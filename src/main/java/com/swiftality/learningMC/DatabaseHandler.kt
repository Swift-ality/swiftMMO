package com.swiftality.learningMC

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.io.File
import java.sql.Connection

class DatabaseHandler(databaseFile: File) {

    private val hikari = HikariDataSource(
        HikariConfig().apply {
            jdbcUrl = "jdbc:sqlite:${databaseFile.absolutePath}"
            maximumPoolSize = 10
        }
    )

    fun createTables() {
        hikari.connection.use { connection ->
            connection.createStatement().use {
                it.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS player_stats (
                        uuid TEXT PRIMARY KEY,
                        intelligence INTEGER DEFAULT 0,
                        strength INTEGER DEFAULT 0,
                        agility INTEGER DEFAULT 0
                    )
                """.trimIndent())
            }
        }
    }

    fun close() {
        hikari.close()
    }
}