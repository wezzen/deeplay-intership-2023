rootProject.name = "deeplay"

include("client")
project(":client").projectDir = file("modules/client")

include("server")
project(":server").projectDir = file("modules/server")

include("game-service")
project(":game-service").projectDir = file("modules/game-service")

include("terminal-ui")
project(":terminal-ui").projectDir = file("modules/terminal-ui")
