plugins {
    id("java")
    id("application")
}

dependencies {
    implementation(project(":game"))
    implementation(project(":terminal-ui"))
}