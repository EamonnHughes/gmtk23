package org.eamonn.asdfgh
import com.badlogic.gdx.physics.box2d.{Contact, ContactImpulse, ContactListener, Manifold}

class Contacter extends ContactListener {
  override def beginContact(contact: Contact): Unit = {
    var a = contact.getFixtureA.getUserData
    var b = contact.getFixtureB.getUserData
    a match {
      case invader: Invader => invader.health -= 1

      case _ =>
    }
  }

  override def endContact(contact: Contact): Unit = {}

  override def preSolve(contact: Contact, oldManifold: Manifold): Unit = {}

  override def postSolve(contact: Contact, impulse: ContactImpulse): Unit = {}
}