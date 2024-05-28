//@file:JsModule("@vue/test-utils")
//@file:JsNonModule

package vue.testUtils

import dw.html.vue.Record
import org.w3c.dom.Element
import org.w3c.dom.Node

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

external interface DOMWrapper<T : Element> : BaseWrapper<T> {

}

external interface BaseWrapper<ElementType : Node> {
    fun <T : Element> find(selector: String): DOMWrapper<T>
    fun <T : Element> findAll(selector: String): Array<DOMWrapper<T>>
    fun text(): String
}

external interface VueWrapper: BaseWrapper<Node> {

}

@JsName("mount")
external fun <T, C: T> mount(originalComponent: T, options: ComponentMountingOptions<C>): VueWrapper //<ComponentExposed<C> & ComponentProps<C> & ComponentData<C>>;
@JsName("shallowMount")
external fun <T, C: T> shallowMount(originalComponent: T, options: ComponentMountingOptions<C>): VueWrapper //<ComponentExposed<C> & ComponentProps<C> & ComponentData<C>>;
