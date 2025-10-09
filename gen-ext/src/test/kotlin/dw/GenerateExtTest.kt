package dw

import io.kotest.matchers.equals.shouldBeEqual
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.Test

class GenerateExtTest {

    companion object {
        val expected = """
package dw.html.vue

import kotlin.String
import kotlin.collections.Set
import kotlinx.html.HTMLTag

public fun HTMLTag.ref(statement: String) {
  attributes["ref"] = statement
}

public fun HTMLTag.vueShow(statement: String) {
  attributes["v-show"] = statement
}

public fun HTMLTag.vueHtml(statement: String) {
  attributes["v-html"] = statement
}

public fun HTMLTag.vueFor(statement: String) {
  attributes["v-for"] = statement
}

public fun HTMLTag.vueIf(statement: String) {
  attributes["v-if"] = statement
}

public fun HTMLTag.vueElseIf(statement: String) {
  attributes["v-else-if"] = statement
}

public fun HTMLTag.vueElse() {
  attributes["v-else"] = ""
}

public fun HTMLTag.vueModel(statement: String) {
  attributes["v-model"] = statement
}

public val HTMLTag.vueModel: VueModel
  get() = VueModel(this)

public val HTMLTag.vueBind: VueBind
  get() = VueBind(this)

public val HTMLTag.vueOn: VueOn
  get() = VueOn(this)

public class VueModel(
  private val tag: HTMLTag,
  private val modifiers: Set<String> = LinkedHashSet(),
) {
  public val lazy: VueModel
    get() = with("lazy")

  public val number: VueModel
    get() = with("number")

  public val trim: VueModel
    get() = with("trim")

  public fun `set`(statement: String) {
    tag.attributes["v-model${'$'}{modifiers.joinToString("")}"] = statement
  }

  public fun with(modifier: String): VueModel = VueModel(tag, modifiers + ".${'$'}modifier")

  public fun lazy(statement: String) {
    lazy.set(statement)
  }

  public fun number(statement: String) {
    number.set(statement)
  }

  public fun trim(statement: String) {
    trim.set(statement)
  }
}

public class VueBind(
  private val tag: HTMLTag,
) {
  public operator fun `set`(key: String, statement: String) {
    tag.attributes["v-bind:${'$'}key"] = statement
  }
}

public class VueOn(
  private val tag: HTMLTag,
) {
  public val click: VueOnClick
    get() = VueOnClick(tag)

  public val submit: VueOnSubmit
    get() = VueOnSubmit(tag)

  public val scroll: VueOnScroll
    get() = VueOnScroll(tag)

  public val keyUp: VueOnKey
    get() = VueOnKey(tag, "keyup")

  public val keyDown: VueOnKey
    get() = VueOnKey(tag, "keydown")

  public val blur: VueOnBlur
    get() = VueOnBlur(tag)

  public val focus: VueOnFocus
    get() = VueOnFocus(tag)

  public val mouse: VueOnMouse
    get() = VueOnMouse(tag)

  public operator fun `set`(key: String, statement: String) {
    tag.attributes["v-on:${'$'}key"] = statement
  }

  public fun click(statement: String) {
    click.set(statement)
  }

  public fun submit(statement: String) {
    submit.set(statement)
  }

  public fun scroll(statement: String) {
    scroll.set(statement)
  }

  public fun keyUp(statement: String) {
    keyUp.set(statement)
  }

  public fun keyDown(statement: String) {
    keyDown.set(statement)
  }

  public fun blur(statement: String) {
    blur.set(statement)
  }

  public fun focus(statement: String) {
    focus.set(statement)
  }
}

public abstract class VueOnEvent<T: VueOnEvent<T>>(
  protected val tag: HTMLTag,
  protected val event: String,
  protected val modifiers: Set<String> = LinkedHashSet(),
) {
  /**
   * the click event's propagation will be stopped
   * <a @click.stop="doThis"></a>
   */
  public val stop: T
    get() = with("stop")

  /**
   * the submit event will no longer reload the page
   * <form @submit.prevent="onSubmit"></form>
   */
  public val prevent: T
    get() = with("prevent")

  /**
   * only trigger handler if event.target is the element itself
   * i.e. not from a child element
   * <div @click.self="doThat">...</div>
   */
  public val self: T
    get() = with("self")

  /**
   * use capture mode when adding the event listener
   * i.e. an event targeting an inner element is handled here before being handled by that element
   * <div @click.capture="doThis">...</div>
   */
  public val capture: T
    get() = with("capture")

  /**
   * the click event will be triggered at most once
   * <a @click.once="doThis"></a>
   */
  public val once: T
    get() = with("once")

  /**
   * the scroll event's default behavior (scrolling) will happen
   * immediately, instead of waiting for `onScroll` to complete
   * in case it contains `event.preventDefault()`
   * <div @scroll.passive="onScroll">...</div>
   */
  public val passive: T
    get() = with("passive")

  public val exact: T
    get() = with("exact")

  public val ctrl: T
    get() = with("ctrl")

  public val alt: T
    get() = with("alt")

  public val shift: T
    get() = with("shift")

  public val meta: T
    get() = with("meta")

  public val altGraph: T
    get() = with("alt-graph")

  public fun `set`(statement: String) {
    tag.attributes["v-on:${'$'}event${'$'}{modifiers.joinToString("")}"] = statement
  }

  protected abstract fun with(modifier: String): T

  public fun stop(statement: String) {
    stop.set(statement)
  }

  public fun prevent(statement: String) {
    prevent.set(statement)
  }

  public fun self(statement: String) {
    self.set(statement)
  }

  public fun capture(statement: String) {
    capture.set(statement)
  }

  public fun once(statement: String) {
    once.set(statement)
  }

  public fun passive(statement: String) {
    passive.set(statement)
  }

  public fun exact(statement: String) {
    exact.set(statement)
  }

  public fun ctrl(statement: String) {
    ctrl.set(statement)
  }

  public fun alt(statement: String) {
    alt.set(statement)
  }

  public fun shift(statement: String) {
    shift.set(statement)
  }

  public fun meta(statement: String) {
    meta.set(statement)
  }

  public fun altGraph(statement: String) {
    altGraph.set(statement)
  }
}

public class VueOnSubmit(
  tag: HTMLTag,
  modifiers: Set<String> = LinkedHashSet(),
) : VueOnEvent<VueOnSubmit>(tag, "submit", modifiers) {
  override fun with(modifier: String): VueOnSubmit = VueOnSubmit(tag, modifiers + ".${'$'}modifier")
}

public class VueOnScroll(
  tag: HTMLTag,
  modifiers: Set<String> = LinkedHashSet(),
) : VueOnEvent<VueOnScroll>(tag, "scroll", modifiers) {
  override fun with(modifier: String): VueOnScroll = VueOnScroll(tag, modifiers + ".${'$'}modifier")
}

public class VueOnClick(
  tag: HTMLTag,
  modifiers: Set<String> = LinkedHashSet(),
) : VueOnEvent<VueOnClick>(tag, "click", modifiers) {
  public val left: VueOnClick
    get() = with("left")

  public val right: VueOnClick
    get() = with("right")

  public val middle: VueOnClick
    get() = with("middle")

  override fun with(modifier: String): VueOnClick = VueOnClick(tag, modifiers + ".${'$'}modifier")

  public fun left(statement: String) {
    left.set(statement)
  }

  public fun right(statement: String) {
    right.set(statement)
  }

  public fun middle(statement: String) {
    middle.set(statement)
  }
}

public class VueOnKey(
  tag: HTMLTag,
  event: String,
  modifiers: Set<String> = LinkedHashSet(),
) : VueOnEvent<VueOnKey>(tag, event, modifiers) {
  public val enter: VueOnKey
    get() = with("enter")

  public val tab: VueOnKey
    get() = with("tab")

  public val delete: VueOnKey
    get() = with("delete")

  public val esc: VueOnKey
    get() = with("esc")

  public val space: VueOnKey
    get() = with("space")

  public val up: VueOnKey
    get() = with("up")

  public val down: VueOnKey
    get() = with("down")

  public val left: VueOnKey
    get() = with("left")

  public val right: VueOnKey
    get() = with("right")

  public val pageDown: VueOnKey
    get() = with("page-down")

  public val a: VueOnKey
    get() = with("a")

  public val b: VueOnKey
    get() = with("b")

  public val c: VueOnKey
    get() = with("c")

  public val d: VueOnKey
    get() = with("d")

  public val e: VueOnKey
    get() = with("e")

  public val f: VueOnKey
    get() = with("f")

  public val g: VueOnKey
    get() = with("g")

  public val h: VueOnKey
    get() = with("h")

  public val i: VueOnKey
    get() = with("i")

  public val j: VueOnKey
    get() = with("j")

  public val k: VueOnKey
    get() = with("k")

  public val l: VueOnKey
    get() = with("l")

  public val m: VueOnKey
    get() = with("m")

  public val n: VueOnKey
    get() = with("n")

  public val o: VueOnKey
    get() = with("o")

  public val p: VueOnKey
    get() = with("p")

  public val q: VueOnKey
    get() = with("q")

  public val r: VueOnKey
    get() = with("r")

  public val s: VueOnKey
    get() = with("s")

  public val t: VueOnKey
    get() = with("t")

  public val u: VueOnKey
    get() = with("u")

  public val v: VueOnKey
    get() = with("v")

  public val w: VueOnKey
    get() = with("w")

  public val x: VueOnKey
    get() = with("x")

  public val y: VueOnKey
    get() = with("y")

  public val z: VueOnKey
    get() = with("z")

  public val F1: VueOnKey
    get() = with("F1")

  public val F2: VueOnKey
    get() = with("F2")

  public val F3: VueOnKey
    get() = with("F3")

  public val F4: VueOnKey
    get() = with("F4")

  public val F5: VueOnKey
    get() = with("F5")

  public val F6: VueOnKey
    get() = with("F6")

  public val F7: VueOnKey
    get() = with("F7")

  public val F8: VueOnKey
    get() = with("F8")

  public val F9: VueOnKey
    get() = with("F9")

  public val F10: VueOnKey
    get() = with("F10")

  public val F11: VueOnKey
    get() = with("F11")

  public val F12: VueOnKey
    get() = with("F12")

  public val F13: VueOnKey
    get() = with("F13")

  public val F14: VueOnKey
    get() = with("F14")

  public val F15: VueOnKey
    get() = with("F15")

  public val F16: VueOnKey
    get() = with("F16")

  public val F17: VueOnKey
    get() = with("F17")

  public val F18: VueOnKey
    get() = with("F18")

  public val F19: VueOnKey
    get() = with("F19")

  public val F20: VueOnKey
    get() = with("F20")

  public val keypad0: VueOnKey
    get() = with("0")

  public val keypad1: VueOnKey
    get() = with("1")

  public val keypad2: VueOnKey
    get() = with("2")

  public val keypad3: VueOnKey
    get() = with("3")

  public val keypad4: VueOnKey
    get() = with("4")

  public val keypad5: VueOnKey
    get() = with("5")

  public val keypad6: VueOnKey
    get() = with("6")

  public val keypad7: VueOnKey
    get() = with("7")

  public val keypad8: VueOnKey
    get() = with("8")

  public val keypad9: VueOnKey
    get() = with("9")

  public val decimal: VueOnKey
    get() = with("decimal")

  public val add: VueOnKey
    get() = with("add")

  public val multiply: VueOnKey
    get() = with("multiply")

  public val clear: VueOnKey
    get() = with("clear")

  public val divide: VueOnKey
    get() = with("divide")

  public val subtract: VueOnKey
    get() = with("subtract")

  public val separator: VueOnKey
    get() = with("separator")

  override fun with(modifier: String): VueOnKey = VueOnKey(tag, event, modifiers + ".${'$'}modifier")

  public fun enter(statement: String) {
    enter.set(statement)
  }

  public fun tab(statement: String) {
    tab.set(statement)
  }

  public fun delete(statement: String) {
    delete.set(statement)
  }

  public fun esc(statement: String) {
    esc.set(statement)
  }

  public fun space(statement: String) {
    space.set(statement)
  }

  public fun up(statement: String) {
    up.set(statement)
  }

  public fun down(statement: String) {
    down.set(statement)
  }

  public fun left(statement: String) {
    left.set(statement)
  }

  public fun right(statement: String) {
    right.set(statement)
  }

  public fun pageDown(statement: String) {
    pageDown.set(statement)
  }

  public fun a(statement: String) {
    a.set(statement)
  }

  public fun b(statement: String) {
    b.set(statement)
  }

  public fun c(statement: String) {
    c.set(statement)
  }

  public fun d(statement: String) {
    d.set(statement)
  }

  public fun e(statement: String) {
    e.set(statement)
  }

  public fun f(statement: String) {
    f.set(statement)
  }

  public fun g(statement: String) {
    g.set(statement)
  }

  public fun h(statement: String) {
    h.set(statement)
  }

  public fun i(statement: String) {
    i.set(statement)
  }

  public fun j(statement: String) {
    j.set(statement)
  }

  public fun k(statement: String) {
    k.set(statement)
  }

  public fun l(statement: String) {
    l.set(statement)
  }

  public fun m(statement: String) {
    m.set(statement)
  }

  public fun n(statement: String) {
    n.set(statement)
  }

  public fun o(statement: String) {
    o.set(statement)
  }

  public fun p(statement: String) {
    p.set(statement)
  }

  public fun q(statement: String) {
    q.set(statement)
  }

  public fun r(statement: String) {
    r.set(statement)
  }

  public fun s(statement: String) {
    s.set(statement)
  }

  public fun t(statement: String) {
    t.set(statement)
  }

  public fun u(statement: String) {
    u.set(statement)
  }

  public fun v(statement: String) {
    v.set(statement)
  }

  public fun w(statement: String) {
    w.set(statement)
  }

  public fun x(statement: String) {
    x.set(statement)
  }

  public fun y(statement: String) {
    y.set(statement)
  }

  public fun z(statement: String) {
    z.set(statement)
  }

  public fun F1(statement: String) {
    F1.set(statement)
  }

  public fun F2(statement: String) {
    F2.set(statement)
  }

  public fun F3(statement: String) {
    F3.set(statement)
  }

  public fun F4(statement: String) {
    F4.set(statement)
  }

  public fun F5(statement: String) {
    F5.set(statement)
  }

  public fun F6(statement: String) {
    F6.set(statement)
  }

  public fun F7(statement: String) {
    F7.set(statement)
  }

  public fun F8(statement: String) {
    F8.set(statement)
  }

  public fun F9(statement: String) {
    F9.set(statement)
  }

  public fun F10(statement: String) {
    F10.set(statement)
  }

  public fun F11(statement: String) {
    F11.set(statement)
  }

  public fun F12(statement: String) {
    F12.set(statement)
  }

  public fun F13(statement: String) {
    F13.set(statement)
  }

  public fun F14(statement: String) {
    F14.set(statement)
  }

  public fun F15(statement: String) {
    F15.set(statement)
  }

  public fun F16(statement: String) {
    F16.set(statement)
  }

  public fun F17(statement: String) {
    F17.set(statement)
  }

  public fun F18(statement: String) {
    F18.set(statement)
  }

  public fun F19(statement: String) {
    F19.set(statement)
  }

  public fun F20(statement: String) {
    F20.set(statement)
  }

  public fun keypad0(statement: String) {
    keypad0.set(statement)
  }

  public fun keypad1(statement: String) {
    keypad1.set(statement)
  }

  public fun keypad2(statement: String) {
    keypad2.set(statement)
  }

  public fun keypad3(statement: String) {
    keypad3.set(statement)
  }

  public fun keypad4(statement: String) {
    keypad4.set(statement)
  }

  public fun keypad5(statement: String) {
    keypad5.set(statement)
  }

  public fun keypad6(statement: String) {
    keypad6.set(statement)
  }

  public fun keypad7(statement: String) {
    keypad7.set(statement)
  }

  public fun keypad8(statement: String) {
    keypad8.set(statement)
  }

  public fun keypad9(statement: String) {
    keypad9.set(statement)
  }

  public fun decimal(statement: String) {
    decimal.set(statement)
  }

  public fun add(statement: String) {
    add.set(statement)
  }

  public fun multiply(statement: String) {
    multiply.set(statement)
  }

  public fun clear(statement: String) {
    clear.set(statement)
  }

  public fun divide(statement: String) {
    divide.set(statement)
  }

  public fun subtract(statement: String) {
    subtract.set(statement)
  }

  public fun separator(statement: String) {
    separator.set(statement)
  }
}

public class VueOnBlur(
  tag: HTMLTag,
  modifiers: Set<String> = LinkedHashSet(),
) : VueOnEvent<VueOnBlur>(tag, "blur", modifiers) {
  override fun with(modifier: String): VueOnBlur = VueOnBlur(tag, modifiers + ".${'$'}modifier")
}

public class VueOnFocus(
  tag: HTMLTag,
  modifiers: Set<String> = LinkedHashSet(),
) : VueOnEvent<VueOnFocus>(tag, "focus", modifiers) {
  override fun with(modifier: String): VueOnFocus = VueOnFocus(tag, modifiers + ".${'$'}modifier")
}

public class VueOnMouse(
  private val tag: HTMLTag,
) {
  public val down: VueOnMouseEvent
    get() = VueOnMouseEvent(tag, "mousedown")

  public val enter: VueOnMouseEvent
    get() = VueOnMouseEvent(tag, "mouseenter")

  public val leave: VueOnMouseEvent
    get() = VueOnMouseEvent(tag, "mouseleave")

  public val move: VueOnMouseEvent
    get() = VueOnMouseEvent(tag, "mousemove")

  public val `out`: VueOnMouseEvent
    get() = VueOnMouseEvent(tag, "mouseout")

  public val over: VueOnMouseEvent
    get() = VueOnMouseEvent(tag, "mouseover")

  public val up: VueOnMouseEvent
    get() = VueOnMouseEvent(tag, "mouseup")

  public fun down(statement: String) {
    down.set(statement)
  }

  public fun enter(statement: String) {
    enter.set(statement)
  }

  public fun leave(statement: String) {
    leave.set(statement)
  }

  public fun move(statement: String) {
    move.set(statement)
  }

  public fun `out`(statement: String) {
    out.set(statement)
  }

  public fun over(statement: String) {
    over.set(statement)
  }

  public fun up(statement: String) {
    up.set(statement)
  }
}

public class VueOnMouseEvent(
  tag: HTMLTag,
  event: String,
  modifiers: Set<String> = LinkedHashSet(),
) : VueOnEvent<VueOnMouseEvent>(tag, event, modifiers) {
  override fun with(modifier: String): VueOnMouseEvent = VueOnMouseEvent(tag, event, modifiers +
      ".${'$'}modifier")
}

        """.trimIndent()
    }
    lateinit var memory: ByteArrayOutputStream



    @Test
    fun generateAndValidate() {
        val result = redirectOutput { main(arrayOf()) }
        result shouldBeEqual expected
    }

    fun redirectOutput(body: () -> Unit): String {
        val original: PrintStream = System.out
        memory = ByteArrayOutputStream()
        System.setOut(PrintStream(memory))
        try {
            body()
        } finally {
            System.setOut(original)
        }
        return memory.toString("utf-8")
    }
}
