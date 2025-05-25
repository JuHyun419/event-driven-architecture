plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "eda"

include("api")
include("common")
include("domain")

include("usecase")
include("usecase:core")
include("usecase:post-usecase")
include("usecase:post-resolving-helper-usecase")
include("usecase:post-inspected-usecase")
include("usecase:subscribing-post-usecase")
include("usecase:indexing-post-usecase")

include("adapter")
include("adapter:metadata-client")
include("adapter:mysql")
include("adapter:kafka")
include("adapter:chat-gpt-client")
include("adapter:mongodb")
include("adapter:redis")
include("adapter:elasticsearch")

include("worker")
include("worker:auto-inspection-worker")
include("worker:content-subscribing-worker")
include("worker:content-caching-worker")
include("worker:content-indexing-worker")
