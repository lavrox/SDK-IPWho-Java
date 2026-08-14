plugins {
    `java-library`
    id("com.vanniktech.maven.publish") version "0.34.0"
}

group = "org.lavrox"
version = "1.0.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    api("com.squareup.okhttp3:okhttp:4.12.0")
    api("com.fasterxml.jackson.core:jackson-databind:2.17.0")
}

val signingKeyFile = file("${System.getProperty("user.home")}/.gnupg/ipwho-private.asc")
if (signingKeyFile.isFile) {
    extra["signingInMemoryKey"] = signingKeyFile.readText()
}
val signingPass = findProperty("signing.password") as String?
if (!signingPass.isNullOrBlank() && findProperty("signingInMemoryKeyPassword") == null) {
    extra["signingInMemoryKeyPassword"] = signingPass
}

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()
    coordinates("org.lavrox", "ipwho", "1.0.0")
    pom {
        name.set("IPWho Java SDK")
        description.set("Official Java client for the IPWho IP geolocation API.")
        inceptionYear.set("2026")
        url.set("https://github.com/lavrox/SDK-IPWho-Java")
        licenses {
            license {
                name.set("MIT License")
                url.set("https://opensource.org/licenses/MIT")
                distribution.set("repo")
            }
        }
        developers {
            developer {
                id.set("lavrox")
                name.set("Lavrox")
                url.set("https://lavrox.com")
            }
        }
        scm {
            url.set("https://github.com/lavrox/SDK-IPWho-Java")
            connection.set("scm:git:git://github.com/lavrox/SDK-IPWho-Java.git")
            developerConnection.set("scm:git:ssh://git@github.com/lavrox/SDK-IPWho-Java.git")
        }
    }
}
