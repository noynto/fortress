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
    // https://mvnrepository.com/artifact/org.mockito/mockito-core
    testImplementation(libs.mockito)
    // https://mvnrepository.com/artifact/tools.jackson.core/jackson-databind
    implementation(libs.jackson.databind)
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