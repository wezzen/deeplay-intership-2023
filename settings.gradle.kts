rootProject.name = "deeplay"

include("client")
project(":client").projectDir = file("modules/client")

include("server")
project(":server").projectDir = file("modules/server")

include("game")
project(":game").projectDir = file("modules/game")

include("terminal-ui")
project(":terminal-ui").projectDir = file("modules/user-interface/terminal-ui")

include("model")
project(":model").projectDir = file("modules/model")

include("validation")
project(":validation").projectDir = file("modules/validation")

include("logger")
project(":logger").projectDir = file("modules/logger")
