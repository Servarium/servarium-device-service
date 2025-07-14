plugins {
	java
	id("org.springframework.boot") version "3.5.3" apply false
	id("io.spring.dependency-management") version "1.1.7"
}

subprojects {
	group = "app.servarium"

	apply(plugin = "java")
	apply(plugin = "io.spring.dependency-management")

	repositories {
		mavenCentral()
	}

	java {
		toolchain {
			languageVersion = JavaLanguageVersion.of(17)
		}
	}

	configurations {
		compileOnly {
			extendsFrom(configurations.annotationProcessor.get())
		}
	}

	dependencyManagement {
		imports {
			mavenBom("org.springframework.boot:spring-boot-dependencies:3.5.3")
		}

		dependencies {
			dependencySet("org.mapstruct:1.5.5.Final") {
				entry("mapstruct")
				entry("mapstruct-processor")
			}

			dependency("org.projectlombok:lombok-mapstruct-binding:0.2.0")

//			dependency("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.9")
		}
	}

	dependencies {
		annotationProcessor("org.projectlombok:lombok")

		compileOnly("org.projectlombok:lombok")
	}
}
