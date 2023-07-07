package org.eamonn.asdfgh

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.asdfgh.scenes.Game
import org.eamonn.asdfgh.util.TextureWrapper

trait invaderType {
  var image: TextureWrapper
  var tier: Int
}
case class Invader(
    var location: Vec2,
    var version: invaderType,
    game: Game
) {
  var health = version.tier
  var target = 0
  var dirMul: Int = -1
  var yTarget: Float = location.y
  var goingUp = false
  def goUp(): Unit = {
    yTarget += 1
    goingUp = true
    if (target == 0) {
      location.x = 0
      target = 15
    } else if (target == 15) {
      location.x = 15
      target = 0
    }
    dirMul = -dirMul
  }
  def update(delta: Float): Unit = {
    if (goingUp) {
      if (location.y < yTarget) {
        location.y += delta * 5
      } else {
        location.y = yTarget
        goingUp = false
      }
    }
    if (
      !game.invaders
        .filter(i => i.location.y.floor == location.y.floor)
        .exists(i => i.goingUp)
    ) {
      if (
        (location.x < target && target == 15) || (location.x > target && target == 0)
      ) {
        location.x += (delta * dirMul * 5)
      } else {
        goUp()
      }
    }
    if(location.y > 31){
      game.invaders = game.invaders.filterNot(i => i eq this)
      game.globalHealth -= version.tier
    }
  }
  def draw(batch: PolygonSpriteBatch): Unit = {
    batch.draw(
      version.image,
      location.x * screenUnit,
      location.y * screenUnit,
      screenUnit,
      screenUnit
    )
  }
}

case class basicOne(var image: TextureWrapper = Asdfgh.invader1, var tier: Int = 1)
    extends invaderType
