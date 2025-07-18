dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.4.3")
    implementation("mysql:mysql-connector-java:8.0.33")
}

plugins {
    kotlin("plugin.jpa") version "1.9.20"
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}
