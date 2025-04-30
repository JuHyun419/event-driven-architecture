dependencies {
    implementation(project(":common"))
    implementation(project(":domain"))

    implementation(project(":usecase"))
    implementation(project(":usecase:core"))
    implementation(project(":usecase:post-inspected-usecase"))

    implementation(project(":adapter:kafka"))
    implementation(project(":adapter:metadata-client"))
    implementation(project(":adapter:chat-gpt-client"))
}
