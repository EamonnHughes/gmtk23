package org.eamonn.asdfgh
package scenes

import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.asdfgh.Scene

class Game extends Scene {
  var spawnTimer = 0f
  var globalHealth = 10
  var resources = 20
  var defender = Defender(this)
  def timeSkip(): Unit = {
    if(resources >= 5) {
      resources -= 5
      invaders.foreach(i => {
        i.location.y += 2
        i.yTarget += 2
      })
    }
  }
  def spawnNewInvader(version: invaderType, locx: Float): Unit = {
    invaders = Invader(Vec2(locx, 0f), version, this) :: invaders
  }
  var rowThreat: Array[Int] =
    Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
  var invaders: List[Invader] = List.empty

  override def init(): InputAdapter = {
for(i <- 0 until 64) {
  spawnNewInvader(new basicOne, 16f + i)
}
  new GameControl(this)
  }

  override def update(delta: Float): Option[Scene] = {
    rowThreat = Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    if (spawnTimer > 0) spawnTimer -= delta
    invaders.filter(i => (i.location.x < 16 && i.location.x >= 0)).foreach(i => {
    rowThreat(i.location.x.floor.toInt) += (i.version.tier * i.location.y).ceil.toInt
      if(i.location.x >= 1) rowThreat(i.location.x.floor.toInt - 1) += (i.version.tier * i.location.y/3).ceil.toInt
      if(i.location.x < 15) rowThreat(i.location.x.floor.toInt + 1) += (i.version.tier * i.location.y/3).ceil.toInt
    })
    invaders.foreach(i => i.update(delta))
    defender.update(delta)
    None
  }

  override def render(batch: PolygonSpriteBatch): Unit = {
    defender.draw(batch)
    invaders.foreach(i => i.draw(batch))
    Text.mediumFont.setColor(Color.WHITE)
    Text.mediumFont.draw(batch, resources.toString, 0f, Geometry.ScreenHeight)
  }
}
