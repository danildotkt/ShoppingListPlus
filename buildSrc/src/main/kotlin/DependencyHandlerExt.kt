import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

fun DependencyHandler.implementation(dependency: String){
    add("implementation", dependency)
}

fun DependencyHandler.implementation(dependency: Dependency){
    add("implementation", dependency)
}

fun DependencyHandler.ksp(dependency: String){
    add("ksp", dependency)
}

fun DependencyHandler.room() {
    implementation(Dependencies.roomKts)
    ksp(Dependencies.roomCompiler)
}

fun DependencyHandler.daggerHilt() {
    implementation(Dependencies.daggerHilt)
    ksp(Dependencies.daggerHiltCompiler)
}

fun DependencyHandler.data() {
    implementation(project(":data"))
}

fun DependencyHandler.domain() {
    implementation(project(":domain"))
}




