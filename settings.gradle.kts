rootProject.name = "servarium-device-service"

include(
    "domain",
    "core",
    "app-spring-boot",
    "output-port-adapter-postgre",
    "output-port-adapter-in-memory",
    "input-port-adapter-rest-api"
)