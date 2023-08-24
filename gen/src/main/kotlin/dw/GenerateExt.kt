package dw

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import dw.Names.htmlTagClass
import dw.Names.vueOnClass
import dw.Names.vueBindClass
import dw.Names.vueModelClass
import dw.Names.vueOnClickClass
import dw.Names.vueOnEventClass
import dw.Names.vueOnKeyClass
import dw.Names.vueOnScrollClass
import dw.Names.vueOnSubmitClass
import java.nio.file.Path

object Names {
    val htmlTagClass = ClassName("kotlinx.html", "HTMLTag")

    val vueBindClass = ClassName("dw.ktor.html.vue", "VueBind")
    val vueOnClass = ClassName("dw.ktor.html.vue", "VueOn")
    val vueOnEventClass = ClassName("dw.ktor.html.vue", "VueOnEvent")
    val vueOnClickClass = ClassName("dw.ktor.html.vue", "VueOnClick")
    val vueOnSubmitClass = ClassName("dw.ktor.html.vue", "VueOnSubmit")
    val vueOnScrollClass = ClassName("dw.ktor.html.vue", "VueOnScroll")
    val vueOnKeyClass = ClassName("dw.ktor.html.vue", "VueOnKey")
    val vueModelClass = ClassName("dw.ktor.html.vue", "VueModel")
}

fun FileSpec.Builder.createHtmlTagMethod(name: String, vueAttribute: String = name): FileSpec.Builder {
    return this.addFunction(FunSpec.builder(name)
        .receiver(htmlTagClass)
        .addParameter("statement", String::class)
        .addStatement("attribute[\"%L\"] = statement", vueAttribute)
        .build())
}

fun FileSpec.Builder.createHtmlTagProperty(name: String, type: ClassName): FileSpec.Builder {
    return this.addProperty(PropertySpec.builder(name, type)
        .receiver(htmlTagClass)
        .getter(
                FunSpec.getterBuilder()
                        .addStatement("return %T(this)", type)
                        .build())
        .build())
}

fun TypeSpec.Builder.createConstructorWithTag(): TypeSpec.Builder {
    return this
            .primaryConstructor(
                    FunSpec.constructorBuilder()
                            .addParameter("tag", htmlTagClass)
                            .build()
            )
            .addProperty(
                    PropertySpec.builder("tag", htmlTagClass)
                            .initializer("tag")
                            .addModifiers(KModifier.PRIVATE)
                            .build()
            )
}

fun TypeSpec.Builder.createKeyedSetter(prefix: String): TypeSpec.Builder {
    return this.addFunction(FunSpec.builder("set")
            .addModifiers(KModifier.OPERATOR, KModifier.OVERRIDE)
            .addParameter("key", String::class)
            .addParameter("statement", String::class)
            .addStatement("tag.attributes[\"%L:\$key\"] = statement", prefix)
            .build()
    )
}

fun TypeSpec.Builder.createDslGetterAndFun(name: String, type: ClassName): TypeSpec.Builder {
    return this
            .addProperty(
                    PropertySpec.builder(name, type)
                            .getter(
                                    FunSpec.getterBuilder()
                                            .addStatement("return %T(tag)", type)
                                            .build()
                            )
                            .build()
            )
            .addFunction(
                    FunSpec.builder(name)
                            .addParameter("statement", String::class)
                            .addStatement("%L.set(statement)", name)
                            .build()
            )
}

fun TypeSpec.Builder.createDslGetterAndFun(name: String, event: String, type: ClassName): TypeSpec.Builder {
    return addProperty(
            PropertySpec.builder(name, type)
                    .getter(
                            FunSpec.getterBuilder()
                                    .addStatement("return %T(tag, \"%L\")", type, event)
                                    .build()
                    )
                    .build()
    )
            .addFunction(
                    FunSpec.builder(name)
                            .addParameter("statement", String::class)
                            .addStatement("%L.set(statement)", name)
                            .build()
            )
}

fun TypeSpec.Builder.createDslModifierGetterAndFun(name: String, vueModifier: String = name, comments: String? = null): TypeSpec.Builder {
    val getterProperty = PropertySpec.builder(name, TypeVariableName("T"))
            .getter(
                    FunSpec.getterBuilder()
                            .addStatement("return with(\"%L\")", vueModifier)
                            .build()
            )
    if(comments != null) {
        getterProperty.addKdoc(comments)
    }
    return this
            .addProperty(getterProperty.build())
            .addFunction(
                    FunSpec.builder(name)
                            .returns(TypeVariableName("T"))
                            .addParameter("statement", String::class)
                            .addStatement("%L.set(statement)", name)
                            .build()
            )
}

