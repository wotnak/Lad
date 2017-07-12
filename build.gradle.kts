import org.gradle.jvm.tasks.Jar
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.apache.tools.ant.filters.ReplaceTokens

buildscript {
    repositories { jcenter() }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.1.3-2")
        classpath("com.github.jengelman.gradle.plugins:shadow:2.0.1")
    }
}

apply {
    plugin("kotlin")
    plugin("com.github.johnrengelman.shadow")
}

group = "net.wotnak"
version = "0.1-dev"

repositories {
    jcenter()
    maven { setUrl("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
}

dependencies {
    compile("org.jetbrains.kotlin:kotlin-stdlib-jre8")
    compile("org.bukkit:bukkit:1.12-R0.1-SNAPSHOT")
}

tasks {
    "processResources"(ProcessResources::class) {
        from("src/main/resources") {
            filter<ReplaceTokens>("tokens" to mapOf("version" to version))
        }
    }

    "jar"(Jar::class) {
       from("LICENSE")
    }

    "shadowJar"(ShadowJar::class) {
        from("LICENSE")
        dependencies {
            include(dependency(":kotlin-runtime"))
            include(dependency(":kotlin-stdlib-jre8"))
        }
        classifier = "withKotlin"
    }
}
