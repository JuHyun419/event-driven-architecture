subprojects {
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter:3.4.3")

        implementation(project(":common"))
        implementation(project(":domain"))
    }
}
