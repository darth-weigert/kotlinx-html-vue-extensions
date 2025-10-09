package dw.html.vue

external interface Ref<T> {
    var value: T
}

external interface ComputedRef<T> {
    val value: T
    val effect: ReactiveEffect<T>
}

external class EffectScope {
    var detached: Boolean
    /* removed internal: _active */
    /* removed internal: effects */
    /* removed internal: cleanups */
    /* removed internal: parent */
    /* removed internal: scopes */
    /* removed internal: index */
    constructor(detached: Boolean?)
    val active: Boolean
    fun <T> run(fn: () -> T): T?
    /* removed internal: on */
    /* removed internal: off */
    fun stop(fromParent: Boolean?): Unit
}

typealias EffectScheduler = (args: Array<Any>) -> Any

external class DebuggerEvent {
    var effect: ReactiveEffect<Any>
    var target: Any
    var type: String
    var key: Any
    var newValue: Any?
    var oldValue: Any?
    var oldTarget: Any?
}

external class ReactiveEffect<T> {
    var fn: () -> T
    var scheduler: dynamic // EffectScheduler | null
    var active: Boolean
    var deps: dynamic // Dep[]
    var parent: ReactiveEffect<Any>?
    /* removed internal: computed */
    /* removed internal: allowRecurse */
    /* removed internal: deferStop */
    var onStop: (() -> Unit)?
    var onTrack: ((event: DebuggerEvent) -> Unit)?
    var onTrigger: ((event: DebuggerEvent) -> Unit)?
    constructor(fn: () -> T, scheduler: EffectScheduler?, scope: EffectScope?)
    fun run(): T?
    fun stop(): Unit
}
