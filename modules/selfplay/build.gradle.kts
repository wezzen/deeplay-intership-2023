dependencies {
    implementation(project(":dto"))
    implementation(project(":game"))
    implementation(project(":model"))

    implementation(project(":decision-maker"))
    implementation(project(":decision-maker-gui"))
    implementation(project(":random-bot"))
    implementation(project(":minimax-bot-gleb"))

    implementation(project(":user-interface"))
    implementation(project(":terminal-ui"))
    implementation(project(":gui"))

    implementation(project(":logger"))
    implementation(project(":client"))
    implementation(project(":socket-io"))

    implementation(project(":game-exception"))
    implementation(project(":server-exception"))
}
