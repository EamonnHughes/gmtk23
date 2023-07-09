package org.eamonn.asdfgh

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.{Body, BodyDef, Fixture, PolygonShape}
import org.eamonn.asdfgh.scenes.Game

case class Defender(game: Game) {
  var location = new Vector2(0, 28f)
  var targetX = 0f
  var sTick = 1f
  def draw(batch: PolygonSpriteBatch): Unit = {
    batch.draw(
      Asdfgh.defender,
      location.x * screenUnit,
      (location.y - 1) * screenUnit,
      screenUnit,
      screenUnit
    )
  }
  def shoot(): Unit = {
    Asdfgh.shot.play()
    var p = Projectile(new Vector2(location.x + .5f, location.y - 1), game)
    p.create()
    game.projectiles = p :: game.projectiles

  }
  def update(delta: Float): Unit = {
    targetX = game.rowThreat.indexOf(game.rowThreat.maxBy(i => i))
    location.x += delta * 5 * (targetX - location.x).sign
    sTick -= delta
    if (sTick <= 0f) {
      sTick = 1f
      shoot()
    }
  }
}

case class Projectile(location: Vector2, game: Game) {
  var bodyd: Body = _
  var destroyed = false
  var fixture: Fixture = _
  def create(): Unit = {
    var bodyDef: BodyDef = new BodyDef()
    val shape = new PolygonShape()
    shape.set(
      Array(
        new Vector2(.1f, .1f),
        new Vector2(.1f, -.1f),
        new Vector2(-.1f, .1f),
        new Vector2(-.1f, -.1f)
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
  def update(delta: Float): Unit = {
    bodyd.setLinearVelocity(0, -delta * 500)
    location.set(bodyd.getPosition)
    if (destroyed) {
      game.projectiles = game.projectiles.filterNot(e => e eq this)
      bodyd.destroyFixture(fixture)
      game.world.destroyBody(bodyd)
    }
  }
  def draw(batch: PolygonSpriteBatch): Unit = {
    batch.draw(
      Asdfgh.Square,
      (location.x - .1f) * screenUnit,
      (location.y - .1f) * screenUnit,
      .2f * screenUnit,
      .2f * screenUnit
    )
  }

}
