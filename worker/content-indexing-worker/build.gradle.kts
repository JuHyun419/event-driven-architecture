dependencies {
    implementation(project(":common"))
    implementation(project(":domain"))

    implementation(project(":usecase:core"))
    implementation(project(":usecase:indexing-post-usecase"))

    implementation(project(":adapter:kafka"))
    implementation(project(":adapter:elasticsearch"))
}
