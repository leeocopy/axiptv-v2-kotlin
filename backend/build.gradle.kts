plugins {
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.serialization") version "1.9.22"
    application
}

group = "com.axiptv"
version = "1.0.0"

repositories {
    mavenCentral()
}

val ktor_version = "2.3.7"
val exposed_version = "0.47.0"
val logback_version = "1.4.14"

dependencies {
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    
    // DB
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:$exposed_version")
    implementation("org.postgresql:postgresql:42.7.1")
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("org.flywaydb:flyway-core:10.4.1")
    implementation("org.flywaydb:flyway-database-postgresql:10.4.1")

    // Time
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")

    // Logging
    implementation("ch.qos.logback:logback-classic:$logback_version")

    // Test
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$1.9.22")
}

application {
    mainClass.set("com.axiptv.backend.ApplicationKt")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}
