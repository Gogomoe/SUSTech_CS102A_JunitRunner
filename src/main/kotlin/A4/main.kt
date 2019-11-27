package A4

import org.junit.runner.JUnitCore
import java.io.File

val root = "Assignment4"

val junitFile = File("$root/Assignment4Junit2.java")

fun main() {
    File(root).listFiles { file -> file.isDirectory }!!.forEach {
        try {
            process(it.name, File(it, "Submission attachment(s)"))
        } catch (e: Exception) {
            Console.error(e.message)
        }
    }
}

fun process(name: String, dir: File) {
    Console.print("$name   ")

    val targetJunit = File("$dir/Assignment4Junit2.java")
    if (!targetJunit.exists()) {
        junitFile.copyTo(targetJunit)
    }

    try {
        Compiler.compile(
            File("$dir/Assignment4Junit2.java"),
            File("$dir/BusLine.java"),
            File("$dir/District.java"),
            File("$dir/Polynomial.java"),
            File("$dir/Station.java")
        )
    } catch (e: Exception) {
        Console.error("compile error")
        throw e
    }

    val loader = Loader(dir.toPath())
    val junitClass = loader.loadClass("Assignment4Junit2")

    val junit = JUnitCore()
    val result = junit.run(junitClass)

    if (result.wasSuccessful()) {
        Console.accept("${27 - result.failureCount}/27\n")
    } else {
        Console.error("${27 - result.failureCount}/27\n")
        result.failures.forEach {
            Console.print("\t${it.description}    ")
            Console.error("${it.exception}: ")
            Console.error(it.message + "\n")
        }
    }

}
