package org.eamonn.asdfgh

import com.badlogic.gdx.Input.Keys
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
  var direction = 0f
  var health = version.tier
  var target = 1
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
        new Vector2(.5f, .5f),
        new Vector2(.5f, -.5f),
        new Vector2(-.5f, .5f),
        new Vector2(-.5f, -.5f)
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
    if (target == 1) {
      target = 16
    } else if (target == 16) {
      target = 1
    }
    dirMul = -dirMul
  }
  def update(delta: Float): Unit = {
    if (goingUp) {
      if (location.y < yTarget) {
        bodyd.setLinearVelocity(delta * 300, 0)
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
        (location.x < target && target == 16) || (location.x > target && target == 10)
      ) {
        bodyd.setLinearVelocity(0, (delta * dirMul * 180))
      } else {
        goUp()
      }
    } else if (!goingUp) {
      bodyd.setLinearVelocity(0, 0)
    }
    location.set(bodyd.getPosition.cpy())
    direction = bodyd.getAngle
    if (location.y > 31) {
      game.invaders = game.invaders.filterNot(i => i eq this)
      game.globalHealth -= version.tier
    }
  }
  def updateAsControlled(delta: Float): Unit = {
    bodyd.setLinearVelocity(0, 0)
    if (game.keysDown.contains(Keys.A)) bodyd.setAngularVelocity(delta * 20)
    if (game.keysDown.contains(Keys.D)) bodyd.setAngularVelocity(-delta * 20)
    location.set(bodyd.getPosition.cpy())
    direction = bodyd.getAngle
  }
  def draw(batch: PolygonSpriteBatch): Unit = {
    batch.draw(
      version.image,
      ((location.x - .5f - (Math.cos(direction).toFloat)) * screenUnit),
      ((location.y - .5f - (Math.sin(direction).toFloat)) * screenUnit),
      0,
      0,
      (screenUnit),
      (screenUnit),
      1f,
      1f,
      direction.toDegrees,
      0,
      0,
      16,
      16,
      false,
      false
    )
    if (game.invaders.indexOf(this) == game.controlled) {
      batch.draw(
        Asdfgh.frame,
        ((location.x - .5f - (Math.cos(direction).toFloat)) * screenUnit),
        ((location.y - .5f - (Math.sin(direction).toFloat)) * screenUnit),
        0,
        0,
        (screenUnit),
        (screenUnit),
        1f,
        1f,
        direction.toDegrees,
        0,
        0,
        16,
        16,
        false,
        false
      )
    }
  }
}

case class basicOne(
    var image: TextureWrapper = Asdfgh.invader1,
    var tier: Int = 1
) extends invaderType
