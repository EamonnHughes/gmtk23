package org.eamonn.asdfgh

import com.badlogic.gdx.Application.ApplicationType
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.{ApplicationAdapter, Gdx, Input}
import org.eamonn.asdfgh.scenes.Home
import org.eamonn.asdfgh.util.{GarbageCan, TextureWrapper}

class Asdfgh extends ApplicationAdapter {
  import Asdfgh.garbage

  private val idMatrix = new Matrix4()
  private var batch: PolygonSpriteBatch = _
  private var scene: Scene = _

  override def create(): Unit = {

    Gdx.input.setCatchKey(Input.Keys.BACK, true)

    batch = garbage.add(new PolygonSpriteBatch())

    Asdfgh.Square = TextureWrapper.load("Square.png")
    Asdfgh.Circle = TextureWrapper.load("Circle.png")
    Asdfgh.Logo = TextureWrapper.load("Logo.png")
    Asdfgh.defender = TextureWrapper.load("defender.png")
    Asdfgh.invader1 = TextureWrapper.load("baseAlien.png")

    //    Asdfgh.sound = Asdfgh.loadSound("triangle.mp3")

    Text.loadFonts()

    setScene(new Home)
  }

  override def render(): Unit = {

    val delta = Gdx.graphics.getDeltaTime
    scene.update(delta) foreach setScene
    ScreenUtils.clear(0f, 0f, 0f, 1)
    batch.setTransformMatrix(idMatrix)
    batch.begin()
    scene.render(batch)

    batch.end()
  }

  override def dispose(): Unit = {
    garbage.dispose()
  }

  private def setScene(newScene: Scene): Unit = {
    scene = newScene
    Gdx.input.setInputProcessor(scene.init())
  }

}

object Asdfgh {
  implicit val garbage: GarbageCan = new GarbageCan

  var sound: Sound = _
  var Square: TextureWrapper = _
  var Circle: TextureWrapper = _
  var Logo: TextureWrapper = _
  var defender: TextureWrapper = _
  var invader1: TextureWrapper = _

  def mobile: Boolean = isMobile(Gdx.app.getType)

  private def isMobile(tpe: ApplicationType) =
    tpe == ApplicationType.Android || tpe == ApplicationType.iOS

  private def loadSound(path: String)(implicit garbage: GarbageCan): Sound =
    garbage.add(Gdx.audio.newSound(Gdx.files.internal(path)))

}
