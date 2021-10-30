import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

fun Project.configureSpotless() {
    val ktlintVersion = "0.41.0"
    val ktlintOptions = mapOf("max_line_length" to "100", "disabled_rules" to "import-ordering")
    apply(plugin = Plugins.BuildPlugins.spotless)
    configure<SpotlessExtension> {
        kotlin {
            target("**/*.kt")
            targetExclude("**/build/")
            targetExclude("bin/**/*.kt")
            ktlint(ktlintVersion).userData(ktlintOptions)
        }

        kotlinGradle {
            target("*.gradle.kts")
            ktlint(ktlintVersion).userData(ktlintOptions)
        }
    }
}
