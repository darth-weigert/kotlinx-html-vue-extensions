//@file:JsModule("vue")
//@file:JsNonModule

package dw.html.vue

@JsModule("vue")
@JsNonModule
@JsName("default")
external object Vue {

    fun defineCustomElement(component: ComponentOptionsBase): () -> dynamic

    fun createApp(component: Component): App

    fun <T> ref(value: T): Ref<T>

    fun <T> computed(getter: ComputedGetter<T>): ComputedRef<T>
}

external interface Ref<T> {
    var value: T
}

external interface ComputedRef<T> {
    val value: T
}

