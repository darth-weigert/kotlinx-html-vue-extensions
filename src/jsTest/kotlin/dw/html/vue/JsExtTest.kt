package dw.html.vue

import io.kotest.matchers.shouldBe
import kotlin.test.Test
import kotlinx.html.*
import kotlinx.html.consumers.DelayedConsumer
import kotlinx.html.stream.HTMLStreamBuilder

class JsExtTest {

    @Test
    fun vueIfElseIfElse() {
        val result = htmlString {
            p {
                vueIf("condition1")
                +"First"
            }
            p {
                vueElseIf("condition2")
                +"Second"
            }
            p {
                vueElse()
                +"Third"
            }
        }
        result shouldBe "<p v-if=\"condition1\">First</p>" +
                "<p v-else-if=\"condition2\">Second</p>" +
                "<p v-else=\"\">Third</p>"
    }

    private fun htmlString(block: TagConsumer<*>.() -> Unit): String {
        val text = StringBuilder()
        val stream = DelayedConsumer(HTMLStreamBuilder(text, false, false))
        block.invoke(stream)
        return text.toString()
    }
}
