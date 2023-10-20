@file:JsModule("vue")
@file:JsNonModule

package dw.html.vue

@JsName("defineCustomElement")
external fun defineCustomElement(component: ComponentOptionsBase): () -> dynamic
