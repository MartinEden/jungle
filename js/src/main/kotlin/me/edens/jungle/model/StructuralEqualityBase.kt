package me.edens.jungle.model

abstract class StructuralEqualityBase {
    override fun equals(other: Any?): Boolean {
        return other != null
                && this::class == other::class
                && fieldsAreEqual(other)
    }

    protected abstract fun fieldsAreEqual(other: Any): Boolean
}