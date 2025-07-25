dependencies {
    implementation("org.springframework.boot:spring-boot-starter:3.4.3")
    implementation("org.springframework.boot:spring-boot-starter-web:3.4.3")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

    implementation(project(":common"))
    implementation(project(":domain"))

    implementation(project(":usecase:core"))
    implementation(project(":usecase:post-inspected-usecase"))
    implementation(project(":usecase:subscribing-post-usecase"))
    implementation(project(":usecase:post-usecase"))
    implementation(project(":usecase:indexing-post-usecase"))

    implementation(project(":adapter:mysql"))
    implementation(project(":adapter:mongodb"))
    implementation(project(":adapter:redis"))
    implementation(project(":adapter:kafka"))
    implementation(project(":adapter:elasticsearch"))
    implementation(project(":adapter:chat-gpt-client"))
}
