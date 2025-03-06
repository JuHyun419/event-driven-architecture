dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.4.3")

    implementation(project(":usecase:core"))
    implementation(project(":usecase:post-resolving-helper-usecase"))

}
