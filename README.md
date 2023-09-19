# Vue.js extensions for ktor-server-html-builder

This library adds vue.js extensions to ktor's html dsl.

This code:

```kotlin
fun Application.configureTemplating() {
    routing {
        get("/html-vue-dsl") {
            call.respondHtml {
                body {
                    div {
                        id = "app"
                        label {
                            htmlFor = "secret"
                            +"Enter secret word:"
                        }
                        input {
                            id = "secret"
                            type = InputType.text
                            vueModel("text")
                        }
                        p {
                            vueIf("show")
                            +"{{ message }}"
                        }
                        p {
                            vueElseIf("almost")
                            +"Almost there!"
                        }
                        p {
                            vueElse()
                            +"Secret message not showing"
                        }
                    }
                    script(type = "application/javascript", src = "https://unpkg.com/vue@3/dist/vue.global.js") {}
                    script(type = "application/javascript") {
                        unsafe {
                            +"""
const { createApp, ref, computed } = Vue

createApp({
  setup() {
    const secretWord = 'vue';
    const text = ref('');
    const message = ref('Hello Vue.js!');
    const show = computed(() => text.value === secretWord);
    const almost = computed(() => text.value.length > 0 && secretWord.startsWith(text.value));
    return {
      text,
      message,
      show,
      almost,
    }
  }
}).mount('#app')
"""
                        }
                    }
                }
            }
        }
    }
}
```

Will generate the following html:

```html
<!DOCTYPE html>
<html>
  <body>
    <div id="app"><label for="secret">Enter secret word:</label><input id="secret" type="text" v-model="text">
      <p v-if="show">{{ message }}</p>
      <p v-else-if="almost">Almost there!</p>
      <p v-else="">Secret message not showing</p>
    </div>
    <script type="application/javascript" src="https://unpkg.com/vue@3/dist/vue.global.js"></script>
    <script type="application/javascript">
const { createApp, ref, computed } = Vue

createApp({
  setup() {
    const secretWord = 'vue';
    const text = ref('');
    const message = ref('Hello Vue.js!');
    const show = computed(() => text.value === secretWord);
    const almost = computed(() => text.value.length > 0 && secretWord.startsWith(text.value));
    return {
      text,
      message,
      show,
      almost,
    }
  }
}).mount('#app')
</script>
  </body>
</html>
```

## Basic syntax

Two way model binding (`v-model`):

```kotlin
input {
    type = InputType.text
    vueModel("text")
}
```

Click handling (`v-on:click`):

```kotlin
button {
    vueOn.click("callback")
}
```

On keyboard events (`v-on:keydown` and `v-on:keyup`):

```kotlin
input {
    type = InputType.text
    vueOn.keyDown.enter("callback")
    vueOn.keyUp.delete("callback")
}
```

On focus lost (`v-on:blur`):

```kotlin
input {
    type = InputType.text
    vueOn.blur("console.log('Focus lost!')")
}
```

And for `v-bind`, we have a special syntax:

```kotlin
a {
    vueBind["href"] = "variable"
}
```

## Modifiers

You can chain modifiers after he main binding:

```kotlin
form {
    vueOn.submit.prevent("submit")
}

button {
    vueOn.click.ctrl.left("callback")
}

input {
    vueOn.keyDown.enter.alt("console.log('alt+enter')")
    vueOn.keyUp.altGraph.delete.exact("console.log('altGraph+delete')")
}
```

## Building

To build this project, you can run:

```shell
./gradlew build
```

It uses kotlinpoet to generate the Ext.kt file. 

## Test coverage
To view test coverage, run:

```shell
./gradlew clean build allTests jacocoTestReport
```

And the reports will be available at:

* build/reports/jacoco/test/html/index.html
* build/reports/jacoco/test/jacocoTestReport.xml

## Publishing

To publish to the local repository, run:

```shell
./gradlew publishToMavenLocal
```
