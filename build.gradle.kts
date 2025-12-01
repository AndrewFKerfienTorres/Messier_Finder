plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "csc380.Team2"
version = "1.0"

repositories {
    mavenCentral()
}

application {
    mainClass.set("application.HomeGUI")
}


tasks.jar {
    manifest {
        attributes["Main-Class"] = "application.HomeGUI"
    }
}

javafx {
    version = "25"
    modules("javafx.controls", "javafx.fxml", "javafx.graphics")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}