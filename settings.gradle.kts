rootProject.name = "deeplay"

include("client")
project(":client").projectDir = file("modules/client/client")

include("user-interface")
project(":user-interface").projectDir = file("modules/client/ui/user-interface")

include("gui")
project(":gui").projectDir = file("modules/client/ui/gui")

include("terminal-ui")
project(":terminal-ui").projectDir = file("modules/client/ui/terminal-ui")

include("decision-maker")
project(":decision-maker").projectDir = file("modules/client/decision-maker/decision-maker")

include("decision-maker-gui")
project(":decision-maker-gui").projectDir = file("modules/client/decision-maker/decision-maker-gui")

include("decision-maker-terminal")
project(":decision-maker-terminal").projectDir = file("modules/client/decision-maker/decision-maker-terminal")

include("server")
project(":server").projectDir = file("modules/server/server")

include("service")
project(":service").projectDir = file("modules/server/service")

include("dao")
project(":dao").projectDir = file("modules/server/dao")

include("aggregator")
project(":aggregator").projectDir = file("modules/server/aggregator")

include("game")
project(":game").projectDir = file("modules/core/game")

include("model")
project(":model").projectDir = file("modules/core/model")

include("logger")
project(":logger").projectDir = file("modules/utils/logger")

include("dto")
project(":dto").projectDir = file("modules/utils/dto")

include("socket-io")
project(":socket-io").projectDir = file("modules/utils/socket-io")

include("json-converter")
project(":json-converter").projectDir = file("modules/utils/json-converter")

include("random-bot")
project(":random-bot").projectDir = file("modules/bots/random-bot")

include("validation")
project(":validation").projectDir = file("modules/validators/validation")

include("dto-validator")
project(":dto-validator").projectDir = file("modules/validators/dto-validator")

include("selfplay")
project(":selfplay").projectDir = file("modules/selfplay")

include("game-exception")
project(":game-exception").projectDir = file("modules/exceptions/game-exception")

include("client-exception")
project(":client-exception").projectDir = file("modules/exceptions/client-exception")

include("server-exception")
project(":server-exception").projectDir = file("modules/exceptions/server-exception")
