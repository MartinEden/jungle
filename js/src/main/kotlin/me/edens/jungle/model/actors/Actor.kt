package me.edens.jungle.model.actors

import me.edens.jungle.model.ActorAction
import me.edens.jungle.model.Model
import me.edens.jungle.model.Place
import me.edens.jungle.model.StructuralEqualityBase

interface Actor {
    val location: Place

    fun act(model: Model): ActorAction
}

abstract class BasicActor : StructuralEqualityBase(), Actor {
    override fun fieldsAreEqual(other: Any): Boolean {
        val x = other as BasicActor
        return location == x.location
    }
}