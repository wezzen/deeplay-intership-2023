plugins {
    id("java")
    id("application")
}

repositories {
    mavenCentral()
}

dependencies {

    implementation("org.apache.groovy:groovy:4.0.12")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}