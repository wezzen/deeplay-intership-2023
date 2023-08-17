dependencies {
    implementation(project(":dto"))
    implementation(project(":model"))
    implementation(project(":service"))
    implementation(project(":server-exception"))
    implementation("com.fasterxml.jackson.core:jackson-core:2.15.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.15.2")
}