import nu.studer.gradle.jooq.JooqGenerate

plugins {
    id("nu.studer.jooq")
}

dependencies {
    implementation(project(":domain"))

    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.liquibase:liquibase-core")

    implementation("org.mapstruct:mapstruct")
    implementation("org.projectlombok:lombok-mapstruct-binding")

    annotationProcessor("org.mapstruct:mapstruct-processor")

    runtimeOnly("org.postgresql:postgresql")
    jooqGenerator("org.postgresql:postgresql")
}

jooq {
    version.set("3.18.31")

    configurations {
        create("main") {
            generateSchemaSourceOnCompilation.set(false)

            jooqConfiguration.apply {
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = "jdbc:postgresql://localhost:5432/device"
                    username = "local"
                    password = "123456"
                }

                generator.apply {
                    name = "org.jooq.codegen.DefaultGenerator"
                    database.apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        inputSchema = "public"
                        includes = ".*"
                        excludes = "databasechangelog.*"
                    }

                    target.apply {
                        packageName = "app.servarium.adapter.postgre.jooq.generated"
                        directory = "src/main/generated"
                    }
                }
            }
        }
    }
}

sourceSets {
    main {
        java.srcDir("src/main/generated")
    }
}

tasks {
    named<JooqGenerate>("generateJooq") {}
}