package dw.html.vue

import io.kotest.matchers.shouldBe
import kotlinx.html.div
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
        val options = create<ComponentMountingOptions<Any>> {
            props = json("msg" to msg)
        }

        val wrapper = mount(component, options)
        wrapper.text() shouldBe msg
    }
}
