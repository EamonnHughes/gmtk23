package org.eamonn.asdfgh
package scenes

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.InputAdapter

class GameControl(game: Game) extends InputAdapter {
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


  override def keyUp(keycode: Int): Boolean = {
    if(keycode == Keys.TAB){
      game.controlled += 1
      if(game.controlled >= game.invaders.length) game.controlled = 0
    }
    true
  }

  override def keyDown(keycode: Int): Boolean = {

    true
  }
}
