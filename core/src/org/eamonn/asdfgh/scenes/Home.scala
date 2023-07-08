package org.eamonn.asdfgh
package scenes

import com.badlogic.gdx.{Gdx, InputAdapter, Preferences}
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.asdfgh.{Scene, Text}

class Home extends Scene {
  var begun = false

  override def init(): InputAdapter = new HomeControl(this)

  override def update(delta: Float): Option[Scene] = {
    if(begun) Some(new Game) else None
  }

  override def render(batch: PolygonSpriteBatch): Unit = {
    if(Game.prefs.contains(Game.HighScoreKey)){
      Text.smallFont.draw(batch, f"High Score: ${Game.prefs.getFloat(Game.HighScoreKey)}%.1f secs", 0f, Geometry.ScreenHeight/3)
    }
    Text.smallFont.draw(batch, f"Begin[SPACE]", 0f, Geometry.ScreenHeight/4)

    batch.draw(Asdfgh.Logo, 0, Geometry.ScreenHeight/2, Geometry.ScreenWidth, Geometry.ScreenHeight/4)
  }
}
