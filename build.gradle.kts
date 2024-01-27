plugins {
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.serialization") version "1.9.22"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("wrabot.competitive:CompetitiveTools:0.12")
    // to test new CompetitiveTools
    //testImplementation("wrabot.competitive:CompetitiveTools") { version { branch = "main" } }
    testImplementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.9.22")
}
