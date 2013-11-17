import java.io.{FilenameFilter, File, FileFilter}
import scala.io.Source

object Clasher extends App {

  def traverseJavaSources(dir: File): Unit = {
    val javaSrcFiles = dir.listFiles(new FilenameFilter() {
      def accept(dir: File, name: String): Boolean = {
        name.endsWith(".java")
      }
    })

//    javaSrcFiles.foreach(println)
    javaSrcFiles.foreach(readFullClassname)

    val subDirs = dir.listFiles(new FileFilter() {
      def accept(pathname: File): Boolean = {
        pathname.isDirectory
      }
    })

    subDirs.foreach(traverseJavaSources)
  }

  def readFullClassname(src: File): Unit = {
    val lines: Iterator[String] = Source.fromFile(src).getLines().dropWhile(!_.contains("package"))
    println(readPackage(lines))
  }

  def readPackage(srcLines: Iterator[String]): String = {
    val lines: Iterator[String] = srcLines.dropWhile(!_.contains("package"))
    if (srcLines.hasNext) lines.next().diff("package").diff(";").trim + "."
    else ""
  }

  val platformDir = "/Users/richard/Documents/dev/apdm/core/platform"
  val rootFile = new File(platformDir)

  println("hello world")

  traverseJavaSources(rootFile)
}
