plugins {
    kotlin("jvm") version "1.9.20"
}

group = "xyz.mishkun"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains:markdown:0.5.0")
    implementation("com.github.ajalt.clikt:clikt:4.2.1")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.9.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.0")
    testImplementation("org.xmlunit:xmlunit-matchers:2.8.2")
    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation("org.hamcrest:java-hamcrest:2.0.0.0")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}