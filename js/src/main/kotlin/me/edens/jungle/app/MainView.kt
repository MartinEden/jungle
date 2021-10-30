package me.edens.jungle.app

import kotlinx.html.TagConsumer
import kotlinx.html.dom.append
import kotlinx.html.js.*
import me.edens.jungle.model.Action
import me.edens.jungle.model.Inventory
import me.edens.jungle.model.Status
import me.edens.jungle.model.actions.HumanAction
import me.edens.jungle.model.actions.DropAction
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
        val modelChange = state.model.update(action)
        val model = modelChange.newModel
        val feedback = modelChange.evidence
                .filter { it.apparentTo(model.human, model.map) }
                .map { it.describe(model.human, model.map) }
        render(AppState(model, feedback))
    }

    private fun render(state: AppState) {
        root.clear()
        root.append {
            ul {
                state.feedback.forEach {
                    li { +it }
                }
            }
            when (state.model.status) {
                Status.InProgress -> renderMainGameUI(state)
                Status.Victory -> renderVictoryUI()
                Status.Death -> renderDeathUI()
            }
        }
    }

    private fun TagConsumer<HTMLElement>.renderMainGameUI(state: AppState) {
        val model = state.model
        p { +model.human.location.description }
        ul {
            model.actionOptions.forEach { action ->
                li { actionLink(state, action) }
            }
        }
        div {
            p { +"Inventory "}
            ul {
                model.items.filter { it.location == Inventory }.forEach {
                    li {
                        span { +it.toString() }
                        span { +" [" }
                        actionLink(state, DropAction(it))
                        span { +"]" }
                    }
                }
            }
        }
    }

    private fun TagConsumer<HTMLElement>.renderVictoryUI() {
        p { +"Victory" }
    }

    private fun TagConsumer<HTMLElement>.renderDeathUI() {
        p { +"Death" }
    }

    private fun TagConsumer<HTMLElement>.actionLink(state: AppState, action: HumanAction) = a {
        href = "#"
        +action.description
        onClickFunction = { dispatch(state, action) }
    }
}