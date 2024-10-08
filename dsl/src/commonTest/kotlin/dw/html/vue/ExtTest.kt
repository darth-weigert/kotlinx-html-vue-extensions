package dw.html.vue

import io.kotest.matchers.shouldBe
import kotlin.test.Test
import kotlinx.html.*
import kotlinx.html.stream.appendHTML

class ExtTest {

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

    @Test
    fun vueOnMouseOverMouseOut() {
        val result = htmlString {
            input {
                vueOn.mouse.over("mouseOverHandler")
                vueOn.mouse.out("mouseOutHandler")
            }
        }

        result shouldBe "<input v-on:mouseover=\"mouseOverHandler\" v-on:mouseout=\"mouseOutHandler\">"
    }

    private fun htmlString(block: TagConsumer<*>.() -> Unit): String {
        return buildString {
            block(appendHTML(prettyPrint = false))
        }
    }
}
