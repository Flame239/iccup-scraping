plugins {
    kotlin("jvm") version "2.0.10"
}

group = "com.flame239"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("it.skrape:skrapeit:1.2.2")
    implementation("me.tongfei:progressbar:0.10.1")
    implementation("com.clickhouse:client-v2:0.6.5")
    implementation("org.lz4:lz4-java:1.8.0")
    implementation("org.slf4j:slf4j-simple:2.0.16")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
