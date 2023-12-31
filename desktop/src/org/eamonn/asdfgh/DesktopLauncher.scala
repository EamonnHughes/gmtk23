package org.eamonn.asdfgh

import com.badlogic.gdx.backends.lwjgl3.{Lwjgl3Application, Lwjgl3ApplicationConfiguration}

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
object DesktopLauncher extends App {
  val config = new Lwjgl3ApplicationConfiguration
  config.setTitle("Earth Protector")
  config.setForegroundFPS(60)
  config.setWindowedMode(512, 1024)
  new Lwjgl3Application(new EarthProtector, config)
}
