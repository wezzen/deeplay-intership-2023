plugins {
    id("net.ltgt.errorprone") version "3.1.0"
}

allprojects {
    apply(plugin = "net.ltgt.errorprone")
    dependencies {
        errorprone("com.google.errorprone:error_prone_core:2.20.0")
    }
}

repositories {
    mavenCentral()
}
