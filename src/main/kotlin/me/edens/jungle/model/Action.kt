package me.edens.jungle.model

interface Action {
    val description: String
    fun apply(model: Model): Model
}