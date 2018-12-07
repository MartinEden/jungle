package me.edens.jungle.model

import me.edens.jungle.model.evidence.Evidence

data class ModelChange(val newModel: Model, val evidence: List<Evidence>)
{
    fun andThen(moreChanges: (Model) -> ModelChange): ModelChange {
        val (successorModel, newEvidence) = moreChanges(newModel)
        return ModelChange(successorModel, evidence + newEvidence)
    }

    infix operator fun plus(newEvidence: Evidence) = copy(evidence = evidence + newEvidence)
}
