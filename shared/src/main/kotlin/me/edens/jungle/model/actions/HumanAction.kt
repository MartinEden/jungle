package me.edens.jungle.model.actions

import me.edens.jungle.model.Action

interface HumanAction : Action {
    val description: String
}