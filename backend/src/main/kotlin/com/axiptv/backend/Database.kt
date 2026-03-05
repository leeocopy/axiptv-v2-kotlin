package com.axiptv.backend

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone
import org.jetbrains.exposed.sql.transactions.transaction

object DevicesTable : Table("devices") {
    val id = long("id").autoIncrement()
    val deviceHash = text("device_hash").uniqueIndex()
    val firstSeenAt = timestampWithTimeZone("first_seen_at")
    val lastSeenAt = timestampWithTimeZone("last_seen_at")
    val trialEndAt = timestampWithTimeZone("trial_end_at")
    val isActive = bool("is_active")
    val activeUntil = timestampWithTimeZone("active_until").nullable()

    override val primaryKey = PrimaryKey(id)
}

fun initDatabase() {
    val config = HikariConfig().apply {
        jdbcUrl = System.getenv("JDBC_URL") ?: "jdbc:postgresql://localhost:5432/axiptv"
        driverClassName = "org.postgresql.Driver"
        username = System.getenv("DB_USER") ?: "user"
        password = System.getenv("DB_PASSWORD") ?: "password"
        maximumPoolSize = 10
    }
    val dataSource = HikariDataSource(config)
    
    // Flyway
    Flyway.configure()
        .dataSource(dataSource)
        .load()
        .migrate()

    Database.connect(dataSource)
}

suspend fun <T> dbQuery(block: suspend () -> T): T =
    transaction {
        // Exposed transaction
        kotlinx.coroutines.runBlocking { block() }
    }
