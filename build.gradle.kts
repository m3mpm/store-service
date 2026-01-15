plugins {
	java
	id("org.springframework.boot") version "4.0.1"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "org.m3mpm"
version = "0.0.1-SNAPSHOT"
description = "Store Service for Spring Boot"

val mapstructVersion = "1.6.3"

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
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.0")

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // MapStruct
    implementation("org.mapstruct:mapstruct:$mapstructVersion")
    annotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")

    // Связка Lombok и MapStruct (чтобы MapStruct видел геттеры/сеттеры Lombok)
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")

    // DevTools
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // Тесты
    testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Добавлено для Lombok в тестах
    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.compilerArgs.addAll(listOf(
        /*Генерирует мапперы как Spring-бины (@Component), чтобы их можно было внедрять через @Autowired
         Если убрать эту строчку, то при создании мапперов придется в каждом интерфейсе вручную писать:
         @Mapper(componentModel = "spring").*/
        "-Amapstruct.defaultComponentModel=spring",

        // Прерывает сборку, если есть несопоставленные поля — предотвращает "тихие" ошибки маппинга
        "-Amapstruct.unmappedTargetPolicy=ERROR",

//        "-Xlint:all,-processing"

    ))
}

