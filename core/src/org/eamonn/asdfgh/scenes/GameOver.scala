package org.eamonn.asdfgh
package scenes

import com.badlogic.gdx.{Gdx, InputAdapter, Preferences}
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.eamonn.asdfgh.Scene

class GameOver(time: Float, won: Boolean) extends Scene {
  var begun = false

  override def init(): InputAdapter = {
    var highScore =
      if (Game.prefs.contains(Game.HighScoreKey))
        Game.prefs.getFloat(Game.HighScoreKey)
      else Float.MaxValue
    if (won) {
      if (time < highScore) {
        Game.prefs.putFloat(Game.HighScoreKey, time)
        Game.prefs.flush()
      }
    }
    new GameOverController(this)
  }

  override def update(delta: Float): Option[Scene] = {
    if (begun) Some(new Home) else None
  }

  override def render(batch: PolygonSpriteBatch): Unit = {
    if (won) {
      Text.smallFont.draw(
        batch,
        f"You won!\nTime: $time%.1f secs",
        0,
        Geometry.ScreenHeight - (3 * screenUnit)
      )
    } else if (time < 60f) {
      Text.tinyFont.draw(
        batch,
        f"The fleet was destroyed!",
        0,
        Geometry.ScreenHeight - (3 * screenUnit)
      )
    } else {
      Text.tinyFont.draw(
        batch,
        f"You ran out of time!",
        0,
        Geometry.ScreenHeight - (3 * screenUnit)
      )
    }
    Text.smallFont.draw(batch, "Try Again[SPACE]", 0, Geometry.ScreenHeight / 2)
  }
}
import com.badlogic.gdx.Input.Keys

class GameOverController(over: GameOver) extends InputAdapter {
  override def touchDown(
      screenX: Int,
      screenY: Int,
      pointer: Int,
      button: Int
  ): Boolean = {
    true
  }

  override def mouseMoved(screenX: Int, screenY: Int): Boolean = {

    true
  }

  override def keyDown(keycode: Int): Boolean = {
    if (keycode == Keys.SPACE) {
      over.begun = true
      EarthProtector.continue.play()
    }
    if (keycode == Keys.ESCAPE) System.exit(0)
    true
  }
}
