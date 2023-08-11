plugins {
    id("java")
    id("application")
}

dependencies {
    implementation(project(mapOf("path" to ":game")))
}
