package dw.html.vue

import kotlinx.html.TagConsumer
import kotlinx.html.stream.appendHTML

fun <T>create(block: T.() -> Unit): T {
    return (js("{}") as T).apply(block)
}

fun component(block: Component.() -> Unit): Component {
    return create(block)
}

fun defineCustomElement(block: Component.() -> Unit): () -> dynamic {
    return Vue.defineCustomElement(component(block))
}

fun createApp(block: Component.() -> Unit): App {
    return Vue.createApp(component(block))
}

fun buildTemplate(block: TagConsumer<*>.() -> Unit): String {
//    val html = document.create
//    block(html)
//    return html.finalize()
    return buildString {
        block(appendHTML(prettyPrint = false))
    }
}
