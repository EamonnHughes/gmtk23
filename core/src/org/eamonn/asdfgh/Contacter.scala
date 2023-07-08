package org.eamonn.asdfgh
import com.badlogic.gdx.physics.box2d.{
  Contact,
  ContactImpulse,
  ContactListener,
  Manifold
}
import org.eamonn.asdfgh.scenes.Wall

class Contacter extends ContactListener {
  override def beginContact(contact: Contact): Unit = {
    var a = contact.getFixtureA.getUserData
    var b = contact.getFixtureB.getUserData
    a match {
      case invader: Invader => {
        if (!b.isInstanceOf[Wall]) {
          invader.health -= 1
        }
      }
      case projectile: Projectile => projectile.destroyed = true
      case _                      =>
    }
  }

  override def endContact(contact: Contact): Unit = {}

  override def preSolve(contact: Contact, oldManifold: Manifold): Unit = {}

  override def postSolve(contact: Contact, impulse: ContactImpulse): Unit = {}
}
