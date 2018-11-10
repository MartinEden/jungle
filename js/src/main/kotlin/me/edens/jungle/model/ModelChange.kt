package me.edens.jungle.model

import me.edens.jungle.model.evidence.Evidence

data class ModelChange(val newModel: Model, val evidence: List<Evidence>)
