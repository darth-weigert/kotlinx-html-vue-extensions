package dw.html.vue

external interface Record<K, T>

external interface Directive

external interface ComponentPublicInstance

typealias VoidFunction = () -> Unit

external class DebuggerEvent {
    var effect: Any
    var target: Any
    var type: String
    var key: Any
    var newValue: Any?
    var oldValue: Any?
    var oldTarget: Any?
}

typealias DebuggerHook = (e: DebuggerEvent) -> Unit

external interface LegacyOptions {
    operator fun get(key: String): Any?
    var compatConfig: Any?
    var data: Any?
    var computed: Any?
    var methods: Any?
    var watch: Record<String, Any>?
    var provide: Any?
    var inject: Any?
    var filters: Record<String, Function<Any>>?
    var mixins: Any?
    var extends: Any?
    var beforeCreate: VoidFunction?
    var created: VoidFunction?
    var beforeMount: VoidFunction?
    var mounted: VoidFunction?
    var beforeUpdate: VoidFunction?
    var updated: VoidFunction?
    var activated: VoidFunction?
    var deactivated: VoidFunction?
    /** @deprecated use `beforeUnmount` instead */
    var beforeDestroy: VoidFunction?
    var beforeUnmount: VoidFunction?
    /** @deprecated use `unmounted` instead */
    var destroyed: VoidFunction?
    var unmounted: VoidFunction?
    var renderTracked: DebuggerHook?
    var renderTriggered: DebuggerHook?
    var errorCaptured: Any?
    /**
     * runtime compile only
     * @deprecated use `compilerOptions.delimiters` instead.
     */
    var delimiters: Array<String>?
}

external interface RuntimeCompilerOptions {
    var isCustomElement: ((tag: String) -> Boolean)?
    var whitespace: String?
    var comments: Boolean?
    var delimiters: Array<String>?
}

external interface ComponentOptionsBase: LegacyOptions {
    var setup: (() -> Any)?
    var name: String?
    var template: Any?
    var render: Any?
    var components: Record<String, ComponentOptionsBase>?
    var directives: Record<String, Directive>?
    var inheritAttrs: Boolean?
    var emits: Any?
    var slots: Any?
    var expose: Array<String>?
    var serverPrefetch: Any?
    var compilerOptions: RuntimeCompilerOptions?
}

external interface FunctionalComponent {
    var props: Any?
    var emits: Any?
    var slots: Any?
    var inheritAttrs: Boolean?
    var displayName: String?
    var compatConfig: Any?
}

external interface Component: ComponentOptionsBase, FunctionalComponent

external interface AppConfig {
    val isNativeTag: ((tag: String) -> Boolean)?
    var performance: Boolean
    var optionMergeStrategies: Record<String, Any /*OptionMergeFunction*/>
//    globalProperties: ComponentCustomProperties & Record<string, any>
//    errorHandler?: (err: unknown, instance: ComponentPublicInstance | null, info: string) => void;
//    warnHandler?: (msg: string, instance: ComponentPublicInstance | null, trace: string) => void;
    /**
     * Options to pass to `@vue/compiler-dom`.
     * Only supported in runtime compiler build.
     */
//    compilerOptions: RuntimeCompilerOptions;
    /**
     * @deprecated use config.compilerOptions.isCustomElement
     */
//    isCustomElement?: (tag: string) => boolean;
    /**
     * Temporary config for opt-in to unwrap injected refs.
     * @deprecated this no longer has effect. 3.3 always unwraps injected refs.
     */
//    unwrapInjectedRef?: boolean;
}

external interface App {
    var version: String
    var config: AppConfig
    //    use<Options extends unknown[]>(plugin: Plugin<Options>, ...options: Options): this;
//    use<Options>(plugin: Plugin<Options>, options: Options): this;
//    mixin(mixin: ComponentOptions): this;
//    component(name: string): Component | undefined;
//    component(name: string, component: Component): this;
//    directive(name: string): Directive | undefined;
//    directive(name: string, directive: Directive): this;
    fun <HostElement> mount(rootContainer: HostElement, isHydrate: Boolean = definedExternally, isSVG: Boolean = definedExternally): ComponentPublicInstance
    fun mount(rootContainer: String, isHydrate: Boolean = definedExternally, isSVG: Boolean = definedExternally): ComponentPublicInstance
    fun unmount()
//    provide<T>(key: InjectionKey<T> | string, value: T): this;
    /**
     * Runs a function with the app as active instance. This allows using of `inject()` within the function to get access
     * to variables provided via `app.provide()`.
     *
     * @param fn - function to run with the app as active instance
     */
    fun <T> runWithContext(fn: () -> T): T
    /**
     * v2 compat only
     */
//    filter?(name: string): Function | undefined;
//    filter?(name: string, filter: Function): this;
}

external interface VNode
external interface Element
external interface ShadowRoot
