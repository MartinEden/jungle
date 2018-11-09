package me.edens.jungle.app

import kotlinx.html.TagConsumer
import kotlinx.html.dom.append
import kotlinx.html.js.*
import me.edens.jungle.model.Action
import org.w3c.dom.Element
import org.w3c.dom.HTMLElement
import kotlin.browser.document
import kotlin.browser.window
import kotlin.dom.clear

class MainView {
    private lateinit var root: Element

    fun run(initialState: AppState) {
        window.onload = {
            root = document.getElementById("root")!!
            render(initialState)
        }
    }

    private fun dispatch(state: AppState, action: Action) {
        render(AppState(action.apply(state.model)))
    }

    private fun render(state: AppState) {
        val model = state.model
        root.clear()
        root.append {
            p { +model.human.location.id }
            ul {
                model.human.actions(state.model).forEach { action ->
                    li { actionLink(state, action) }
                }
            }
        }
    }

    private fun TagConsumer<HTMLElement>.actionLink(state: AppState, action: Action) = a {
        href = "#"
        +action.description
        onClickFunction = { dispatch(state, action) }
    }
}