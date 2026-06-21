package com.exploramus.shared.viewmodel.utils

data class SingleEffect(
    val pending: Boolean = false,
    val id: Int = 0
) {
    companion object {
        fun idle() = SingleEffect()
    }

    fun trigger() = SingleEffect(pending = true, id + 1)
    fun consume() = copy(pending = false)
}


