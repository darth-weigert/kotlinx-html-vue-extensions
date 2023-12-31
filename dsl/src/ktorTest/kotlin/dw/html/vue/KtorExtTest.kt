package dw.html.vue

import io.kotest.matchers.shouldBe
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import io.ktor.utils.io.charsets.*
import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import kotlin.test.Test

class KtorExtTest {
    @Test
    fun vueBind() = testApplication {
        bodyFixture {
            a {
                vueBind["href"] = "variable"
            }
        }

        val response = client.get("/vue")

        response.status shouldBe HttpStatusCode.OK
        bodyOf(response) shouldBe "<a v-bind:href=\"variable\"></a>"
    }

    @Test
    fun vueClickCtrlLeft() = testApplication {
        bodyFixture {
            button {
                vueOn.click.ctrl.left("callback")
            }
        }

        val response = client.get("/vue")

        response.status shouldBe HttpStatusCode.OK
        bodyOf(response) shouldBe "<button v-on:click.ctrl.left=\"callback\"></button>"
    }

    @Test
    fun vueClickAltMiddle() = testApplication {
        bodyFixture {
            button {
                vueOn.click.alt.middle("callback")
            }
        }

        val response = client.get("/vue")

        response.status shouldBe HttpStatusCode.OK
        bodyOf(response) shouldBe "<button v-on:click.alt.middle=\"callback\"></button>"
    }

    @Test
    fun vueClickShiftRight() = testApplication {
        bodyFixture {
            button {
                vueOn.click.shift.right("callback")
            }
        }

        val response = client.get("/vue")

        response.status shouldBe HttpStatusCode.OK
        bodyOf(response) shouldBe "<button v-on:click.shift.right=\"callback\"></button>"
    }

    @Test
    fun vueKeyUpExactEnter() = testApplication {
        bodyFixture {
            button {
                vueOn.keyUp.exact.enter("callback")
            }
        }

        val response = client.get("/vue")

        response.status shouldBe HttpStatusCode.OK
        bodyOf(response) shouldBe "<button v-on:keyup.exact.enter=\"callback\"></button>"
    }

    @Test
    fun vueKeyDownStopCaptureCtrlA() = testApplication {
        bodyFixture {
            button {
                vueOn.keyDown.stop.capture.ctrl.a("callback")
            }
        }

        val response = client.get("/vue")

        response.status shouldBe HttpStatusCode.OK
        bodyOf(response) shouldBe "<button v-on:keydown.stop.capture.ctrl.a=\"callback\"></button>"
    }

    @Test
    fun vueModelLazyNumberTrim() = testApplication {
        bodyFixture {
            input {
                vueModel.lazy.number.trim("variable")
            }
        }

        val response = client.get("/vue")

        response.status shouldBe HttpStatusCode.OK
        bodyOf(response) shouldBe "<input v-model.lazy.number.trim=\"variable\">"
    }

    @Test
    fun vueIfElseIfElse() = testApplication {
        bodyFixture {
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

        val response = client.get("/vue")

        response.status shouldBe HttpStatusCode.OK
        bodyOf(response) shouldBe "<p v-if=\"condition1\">First</p>" +
                    "<p v-else-if=\"condition2\">Second</p>" +
                    "<p v-else=\"\">Third</p>"
    }

    @Test
    fun vueSubmitPrevent() = testApplication {
        bodyFixture {
            form {
                vueOn.submit.prevent("submit")
                input {
                    type = InputType.email
                    vueModel("form.email")
                }
                textArea {
                    vueModel("form.description")
                }
                select {
                    vueModel("form.city")
                    option {
                        value = "new-york"
                        +"New York"
                    }
                    option {
                        value = "moscow"
                        +"Moscow"
                    }
                }
                input {
                    type = InputType.checkBox
                    vueModel("form.subscribe")
                }
                input {
                    type = InputType.radio
                    value = "weekly"
                    vueModel("form.interval")
                }
                input {
                    type = InputType.radio
                    value = "monthly"
                    vueModel("form.interval")
                }
                button {
                    type = ButtonType.submit
                    +"Submit"
                }
            }
        }

        val response = client.get("/vue")

        response.status shouldBe HttpStatusCode.OK
        bodyOf(response) shouldBe "<form v-on:submit.prevent=\"submit\">" +
                    "<input type=\"email\" v-model=\"form.email\">" +
                    "<textarea v-model=\"form.description\"></textarea>" +
                    "<select v-model=\"form.city\">" +
                    "<option value=\"new-york\">New York</option>" +
                    "<option value=\"moscow\">Moscow</option>" +
                    "</select>" +
                    "<input type=\"checkbox\" v-model=\"form.subscribe\">" +
                    "<input type=\"radio\" value=\"weekly\" v-model=\"form.interval\">" +
                    "<input type=\"radio\" value=\"monthly\" v-model=\"form.interval\">" +
                    "<button type=\"submit\">Submit</button>" +
                    "</form>"
    }

    @Test
    fun vueOnBlurOnFocus() = testApplication {
        bodyFixture {
            input {
                vueOn.blur("blurHandler")
                vueOn.focus("focusHandler")
            }
        }

        val response = client.get("/vue")

        response.status shouldBe HttpStatusCode.OK
        bodyOf(response) shouldBe "<input v-on:blur=\"blurHandler\" v-on:focus=\"focusHandler\">"
    }

    private fun ApplicationTestBuilder.bodyFixture(block: BODY.() -> Unit) {
        application {
            routing {
                get("/vue") {
                    call.respondHtml {
                        body(block = block)
                    }
                }
            }
        }
    }

}

suspend fun ApplicationCall.respondHtml(status: HttpStatusCode = HttpStatusCode.OK, block: HTML.() -> Unit) {
    val text = buildString {
        append("<!DOCTYPE html>\n")
        appendHTML(prettyPrint = false).html(block = block)
    }
    respond(TextContent(text, ContentType.Text.Html.withCharset(Charsets.UTF_8), status))
}

suspend fun bodyOf(response: HttpResponse): String {
    val document = response.bodyAsText()
    return document.substringAfter("<body>").substringBefore("</body>").trim()
}
