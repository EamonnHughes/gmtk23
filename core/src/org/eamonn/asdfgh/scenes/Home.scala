package org.eamonn.asdfgh
package scenes

import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.asdfgh.Scene

class Home extends Scene {
  var begun = false
  override def init(): InputAdapter = new HomeControl(this)

  override def update(delta: Float): Option[Scene] = {
    if(begun) Some(new Game) else None
  }

  override def render(batch: PolygonSpriteBatch): Unit = {
    batch.draw(Asdfgh.Logo, 0, Geometry.ScreenHeight/2, Geometry.ScreenWidth, Geometry.ScreenHeight/4)
  }
}
