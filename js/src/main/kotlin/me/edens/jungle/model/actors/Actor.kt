package me.edens.jungle.model.actors

import me.edens.jungle.model.*

interface Actor : Thing {
    fun act(model: Model): ActorAction
}

abstract class BasicActor : StructuralEqualityBase(), Actor {
    override fun fieldsAreEqual(other: Any): Boolean {
        val x = other as BasicActor
        return location == x.location
    }
}