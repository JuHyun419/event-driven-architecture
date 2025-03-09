subprojects {
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter:3.4.3")
        implementation("io.github.oshai:kotlin-logging-jvm:5.1.0")

        implementation(project(":common"))
        implementation(project(":domain"))
        implementation(project(":usecase:core"))
    }
}
