package org.eamonn.asdfgh
package scenes

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.{Gdx, InputAdapter, Preferences}
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import com.badlogic.gdx.math.{Matrix4, Vector2}
import com.badlogic.gdx.physics.box2d.{Body, Box2DDebugRenderer, World}
import org.eamonn.asdfgh.Scene

class Game extends Scene {
  var spawnTimer = 0f
  var globalHealth = 10
  var timerUp = 0f
  var defender = Defender(this)
  var contacter: Contacter = _
  var keysDown = List.empty[Int]
  var projectiles = List.empty[Projectile]
  def spawnNewInvader(version: invaderType, loc: Vector2): Unit = {
    invaders = Invader(new Vector2(1 + loc.x, loc.y), version, this) :: invaders
  }
  var rowThreat: Array[Int] =
    Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
  var invaders: List[Invader] = List.empty

  override def init(): InputAdapter = {
    world = new World(new Vector2(0, -10f), true)
    for (x <- 0 until 8) {
      for (y <- 0 until 2) {
        spawnNewInvader(new basicOne, new Vector2(x * 2 + .5f, y * 2 + .5f))
      }
    }
    invaders.foreach(i => i.create())

    debugging = false
    debugRenderer = new Box2DDebugRenderer()
    contacter = new Contacter()
    world.setContactListener(contacter)
    new GameControl(this)
  }
  override def update(delta: Float): Option[Scene] = {
    timerUp += delta
    world.step(delta, 3, 3)
    projectiles.foreach(p => p.update(delta))
    rowThreat = Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    if (spawnTimer > 0) spawnTimer -= delta
    invaders
      .filter(i => (i.location.x < 16 && i.location.x >= 0))
      .foreach(i => {
        rowThreat(
          i.location.x.floor.toInt
        ) += (i.version.tier * i.location.y).ceil.toInt
        if (i.location.x >= 1)
          rowThreat(
            i.location.x.floor.toInt - 1
          ) += (i.version.tier * i.location.y / 3).ceil.toInt
        if (i.location.x < 15)
          rowThreat(
            i.location.x.floor.toInt + 1
          ) += (i.version.tier * i.location.y / 3).ceil.toInt
      })
    invaders
      .filterNot(i => i.controlled)
      .foreach(i => i.update(delta))
    invaders.filter(i => i.controlled).foreach(e => e.updateAsControlled(delta))
    defender.update(delta)
    if (globalHealth <= 0) Some(new GameOver(timerUp, true))
    else if (invaders.isEmpty || timerUp >= 60f)
      Some(new GameOver(timerUp, false))
    else None
  }

  override def render(batch: PolygonSpriteBatch): Unit = {
    defender.draw(batch)
    projectiles.foreach(p => p.draw(batch))
    invaders.foreach(i => i.draw(batch))
    Text.smallFont.setColor(Color.WHITE)
    Text.smallFont.draw(
      batch,
      s"Laser at %${(timerUp * 100 / 60).round} \nShields at %${globalHealth * 10}",
      0f,
      Geometry.ScreenHeight
    )

    debugMatrix = new Matrix4(batch.getProjectionMatrix)
    debugMatrix.scale(screenUnit, screenUnit, 1f)
  }
}

object Game {
  final val HighScoreKey = "Highscore"
  val prefs: Preferences = Gdx.app.getPreferences("org.eamonn.asdfgh.Template")
}