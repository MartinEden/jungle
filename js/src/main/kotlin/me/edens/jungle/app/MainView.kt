package me.edens.jungle.app

import kotlinx.html.HTML
import kotlinx.html.TagConsumer
import kotlinx.html.dom.append
import kotlinx.html.js.*
import me.edens.jungle.model.Action
import me.edens.jungle.model.Inventory
import me.edens.jungle.model.Status
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
        render(AppState(state.model.update(action)))
    }

    private fun render(state: AppState) {
        root.clear()
        root.append {
            when (state.model.status) {
                Status.InProgress -> renderMainGameUI(state)
                Status.Victory -> renderVictoryUI(state)
            }
        }
    }

    private fun TagConsumer<HTMLElement>.renderMainGameUI(state: AppState) {
        val model = state.model
        p { +model.human.location.description }
        ul {
            model.actions.forEach { action ->
                li { actionLink(state, action) }
            }
        }
        p {
            +model.actors.first().location.toString()
        }
        div {
            p { +"Inventory "}
            ul {
                model.items.filter { it.location == Inventory }.forEach {
                    li { +it.toString() }
                }
            }
        }
    }

    private fun TagConsumer<HTMLElement>.renderVictoryUI(state: AppState) {
        p { +"Victory" }
    }

    private fun TagConsumer<HTMLElement>.actionLink(state: AppState, action: Action) = a {
        href = "#"
        +action.description
        onClickFunction = { dispatch(state, action) }
    }
}