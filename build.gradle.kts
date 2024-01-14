plugins {
    kotlin("jvm") version "1.9.20"
    kotlin("plugin.serialization") version "1.9.20"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("wrabot.competitive:CompetitiveTools:0.6")
    // to test new CompetitiveTools
    //implementation("wrabot.competitive:CompetitiveTools") { version { branch = "main" } }
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
}

application {
    mainClass.set("MainKt")
}
