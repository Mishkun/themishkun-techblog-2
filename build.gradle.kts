plugins {
    kotlin("jvm") version "1.9.20"
    id("io.gitlab.arturbosch.detekt") version "1.23.3"
    application
}

group = "xyz.mishkun"
version = "1.0-SNAPSHOT"

application {
    mainClass = "xyz.mishkun.MainKt"
}

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

tasks.named<JavaExec>("run") {
    inputs.dir("blog").withPropertyName("blogSourceDir")
    outputs.dir("docs").withPropertyName("outputDir")
    args("blog", "docs")
}

tasks.clean.configure {
    doLast {
        file("docs").deleteRecursively()
    }
}