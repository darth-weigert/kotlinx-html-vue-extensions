//@file:JsModule("vue")
//@file:JsNonModule

package dw.html.vue

external interface Ref<T> {
    var value: T
}

external interface ComputedRef<T> {
    val value: T
}

external fun <T> ref(value: T): Ref<T>

external fun <T> computed(getter: ComputedGetter<T>): ComputedRef<T>
