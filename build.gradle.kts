plugins {
    id("java")
    id("net.ltgt.errorprone") version "3.1.0"
    id("jacoco")
}

allprojects {
    apply(plugin = "net.ltgt.errorprone")
    apply(plugin = "jacoco")
    apply(plugin = "java")

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    dependencies {
        errorprone("com.google.errorprone:error_prone_core:2.20.0")

        testImplementation(platform("org.junit:junit-bom:5.9.3"))
        testImplementation("org.junit.jupiter:junit-jupiter")
        testImplementation("org.mockito:mockito-core:5.3.1")
    }

    repositories {
        mavenCentral()
    }

    tasks.test {
        useJUnitPlatform()
        finalizedBy(tasks.jacocoTestReport)
    }

    tasks.jacocoTestReport {
        dependsOn(tasks.test)
        reports {
            xml.required.set(true)
            csv.required.set(false)
            html.required.set(false)
        }
    }
}


