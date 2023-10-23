package dw.html.vue

import kotlinx.browser.document
import org.w3c.dom.HTMLElement
import kotlinx.html.TagConsumer
import kotlinx.html.dom.create

fun <T>create(block: T.() -> Unit): T {
    return (js("{}") as T).apply(block)
}

fun component(block: Component.() -> Unit): Component {
    return create(block)
}

fun defineCustomElement(block: Component.() -> Unit): () -> dynamic {
    return defineCustomElement(component(block))
}

fun createApp(block: Component.() -> Unit): App {
    return createApp(component(block))
}

fun buildTemplate(block: TagConsumer<*>.() -> Unit): HTMLElement {
    val html = document.create
    block(html)
    return html.finalize()
}
