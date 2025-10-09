package dw.html.vue

external interface Record<K, T> {
    operator fun get(key: Any): T
    operator fun set(key: Any, value: T?)
}

typealias Data = Record<String, Any>

external interface Directive

external interface ComponentPublicInstance

typealias VoidFunction = () -> Unit

typealias ComputedGetter<T> = () -> T

typealias WatchEffect = (onCleanup: OnCleanup) -> Unit

typealias OnCleanup = (cleanupFun: () -> Unit) -> Unit

typealias Slot<T> = (args: dynamic) -> Array<VNode>
external interface InternalSlots {
    operator fun get(name: String): Slot<Any>?
    operator fun set(name: String, value: Slot<Any>?)
}
external interface Slots {
    operator fun get(name: String): Slot<Any>?
}
external interface SchedulerJob {
    var id: Number?
    var pre: Boolean?
    var active: Boolean?
    var computed: Boolean?
    /**
     * Indicates whether the effect is allowed to recursively trigger itself
     * when managed by the scheduler.
     *
     * By default, a job cannot trigger itself because some built-in method calls,
     * e.g. Array.prototype.push actually performs reads as well (#1740) which
     * can lead to confusing infinite loops.
     * The allowed cases are component update functions and watch callbacks.
     * Component update functions may update child component props, which in turn
     * trigger flush: "pre" watch callbacks that mutates state that the parent
     * relies on (#1801). Watch callbacks doesn't track its dependencies so if it
     * triggers itself again, it's likely intentional and it is the user's
     * responsibility to perform recursive state mutation that eventually
     * stabilizes (#1727).
     */
    var allowRecurse: Boolean?
    /**
     * Attached by renderer.ts when setting up a component's render effect
     * Used to obtain component information when reporting max recursive updates.
     * dev only.
     */
    var ownerInstance: ComponentInternalInstance?
}

external interface ComponentInternalInstance {
    var uid: Number
    var type: dynamic // ConcreteComponent
    var parent: ComponentInternalInstance?
    var root: ComponentInternalInstance
    var appContext: dynamic // AppContext
    /**
     * Vnode representing this component in its parent's vdom tree
     */
    var vnode: VNode
    /* removed internal: next */
    /**
     * Root vnode of this component's own vdom tree
     */
    var subTree: VNode
    /**
     * Render effect instance
     */
    var effect: ReactiveEffect<Any>
    /**
     * Bound effect runner to be passed to schedulers
     */
    var update: SchedulerJob
    /* removed internal: render */
    /* removed internal: ssrRender */
    /* removed internal: provides */
    /* removed internal: scope */
    /* removed internal: accessCache */
    /* removed internal: renderCache */
    /* removed internal: components */
    /* removed internal: directives */
    /* removed internal: filters */
    /* removed internal: propsOptions */
    /* removed internal: emitsOptions */
    /* removed internal: inheritAttrs */
    /* removed internal: isCE */
    /* removed internal: ceReload */
    var proxy: ComponentPublicInstance?
    var exposed: Record<String, Any>?
    var exposeProxy: Record<String, Any>?
    /* removed internal: withProxy */
    /* removed internal: ctx */
    var data: Data
    var props: Data
    var attrs: Data
    var slots: InternalSlots
    var refs: Data
    var emit: dynamic // EmitFn
    var attrsProxy: Data?
    var slotsProxy: Slots?
    /* removed internal: emitted */
    /* removed internal: propsDefaults */
    /* removed internal: setupState */
    /* removed internal: devtoolsRawSetupState */
    /* removed internal: setupContext */
    /* removed internal: suspense */
    /* removed internal: suspenseId */
    /* removed internal: asyncDep */
    /* removed internal: asyncResolved */
    var isMounted: Boolean
    var isUnmounted: Boolean
    var isDeactivated: Boolean














    /* removed internal: f */
    /* removed internal: n */
    /* removed internal: ut */
}

external interface WatchOptionsBase : DebuggerOptions {
    var flush: String? // 'pre' | 'post' | 'sync';
}

typealias WatchStopHandle = () -> Unit

external interface DebuggerOptions {
    var onTrack: ((event: DebuggerEvent) -> Unit)?
    var onTrigger: ((event: DebuggerEvent) -> Unit)?
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
    // setup?: (this: void, props: LooseRequired<Props & Prettify<UnwrapMixinsType<IntersectionMixin<Mixin> & IntersectionMixin<Extends>, 'P'>>>, ctx: SetupContext<E, S>) => Promise<RawBindings> | RawBindings | RenderFunction | void;
    var setup: ((props: dynamic, ctx: Any) -> Any)?
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
    var props: dynamic
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
    fun component(name: String, component: Component): App
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
