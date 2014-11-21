organization  := "com.example"

version       := "0.1"

scalaVersion  := "2.11.2"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaV = "2.3.6"
  val sprayV = "1.3.2"
  Seq(
    "com.typesafe.akka"   %%  "akka-actor"    % akkaV,
    "com.typesafe.akka"   %%  "akka-testkit"  % akkaV    % "test",
    "com.typesafe.slick"  %%  "slick"         % "2.1.0",
    "io.spray"            %%  "spray-can"     % sprayV,
    "io.spray"            %%  "spray-json"    % "1.3.1",
    "io.spray"            %%  "spray-routing" % sprayV,
    "io.spray"            %%  "spray-testkit" % sprayV   % "test",
    "org.slf4j"           %   "slf4j-nop"     % "1.6.4",
    "org.specs2"          %%  "specs2-core"   % "2.3.11" % "test",
    "org.xerial"          %   "sqlite-jdbc"   % "3.7.2"            // << SQLite JDBC Driver
  )
}

Revolver.settings
