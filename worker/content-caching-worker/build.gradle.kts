dependencies {
    implementation(project(":common"))
    implementation(project(":domain"))

    implementation(project(":usecase:post-resolving-helper-usecase"))

    implementation(project(":adapter:kafka"))
    implementation(project(":adapter:mysql"))
    implementation(project(":adapter:redis"))
    implementation(project(":adapter:metadata-client"))
}
