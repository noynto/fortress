plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api
    testImplementation(libs.junit.jupiter)
    // https://mvnrepository.com/artifact/org.junit.platform/junit-platform-launcher
    testImplementation(libs.junit.platform)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "me.noynto.fortress.finance.App"
}

tasks.test {
    useJUnitPlatform()
}