plugins {
    id("java")
    id("application")
}

dependencies {

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testImplementation("junit:junit:4.13.1")
}

tasks.test {
    useJUnitPlatform()
}