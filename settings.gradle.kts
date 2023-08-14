rootProject.name = "deeplay"

include("client")
project(":client").projectDir = file("modules/client")

include("server")
project(":server").projectDir = file("modules/server")

include("game")
project(":game").projectDir = file("modules/game")

include("terminal-ui")
project(":terminal-ui").projectDir = file("modules/terminal-ui")

include("random-bot")
project(":random-bot").projectDir = file("modules/bots/random-bot")
