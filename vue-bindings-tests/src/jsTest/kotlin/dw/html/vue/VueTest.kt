package dw.html.vue

import io.kotest.matchers.shouldBe
import kotlinx.html.div
import kotlinx.html.p
import org.w3c.dom.HTMLParagraphElement
import vue.testUtils.ComponentMountingOptions
import vue.testUtils.mount
import kotlin.js.json
import kotlin.test.Test

class VueTest {
    @Test
    fun helloWorld() {
        val msg = "new message"
        val component = create<Component> {
            props = arrayOf("msg")
            template = buildTemplate {
                div {
                    +"{{ msg }}"
                }
            }
        }
        println(js("JSON.stringify(component)"))
        val options = create<ComponentMountingOptions<Any>> {
            props = json("msg" to msg)
        }

        val wrapper = mount(component, options)
        wrapper.text() shouldBe msg
    }

    @Test
    fun forLoop() {
        val list = arrayOf("A", "B", "C")
        val component = create<Component> {
            props = arrayOf("list")
            template = buildTemplate {
                p {
                    vueFor("item in list")
                    +"Paragraph {{ item }}"
                }
            }
        }
        println(js("JSON.stringify(component)"))
        val options = create<ComponentMountingOptions<Any>> {
            props = json("list" to list)
        }

        val wrapper = mount(component, options)
        val nodes = wrapper.findAll<HTMLParagraphElement>("p")
        nodes.size shouldBe 3
        nodes[0].text() shouldBe "Paragraph A"
        nodes[1].text() shouldBe "Paragraph B"
        nodes[2].text() shouldBe "Paragraph C"
    }
}
