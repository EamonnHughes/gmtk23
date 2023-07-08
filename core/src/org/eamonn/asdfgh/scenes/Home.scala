package org.eamonn.asdfgh
package scenes

import com.badlogic.gdx.{Gdx, InputAdapter, Preferences}
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.asdfgh.Asdfgh.garbage
import org.eamonn.asdfgh.util.TextureWrapper
import org.eamonn.asdfgh.{Scene, Text}

class Home extends Scene {
  var state = 0
  var intro1: TextureWrapper = TextureWrapper.load("Intro1.png")
  var intro2: TextureWrapper = TextureWrapper.load("Intro2.png")

  override def init(): InputAdapter = new HomeControl(this)

  override def update(delta: Float): Option[Scene] = {
    if(state == 3) Some(new Game) else None
  }

  override def render(batch: PolygonSpriteBatch): Unit = {
    if(state == 0) {
      if (Game.prefs.contains(Game.HighScoreKey)) {
        Text.smallFont.draw(batch, f"High Score:\n${Game.prefs.getFloat(Game.HighScoreKey)}%.1f seconds", 0f, Geometry.ScreenHeight / 3)
      }
      Text.smallFont.draw(batch, f"Begin[SPACE]", 0f, Geometry.ScreenHeight / 4)
      batch.draw(Asdfgh.Logo, 0, Geometry.ScreenHeight/2, Geometry.ScreenWidth, Geometry.ScreenHeight/4)
    } else if(state == 1){
      batch.draw(intro1, 0, Geometry.ScreenWidth, Geometry.ScreenWidth, Geometry.ScreenWidth)
    } else if (state == 2) {
      batch.draw(intro2, 0, Geometry.ScreenWidth, Geometry.ScreenWidth, Geometry.ScreenWidth)
    }
  }
}
