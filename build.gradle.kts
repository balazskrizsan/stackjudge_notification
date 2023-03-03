plugins {
    kotlin("jvm") version "1.7.20"
    kotlin("plugin.allopen") version "1.7.20"
    id("io.quarkus")
    id("java-library")
    id("net.afanasev.sekret") version "0.1.2"
    id("maven-publish")
    id("java")
}

repositories {
    mavenCentral()
    mavenLocal()
    maven {
        url = uri("https://dl.cloudsmith.io/public/stackjudge/simple_oidc_java_maven/maven/")
        credentials {
            username = "krizsan-balazs"
            password = "ffe61187c0230469f7120be055ed0f528305988e"
        }
    }
}

allprojects {
    repositories {
        maven("https://jitpack.io")
    }
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    implementation("io.quarkus:quarkus-kotlin:2.16.3.Final")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.5.31")
    implementation("io.quarkus:quarkus-arc:2.16.3.Final")
    implementation("org.projectlombok:lombok:1.18.20")
    testImplementation("io.quarkus:quarkus-junit5:2.16.3.Final")
    testImplementation("io.rest-assured:rest-assured")
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))

    // custom
    implementation("com.kbalazsworks:simple_oidc_java_maven:1.0.1")
    // https://mvnrepository.com/artifact/org.eclipse.microprofile.rest.client/microprofile-rest-client-api
    implementation("org.eclipse.microprofile.rest.client:microprofile-rest-client-api:3.0")
    // https://mvnrepository.com/artifact/io.quarkus/quarkus-resteasy-reactive
    implementation("io.quarkus:quarkus-resteasy-reactive:2.16.3.Final")
    // https://mvnrepository.com/artifact/io.quarkus/quarkus-resteasy-reactive-qute
    implementation("io.quarkus:quarkus-resteasy-reactive-qute:2.16.3.Final")
    // https://mvnrepository.com/artifact/io.quarkus/quarkus-smallrye-openapi
    implementation("io.quarkus:quarkus-smallrye-openapi:2.16.3.Final")
    // https://mvnrepository.com/artifact/javax.xml.bind/jaxb-api
    implementation("javax.xml.bind:jaxb-api:2.3.0")
    // https://mvnrepository.com/artifact/io.quarkus/quarkus-resteasy-reactive-jsonb
    implementation("io.quarkus:quarkus-resteasy-reactive-jsonb:2.16.3.Final")
    // https://mvnrepository.com/artifact/io.github.microutils/kotlin-logging-jvm
    runtimeOnly("io.github.microutils:kotlin-logging-jvm:2.1.21")
    // https://mvnrepository.com/artifact/org.assertj/assertj-core
    testImplementation("org.assertj:assertj-core:3.22.0")
    // https://mvnrepository.com/artifact/org.mockito.kotlin/mockito-kotlin
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    // https://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient
    implementation("org.apache.httpcomponents:httpclient:4.5.13")
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.module/jackson-module-kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.2")
}

group = "com.kbalazsworks"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

allOpen {
    annotation("javax.ws.rs.Path")
    annotation("javax.enterprise.context.ApplicationScoped")
    annotation("io.quarkus.test.junit.QuarkusTest")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
    kotlinOptions.javaParameters = true
}
