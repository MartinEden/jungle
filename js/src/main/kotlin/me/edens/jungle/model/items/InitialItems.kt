package me.edens.jungle.model.items

import me.edens.jungle.model.CrashSite
import me.edens.jungle.model.Inventory

fun initialItems() = listOf(
        Knife(Inventory),
        Parachute(CrashSite)
)