package component

import kotlinx.html.InputType
import kotlinx.html.id
import react.*
import react.dom.input

fun RBuilder.redactLesson() =
    child(functionalComponent<RProps> {
        input(InputType.text){
            attrs{
                placeholder = "Enter lesson name"
                id = "Lesson"
            }
        }
    }){

    }