package org.eamonn.asdfgh

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.asdfgh.scenes.Game

case class Defender(game: Game) {
  var location = Vec2(0, 32f)
  var targetX = 0f
  def draw(batch: PolygonSpriteBatch): Unit = {
    batch.draw(
      Asdfgh.defender,
      location.x * screenUnit,
      (location.y - 1) * screenUnit,
      screenUnit,
      screenUnit
    )
  }
  def update(delta: Float): Unit = {
    targetX = game.rowThreat.indexOf(game.rowThreat.maxBy(i => i))
    location.x += delta * 5 * (targetX - location.x).sign

  }
}
