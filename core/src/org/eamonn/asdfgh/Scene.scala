package org.eamonn.asdfgh

import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.physics.box2d.{Box2DDebugRenderer, World}

abstract class Scene {
  var debugging: Boolean = _
  var debugRenderer: Box2DDebugRenderer = _
  var world: World = _
  var debugMatrix: Matrix4 = _

  def init(): InputAdapter
  def update(delta: Float): Option[Scene]
  def render(batch: PolygonSpriteBatch): Unit
}
