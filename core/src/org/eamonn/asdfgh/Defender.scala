package org.eamonn.asdfgh

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.{Body, BodyDef, Fixture, PolygonShape}
import org.eamonn.asdfgh.scenes.Game

case class Defender(game: Game) {
  var location = new Vector2(0, 32f)
  var targetX = 0f
  var bodyd: Body = _
  var fixture: Fixture = _
  def create(): Unit = {
    var bodyDef: BodyDef = new BodyDef()
    val shape = new PolygonShape()
    shape.set(
      Array(
        new Vector2(0.5f, 0.5f),
        new Vector2(-0.5f, 0.5f),
        new Vector2(0.5f, -0.5f),
        new Vector2(-0.5f, -0.5f)
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
    bodyd.setAngularVelocity(delta * 5 * (targetX - location.x).sign)
    location = bodyd.getPosition
  }
}