fun builderOnEvent(type: ClassName): TypeSpec.Builder {
    return TypeSpec.classBuilder(type)
            .superclass(vueOnEventClass.parameterizedBy(type))
            .primaryConstructor(
                    FunSpec.constructorBuilder()
                            .addParameter("tag", htmlTagClass)
                            .addParameter("event", String::class)
                            .addParameter(
                                    ParameterSpec.builder("modifiers", Set::class.parameterizedBy(String::class))
                                            .defaultValue("LinkedHashSet()")
                                            .build()
                            )
                            .build()
            )
            .addSuperclassConstructorParameter("tag")
            .addSuperclassConstructorParameter("event")
            .addSuperclassConstructorParameter("modifiers")
            .addFunction(
                    FunSpec.builder("with")
                            .addModifiers(KModifier.OVERRIDE)
                            .returns(type)
                            .addParameter("modifier", String::class)
                            .addStatement("return %T(tag, event, modifiers + \".\$modifier\")", type)
                            .build()
            )
}

fun builderOnEvent(type: ClassName, event: String): TypeSpec.Builder {
    return TypeSpec.classBuilder(type)
            .superclass(vueOnEventClass.parameterizedBy(type))
            .primaryConstructor(
                    FunSpec.constructorBuilder()
                            .addParameter("tag", htmlTagClass)
                            .addParameter(
                                    ParameterSpec.builder("modifiers", Set::class.parameterizedBy(String::class))
                                            .defaultValue("LinkedHashSet()")
                                            .build()
                            )
                            .build()
            )
            .addSuperclassConstructorParameter("tag")
            .addSuperclassConstructorParameter("\"%L\"", event)
            .addSuperclassConstructorParameter("modifiers")
            .addFunction(
                    FunSpec.builder("with")
                            .addModifiers(KModifier.OVERRIDE)
                            .returns(type)
                            .addParameter("modifier", String::class)
                            .addStatement("return %T(tag, modifiers + \".\$modifier\")", type)
                            .build()
            )
}

