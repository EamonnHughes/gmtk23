package org.eamonn.asdfgh

import com.badlogic.gdx.Application.ApplicationType
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.utils.{GdxNativesLoader, ScreenUtils}
import com.badlogic.gdx.{ApplicationAdapter, Gdx, Input}
import org.eamonn.asdfgh.scenes.Home
import org.eamonn.asdfgh.util.{GarbageCan, TextureWrapper}

class EarthProtector extends ApplicationAdapter {
  import EarthProtector.garbage

  private val idMatrix = new Matrix4()
  private var batch: PolygonSpriteBatch = _
  private var scene: Scene = _

  override def create(): Unit = {

    Gdx.input.setCatchKey(Input.Keys.BACK, true)

    batch = garbage.add(new PolygonSpriteBatch())

    EarthProtector.Square = TextureWrapper.load("Square.png")
    EarthProtector.Circle = TextureWrapper.load("Circle.png")
    EarthProtector.Logo = TextureWrapper.load("Logo.png")
    EarthProtector.defender = TextureWrapper.load("defender.png")
    EarthProtector.invader1 = TextureWrapper.load("baseAlien.png")
    EarthProtector.frame = TextureWrapper.load("frame.png")
    EarthProtector.explode = EarthProtector.loadSound("explode.mp3")
    EarthProtector.shot = EarthProtector.loadSound("shoot.mp3")
    EarthProtector.continue = EarthProtector.loadSound("continue.mp3")

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
    if (scene.debugging) {
      scene.debugRenderer.render(scene.world, scene.debugMatrix)
    }
  }

  override def dispose(): Unit = {
    garbage.dispose()
  }

  private def setScene(newScene: Scene): Unit = {
    scene = newScene
    Gdx.input.setInputProcessor(scene.init())
  }

}

object EarthProtector {
  implicit val garbage: GarbageCan = new GarbageCan

  var shot: Sound = _
  var explode: Sound = _
  var Square: TextureWrapper = _
  var Circle: TextureWrapper = _
  var Logo: TextureWrapper = _
  var defender: TextureWrapper = _
  var invader1: TextureWrapper = _
  var frame: TextureWrapper = _
  var continue: Sound = _

  def mobile: Boolean = isMobile(Gdx.app.getType)

  private def isMobile(tpe: ApplicationType) =
    tpe == ApplicationType.Android || tpe == ApplicationType.iOS

  private def loadSound(path: String)(implicit garbage: GarbageCan): Sound =
    garbage.add(Gdx.audio.newSound(Gdx.files.internal(path)))

}
