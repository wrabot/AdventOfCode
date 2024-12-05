plugins {
    kotlin("jvm") version "2.0.20"
    kotlin("plugin.serialization") version "2.0.20"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("wrabot.competitive:CompetitiveTools:0.44")
    // to test new CompetitiveTools
    //testImplementation("wrabot.competitive:CompetitiveTools") { version { branch = "main" } }
    testImplementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test:2.0.20")
}
