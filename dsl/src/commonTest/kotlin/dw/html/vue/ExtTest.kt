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

    @Test
    fun vueOnMouseClick() {
        val result = htmlString {
            button {
                vueOn.click("clickHandler")
                +"Click me!"
            }
        }

        result shouldBe "<button v-on:click=\"clickHandler\">Click me!</button>"
    }

    @Test
    fun vueOnSubmit() {
        val result = htmlString {
            form {
                vueOn.submit("submitHandler")
            }
        }

        result shouldBe "<form v-on:submit=\"submitHandler\"></form>"
    }

    @Test
    fun vueOnKeyUpKeyDown() {
        val result = htmlString {
            input {
                vueOn.keyUp("keyUpHandler")
                vueOn.keyDown("keyDownHandler")
            }
        }

        result shouldBe "<input v-on:keyup=\"keyUpHandler\" v-on:keydown=\"keyDownHandler\">"
    }

    private fun htmlString(block: TagConsumer<*>.() -> Unit): String {
        return buildString {
            block(appendHTML(prettyPrint = false))
        }
    }
}
