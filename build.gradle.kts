plugins {
    java
    id("org.springframework.boot") version "3.4.8-SNAPSHOT"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "toy.test"
version = "1.0.3-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/snapshot") }
}

// core
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("io.netty:netty-resolver-dns-native-macos:4.1.110.Final:osx-aarch_64")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    val querydslVersion = "5.1.0"
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")
    implementation("com.querydsl:querydsl-jpa:${querydslVersion}:jakarta")
    annotationProcessor("com.querydsl:querydsl-apt:${querydslVersion}:jakarta")
    runtimeOnly("com.h2database:h2")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")
}

// docs
dependencies {
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.6")
}

// test
dependencies {
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.assertj:assertj-core:3.27.3")

    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