fun main(args: Array<String>) {
    val writeOutput: (FileSpec) -> Unit = if (args.size == 2 && args[0] == "--output") {
        val outputPath = Path.of(args[1])
        ({ file ->
            file.writeTo(outputPath)
        })
    } else {
        { file ->
            file.writeTo(System.out)
        }
    }
    val file = FileSpec.builder("dw.ktor.html.vue", "Ext")
            .createHtmlTagMethod("ref")
            .createHtmlTagMethod("vueShow", "v-show")
            .createHtmlTagMethod("vueHtml", "v-html")
            .createHtmlTagMethod("vueFor", "v-for")
            .createHtmlTagMethod("vueIf", "v-if")
            .createHtmlTagMethod("vueElseIf", "v-else-if")
            .addFunction(
                FunSpec.builder("vueElse")
                    .receiver(htmlTagClass)
                    .addStatement("attributes[\"v-else\"] = \"\"")
                    .build()
            )
            .createHtmlTagMethod("vueModel", "v-model")
            .createHtmlTagProperty("vueModel", vueModelClass)
            .createHtmlTagProperty("vueBind", vueBindClass)
            .createHtmlTagProperty("vueOn", vueOnClass)
            .addType(
                    TypeSpec.classBuilder(vueModelClass)
                            .primaryConstructor(
                                    FunSpec.constructorBuilder()
                                            .addParameter("tag", htmlTagClass)
                                            .addParameter(
                                                    ParameterSpec.builder("modifiers", Set::class.parameterizedBy(String::class))
                                                            .defaultValue("LinkedHashSet()")
                                                            .build()
                                            )
                                            .build()
                            )
                            .addFunction(
                                    FunSpec.builder("with")
                                            .returns(vueModelClass)
                                            .addParameter("modifier", String::class)
                                            .addStatement("return %T(tag, modifiers + \".\$modifier\")", vueModelClass)
                                            .build()
                            )
                            .createDslModifierGetterAndFun("lazy")
                            .createDslModifierGetterAndFun("number")
                            .createDslModifierGetterAndFun("trim")
                            .build()
            )
            .addType(
                    TypeSpec.classBuilder(vueBindClass)
                            .createConstructorWithTag()
                            .createKeyedSetter("v-bind")
                            .build()
            )
            .addType(
                    TypeSpec.classBuilder(vueOnClass)
                            .createConstructorWithTag()
                            .createKeyedSetter("v-on")
                            .createDslGetterAndFun("click", vueOnClickClass)
                            .createDslGetterAndFun("submit", vueOnSubmitClass)
                            .createDslGetterAndFun("scroll", vueOnScrollClass)
                            .createDslGetterAndFun("keyUp", "keyup", vueOnKeyClass)
                            .createDslGetterAndFun("keyDown", "keydown", vueOnKeyClass)
                            .build()
            )
            .addType(
                    TypeSpec.classBuilder(vueOnEventClass)
                            .addModifiers(KModifier.ABSTRACT)
                            .addTypeVariable(TypeVariableName("T: VueOnEvent<T>"))
                            .primaryConstructor(
                                    FunSpec.constructorBuilder()
                                            .addParameter("tag", htmlTagClass)
                                            .addParameter("event", String::class)
                                            .addParameter(
                                                    ParameterSpec.builder("modifiers", Set::class.parameterizedBy(String::class))
                                                            .defaultValue("LinkedHashSet()")
                                                            .build()
                                            )
                                            .build()
                            )
                            .addProperty(
                                    PropertySpec.builder("tag", htmlTagClass)
                                            .initializer("tag")
                                            .addModifiers(KModifier.PROTECTED)
                                            .build()
                            )
                            .addProperty(
                                    PropertySpec.builder("event", String::class)
                                            .initializer("event")
                                            .addModifiers(KModifier.PROTECTED)
                                            .build()
                            )
                            .addProperty(
                                    PropertySpec.builder("modifiers", Set::class.parameterizedBy(String::class))
                                            .initializer("modifiers")
                                            .addModifiers(KModifier.PROTECTED)
                                            .build()
                            )
                            .addFunction(
                                    FunSpec.builder("set")
                                            .addParameter("statement", String::class)
                                            .addStatement("tag.attributes[\"v-on:\$event\${modifiers.joinToString(\"\")}\"] = statement")
                                            .build()
                            )
                            .addFunction(
                                    FunSpec.builder("with")
                                            .returns(TypeVariableName("T"))
                                            .addModifiers(KModifier.PROTECTED, KModifier.ABSTRACT)
                                            .addParameter("modifier", String::class)
                                            .build()
                            )
                            .createDslModifierGetterAndFun(name="stop", comments="""
                                |the click event's propagation will be stopped
                                |<a @click.stop="doThis"></a>""".trimMargin())
                            .createDslModifierGetterAndFun(name="prevent", comments="""
                                |the submit event will no longer reload the page
                                |<form @submit.prevent="onSubmit"></form>""".trimMargin())
                            .createDslModifierGetterAndFun(name="self", comments="""only trigger handler if event.target is the element itself
                                |i.e. not from a child element
                                |<div @click.self="doThat">...</div>""".trimMargin())
                            .createDslModifierGetterAndFun(name="capture", comments="""
                                |use capture mode when adding the event listener
                                |i.e. an event targeting an inner element is handled here before being handled by that element
                                |<div @click.capture="doThis">...</div>""".trimMargin())
                            .createDslModifierGetterAndFun(name="once", comments="""
                                |the click event will be triggered at most once
                                |<a @click.once="doThis"></a>""".trimMargin())
                            .createDslModifierGetterAndFun(name="passive", comments="""
                                |the scroll event's default behavior (scrolling) will happen
                                |immediately, instead of waiting for `onScroll` to complete
                                |in case it contains `event.preventDefault()`
                                |<div @scroll.passive="onScroll">...</div>""".trimMargin())
                            .createDslModifierGetterAndFun(name="exact")
                            .createDslModifierGetterAndFun(name="ctrl")
                            .createDslModifierGetterAndFun(name="alt")
                            .createDslModifierGetterAndFun(name="shift")
                            .createDslModifierGetterAndFun(name="meta")
                            .createDslModifierGetterAndFun(name="altGraph", vueModifier="alt-graph")
                            .build()
            )
            .addType(
                    builderOnEvent(vueOnSubmitClass, "submit")
                            .build()
            )
            .addType(
                    builderOnEvent(vueOnScrollClass, "scroll")
                            .build()
            )
            .addType(
                    builderOnEvent(vueOnClickClass, "click")
                            .createDslModifierGetterAndFun("left")
                            .createDslModifierGetterAndFun("right")
                            .createDslModifierGetterAndFun("middle")
                            .build()
            )
            .addType(
                    builderOnEvent(vueOnKeyClass)
                            .createDslModifierGetterAndFun("enter")
                            .createDslModifierGetterAndFun("tag")
                            .createDslModifierGetterAndFun("delete")
                            .createDslModifierGetterAndFun("esc")
                            .createDslModifierGetterAndFun("space")
                            .createDslModifierGetterAndFun("up")
                            .createDslModifierGetterAndFun("down")
                            .createDslModifierGetterAndFun("left")
                            .createDslModifierGetterAndFun("right")
                            .createDslModifierGetterAndFun("pageDown", "page-down")
                            .apply {
                                // regular keys
                                for (character in 'a'..'z') {
                                    createDslModifierGetterAndFun("$character")
                                }
                                // function keys
                                for (n in 1..20) {
                                    createDslModifierGetterAndFun("F$n")
                                }
                                // numeric keypad keys
                                for (k in 0..9) {
                                    createDslModifierGetterAndFun("keypad$k", k.toString())
                                }
                                createDslModifierGetterAndFun("decimal")
                                createDslModifierGetterAndFun("add")
                                createDslModifierGetterAndFun("multiply")
                                createDslModifierGetterAndFun("clear")
                                createDslModifierGetterAndFun("divide")
                                createDslModifierGetterAndFun("subtract")
                                createDslModifierGetterAndFun("separator")
                            }
                            .build()
            )
            .build()

    writeOutput(file)
}

