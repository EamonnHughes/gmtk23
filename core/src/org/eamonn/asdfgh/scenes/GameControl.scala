package org.eamonn.asdfgh
package scenes

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.InputAdapter

import scala.util.Random

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
        game.invaders.filter(e => e.controlled).foreach(e => {
          e.controlled = false
          game.invaders(Random.nextInt(game.invaders.length)).controlled = true
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
