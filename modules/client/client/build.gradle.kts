dependencies {
    implementation(project(":dto"))
    implementation(project(":model"))
    implementation(project(":validation"))

    implementation(project(":user-interface"))
    implementation(project(":gui"))
    implementation(project(":terminal-ui"))

    implementation(project(":decision-maker"))
    implementation(project(":decision-maker-terminal"))
    implementation(project(":decision-maker-gui"))

    implementation(project(":socket-io"))
    implementation(project(":client-exception"))
}
