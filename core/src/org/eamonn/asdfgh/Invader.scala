package org.eamonn.asdfgh

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.{Body, BodyDef, Fixture, PolygonShape}
import org.eamonn.asdfgh.scenes.Game
import org.eamonn.asdfgh.util.TextureWrapper

trait invaderType {
  var image: TextureWrapper
  var tier: Int
}
case class Invader(
    var location: Vector2,
    var version: invaderType,
    game: Game
) {
  var health = version.tier
  var target = 0
  var dirMul: Int = -1
  var yTarget: Float = location.y
  var goingUp = false
  var bodyd: Body = _
  var fixture: Fixture = _

  def create(): Unit = {
    var bodyDef: BodyDef = new BodyDef()
    val shape = new PolygonShape()
    shape.set(
      Array(
        new Vector2(1f, 1f),
        new Vector2(1f, 0f),
        new Vector2(0f, 1f),
        new Vector2(0f, 0f)
      )
    )
    bodyDef.`type` = BodyDef.BodyType.DynamicBody
    bodyDef.position.set(location.x, location.y)
    bodyd = game.world.createBody(bodyDef)
    fixture = bodyd.createFixture(shape, 1f)
    fixture.setUserData(this)
    bodyd.setGravityScale(0)
    shape.dispose()
  }
  def goUp(): Unit = {
    yTarget += 2
    goingUp = true
    if (target == 0) {
      target = 15
    } else if (target == 15) {
      target = 0
    }
    dirMul = -dirMul
  }
  def update(delta: Float): Unit = {
    if (goingUp) {
      if (location.y < yTarget) {
        bodyd.setLinearVelocity(0, delta * 300)
      } else {
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
        bodyd.setLinearVelocity((delta * dirMul * 180), 0)
      } else {
        goUp()
      }
    } else if (!goingUp) {
      bodyd.setLinearVelocity(0, 0)
    }
    location.set(bodyd.getPosition.cpy())
    if (location.y > 31) {
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
    if (game.invaders.indexOf(this) == game.controlled) {
      batch.draw(
        Asdfgh.frame,
        location.x * screenUnit,
        location.y * screenUnit,
        screenUnit,
        screenUnit
      )
    }
  }
}

case class basicOne(
    var image: TextureWrapper = Asdfgh.invader1,
    var tier: Int = 1
) extends invaderType
