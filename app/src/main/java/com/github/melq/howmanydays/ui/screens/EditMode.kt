package com.github.melq.howmanydays.ui.screens

enum class EditMode {
    Add,
    Edit;

    companion object {
        fun fromOrdinal(ordinal: Int): EditMode {
            return when (ordinal) {
                0 -> Add
                1 -> Edit
                else -> throw IllegalArgumentException("Invalid ordinal: $ordinal")
            }
        }
    }
}
