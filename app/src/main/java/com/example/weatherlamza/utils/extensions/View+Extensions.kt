package com.example.weatherlamza.utils.extensions

import android.view.View


fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun <T : View> T.visibleIf(
    condition: Boolean,
    invisibleStatus: Int = View.GONE,
    apply: T.() -> Unit
) = visibleIf({ condition }, invisibleStatus, apply)


fun <T : View> T.visibleIf(
    condition: () -> Boolean,
    invisibleStatus: Int = View.GONE,
    apply: T.() -> Unit
) {
    if (condition.invoke()) {
        visibility = View.VISIBLE
        apply.invoke(this)
    } else {
        visibility = invisibleStatus
    }
}
