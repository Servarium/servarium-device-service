import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
	id("org.springframework.boot")
}

dependencies {
	implementation(project(":core"))
	implementation(project(":output-port-adapter-postgre"))
	implementation(project(":output-port-adapter-in-memory"))
	implementation(project(":input-port-adapter-rest-api"))

	implementation("org.springframework.boot:spring-boot-starter")
}


tasks {
	named<BootJar>("bootJar") {
		layered {
			enabled = true
		}
		archiveFileName.set("servarium.jar")
		mainClass.set("app.servarium.ServariumApplication")
	}

	named<Jar>("jar") {
		enabled = false
	}

	named("build") {
		dependsOn("bootJar")
	}
}