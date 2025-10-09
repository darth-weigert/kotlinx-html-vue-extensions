package dw.html.vue

@JsModule("vue")
@JsNonModule
@JsName("default")
external object Vue {

    fun defineCustomElement(component: ComponentOptionsBase): () -> dynamic

    fun createApp(component: Component, rootProps: Data? = definedExternally): App

    fun <T> isRef(r: Ref<T>): dynamic // r is Ref<T>

    fun <T> ref(value: T): Ref<T>

    fun <T> computed(getter: ComputedGetter<T>): ComputedRef<T>

    fun watchEffect(effect: WatchEffect, options: WatchOptionsBase? = definedExternally): WatchStopHandle
    fun watchPostEffect(effect: WatchEffect, options: DebuggerOptions? = definedExternally): WatchStopHandle
    fun watchSyncEffect(effect: WatchEffect, options: DebuggerOptions? = definedExternally): WatchStopHandle

    fun onBeforeMount(hook: () -> Any, target: ComponentInternalInstance? = definedExternally): dynamic // false | Function | undefined;
    fun onMounted(hook: () -> Any, target: ComponentInternalInstance? = definedExternally): dynamic // false | Function | undefined;
    fun onBeforeUpdate(hook: () -> Any, target: ComponentInternalInstance? = definedExternally): dynamic // false | Function | undefined;
    fun onUpdated(hook: () -> Any, target: ComponentInternalInstance? = definedExternally): dynamic // false | Function | undefined;
    fun onBeforeUnmount(hook: () -> Any, target: ComponentInternalInstance? = definedExternally): dynamic // false | Function | undefined;
    fun onUnmounted(hook: () -> Any, target: ComponentInternalInstance? = definedExternally): dynamic // false | Function | undefined;
    fun onServerPrefetch(hook: () -> Any, target: ComponentInternalInstance? = definedExternally): dynamic // false | Function | undefined;
}
