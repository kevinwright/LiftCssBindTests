import sbt._

class LiftCssBindTestsProject(info: ProjectInfo) extends DefaultWebProject(info) {
  override def scanDirectories = Nil
  override val jettyPort = 9090

  val mavenLocal = "Local Maven Repository" at "file://"+Path.userHome+"/.m2/repository"

  val liftVersion = "2.2-RC5"
  val jettyVersion = "7.2.0.RC0"

  val jetty7Client = "org.eclipse.jetty" % "jetty-client" % jettyVersion % "compile,test"
  val jetty7Webapp = "org.eclipse.jetty" % "jetty-webapp" % jettyVersion % "compile,test"
  val jetty7Server = "org.eclipse.jetty" % "jetty-server" % jettyVersion % "compile,test"
  val jetty7Plus = "org.eclipse.jetty" % "jetty-plus" % jettyVersion % "compile,test"
  val liftWebkit = "net.liftweb" %% "lift-webkit" % liftVersion
  val grizzledSlf4j = "org.clapper" %% "grizzled-slf4j" % "0.3.2"
  val specs = "org.scala-tools.testing" %% "specs" % "1.6.6"
  val unit = "junit" % "junit" % "4.8.2" % "test"
  val logback = "ch.qos.logback" % "logback-classic" % "0.9.25"
}
