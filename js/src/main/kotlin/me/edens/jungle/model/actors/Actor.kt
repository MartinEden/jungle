package me.edens.jungle.model.actors

import me.edens.jungle.model.*
import me.edens.jungle.model.evidence.Observer

interface Actor : Thing, Observer {
    val signature: Signature
    fun act(model: Model): Action
    fun atLocation(location: Place): Actor
}

abstract class BasicActor(override val signature: Signature) : StructuralEqualityBase(), Actor {
    override fun fieldsAreEqual(other: Any): Boolean {
        val x = other as BasicActor
        return signature == x.signature
                && location == x.location
    }
}