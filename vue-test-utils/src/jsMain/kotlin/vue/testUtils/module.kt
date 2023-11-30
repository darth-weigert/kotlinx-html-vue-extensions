//@file:JsModule("@vue/test-utils")
//@file:JsNonModule

package vue.testUtils

import dw.html.vue.Record

external interface BaseMountingOptions<Props, Data> {
    @JsName("data")
    var dataProvider: (() -> Data)?
    @JsName("data")
    var data: Data?
    var props: Props?
    var propsData: Props?
    var attrs: Record<String, Any>?
    var slots: Any?
    var global: Any?
    var shallow: Boolean?
}

external interface MountingOptions<Props, Data>: BaseMountingOptions<Props, Data> {
    var attachTo: Any?
}

external interface ComponentMountingOptions<C>: MountingOptions<C, Any> {

}

external interface BaseWrapper {
    fun text(): String
}

external interface VueWrapper: BaseWrapper {

}

@JsName("mount")
external fun <T, C: T> mount(originalComponent: T, options: ComponentMountingOptions<C>): VueWrapper //<ComponentExposed<C> & ComponentProps<C> & ComponentData<C>>;
@JsName("shallowMount")
external fun <T, C: T> shallowMount(originalComponent: T, options: ComponentMountingOptions<C>): VueWrapper //<ComponentExposed<C> & ComponentProps<C> & ComponentData<C>>;
