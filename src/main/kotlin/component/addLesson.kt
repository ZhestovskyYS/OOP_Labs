package component

import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.button
import react.dom.input
import kotlin.browser.document

interface newLessonProps: RProps{
    var inputLessons: (String) -> Unit
}

fun RBuilder.addingLesson(inputLesson: (String) -> Unit) =
    child(functionalComponent<newLessonProps> {props ->
        input(InputType.text){
            attrs{
                id = "newLesson"
            }
        }
        button {
            +"Add"
            attrs.onClickFunction={
                val lessonName = document.getElementById("newLesson") as HTMLInputElement
                props.inputLessons(lessonName.value)
            }
        }
    }){
        attrs.inputLessons=inputLesson
    }