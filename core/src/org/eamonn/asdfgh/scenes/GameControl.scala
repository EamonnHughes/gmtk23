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
    game.keysDown = game.keysDown.filterNot(k => k == keycode)
    if(keycode == Keys.TAB){
      if(game.invaders.exists(e=> e.controlled)) {
        game.invaders.foreach(e => {
          game.invaders((game.invaders.indexOf(e) + 1) % game.invaders.length).controlled = true
          e.controlled = false

        })
      } else if (game.invaders.nonEmpty) {
        game.invaders.head.controlled = true
      }

    }
    true
  }

  override def keyDown(keycode: Int): Boolean = {
    game.keysDown = keycode :: game.keysDown
    true
  }
}
