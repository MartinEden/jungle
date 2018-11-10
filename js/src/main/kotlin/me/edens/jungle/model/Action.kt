package me.edens.jungle.model

interface Action {
    fun apply(model: Model): ModelChange
}