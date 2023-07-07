package org.eamonn.asdfgh
package scenes

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.InputAdapter

class HomeControl(home: Home) extends InputAdapter {
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
    if(keycode == Keys.SPACE) home.begun = true
    if(keycode == Keys.ESCAPE) System.exit(0)
    true
  }
}
