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
  var controlled = false
  var dirMul: Int = -1
  var yTarget: Float = location.y
  var goingUp = false
  var bodyd: Body = _
  var fixture: Fixture = _
  var dead = false

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
    yTarget += 3
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
        bodyd.setLinearVelocity(0, delta * 100)
      } else {
        bodyd.setLinearVelocity(0, 0)
        goingUp = false
      }
    }
    if (
      !game.invaders
        .filter(i => i.location.y.floor - 1 == location.y.floor)
        .exists(i => i.goingUp) && !goingUp
    ) {
      if (
        (location.x < target && target == 16) || (location.x > target && target == 1)
      ) {
        bodyd.setLinearVelocity((delta * dirMul * 180), 0)
      } else {
        goUp()
      }
    } else if (!goingUp) {
      bodyd.setLinearVelocity(0, 0)
    }
    location.set(bodyd.getPosition.cpy())
    direction = bodyd.getAngle
    if (location.y > 27) {
      dead = true
      game.globalHealth -= health
    }
    if (health <= 0) dead = true
    if (dead) {
      EarthProtector.explode.play()
      game.invaders = game.invaders.filterNot(e => e eq this)
      bodyd.destroyFixture(fixture)
      game.world.destroyBody(bodyd)
    }
  }
  def updateAsControlled(delta: Float): Unit = {
    if (game.keysDown.contains(Keys.A)) bodyd.setAngularVelocity(delta * 120)
    else if (game.keysDown.contains(Keys.D))
      bodyd.setAngularVelocity(-delta * 120)
    else bodyd.setAngularVelocity(0)
    if (game.keysDown.contains(Keys.W))
      bodyd.setLinearVelocity(
        -Math.sin(direction).toFloat * delta * 180f,
        Math.cos(direction).toFloat * delta * 180f
      )
    location.set(bodyd.getPosition.cpy())
    direction = bodyd.getAngle
    if (location.y > 27) {
      dead = true
      game.globalHealth -= health
    }
    if (health <= 0) dead = true
    if (dead) {
      game.invaders = game.invaders.filterNot(e => e eq this)
      bodyd.destroyFixture(fixture)
      game.world.destroyBody(bodyd)
    }
  }
  def draw(batch: PolygonSpriteBatch): Unit = {
    batch.draw(
      version.image,
      ((location.x - .5f) * screenUnit),
      ((location.y - .5f) * screenUnit),
      screenUnit / 2,
      screenUnit / 2,
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
    if (controlled) {
      batch.draw(
        EarthProtector.frame,
        ((location.x - .5f) * screenUnit),
        ((location.y - .5f) * screenUnit),
        screenUnit / 2,
        screenUnit / 2,
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
                     var image: TextureWrapper = EarthProtector.invader1,
                     var tier: Int = 5
) extends invaderType
