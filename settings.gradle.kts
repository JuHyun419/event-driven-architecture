plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "eda"

include("adapter")
include("api")
include("common")
include("domain")
include("usecase")
include("usecase:post-usecase")
