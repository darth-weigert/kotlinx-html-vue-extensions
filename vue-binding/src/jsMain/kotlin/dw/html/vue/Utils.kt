package dw.html.vue

import kotlinx.html.TagConsumer
import kotlinx.html.stream.appendHTML
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
inline fun <T>create(block: T.() -> Unit): T {
    contract {
        callsInPlace(block, kind = InvocationKind.EXACTLY_ONCE)
    }

    return (Any().unsafeCast<T>()).apply(block)
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

//fun buildTemplate(block: TagConsumer<*>.() -> Unit): HTMLElement {
//    val html = document.create
//    block(html)
//    return html.finalize()
//}

fun buildTemplate(block: TagConsumer<*>.() -> Unit): String {
    return buildString {
        block(appendHTML(prettyPrint = false))
    }
}

fun App.component(name: String, block: Component.() -> Unit): App {
    return this.component(name, component(block))
}
