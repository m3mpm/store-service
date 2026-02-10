plugins {
	java
	id("org.springframework.boot") version "4.0.1"
	id("io.spring.dependency-management") version "1.1.7"
}

// Версии зависимостей
val mapstructVersion = "1.6.3"
val springDocVersion = "2.8.0"
val lombokMapstructBindingVersion = "0.2.0"
val dotenvVersion = "5.1.0"
val uuidCreatorVersion = "6.1.1"

group = "org.m3mpm"
version = "0.0.1-SNAPSHOT"
description = "Store Service for Spring Boot"

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
}

dependencies {
    // Web и Data
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // Миграции БД
    implementation("org.springframework.boot:spring-boot-starter-liquibase")
    runtimeOnly("org.postgresql:postgresql")

    // Swagger / OpenAPI
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springDocVersion")

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // MapStruct
    implementation("org.mapstruct:mapstruct:$mapstructVersion")
    annotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")

    // Связка Lombok и MapStruct (чтобы MapStruct видел геттеры/сеттеры Lombok)
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:$lombokMapstructBindingVersion")

    // DevTools
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // Тесты
    testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Добавлено для Lombok в тестах
    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")

    // env file
    // Source: https://mvnrepository.com/artifact/me.paulschwarz/springboot4-dotenv
    implementation("me.paulschwarz:springboot4-dotenv:$dotenvVersion")

    // not work
    // Source: https://mvnrepository.com/artifact/io.github.cdimascio/dotenv-java
    //    implementation("io.github.cdimascio:dotenv-java:3.2.0")

    // not work
    // Source: https://mvnrepository.com/artifact/io.github.cdimascio/dotenv-kotlin
    //    runtimeOnly("io.github.cdimascio:dotenv-kotlin:6.5.1")

    // to create uuid v.7 for tests
    // Source: https://mvnrepository.com/artifact/com.github.f4b6a3/uuid-creator
    implementation("com.github.f4b6a3:uuid-creator:$uuidCreatorVersion")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.compilerArgs.addAll(listOf(
//        "-Xlint:all,-processing" // Включает все проверки компилятора на чистоту кода

    ))
}

