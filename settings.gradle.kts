rootProject.name = "deeplay"

include("client")
project(":client").projectDir = file("modules/client")

include("server")
project(":server").projectDir = file("modules/server")

include("game")
project(":game").projectDir = file("modules/game")

include("terminal-ui")
project(":terminal-ui").projectDir = file("modules/ui/terminal-ui")

include("model")
project(":model").projectDir = file("modules/model")

include("validation")
project(":validation").projectDir = file("modules/validators/validation")

include("logger")
project(":logger").projectDir = file("modules/logger")

include("random-bot")
project(":random-bot").projectDir = file("modules/bots/random-bot")

include("service")
project(":service").projectDir = file("modules/service")

include("dto")
project(":dto").projectDir = file("modules/dto")

include("server-exception")
project(":server-exception").projectDir = file("modules/exceptions/server-exception")

include("dto-validator")
project(":dto-validator").projectDir = file("modules/validators/dto-validator")

include("json-converter")
project(":json-converter").projectDir = file("modules/json-converter")

include("sandbox")
project(":sandbox").projectDir = file("modules/sandbox")

include("game-exception")
project(":game-exception").projectDir = file("modules/exceptions/game-exception")

include("decision-maker")
project(":decision-maker").projectDir = file("modules/decision-maker/decision-maker")

include("user-interface")
project(":user-interface").projectDir = file("modules/ui/user-interface")

include("decision-maker-terminal")
project(":decision-maker-terminal").projectDir = file("modules/decision-maker/decision-maker-terminal")

include("client-exception")
project(":client-exception").projectDir = file("modules/exceptions/client-exception")

include("gui")
project(":gui").projectDir = file("modules/ui/gui")

include("decision-maker-gui")
project(":decision-maker-gui").projectDir = file("modules/decision-maker/decision-maker-gui")
