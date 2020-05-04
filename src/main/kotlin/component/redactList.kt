package component


import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.*
import react.dom.br
import react.dom.button
import react.dom.h2
import react.dom.p

interface redactListProps<O>: RProps{
    var elements: Array<O>
    var Add: (Event) -> Unit
    var Delete: (Event) -> Unit
    var name: String
    var path: String
}


fun <O> fRedactList(
    rObj : RBuilder.() -> ReactElement,
    rComponent: RBuilder.(Array<O>, String, String)->ReactElement
) =
    functionalComponent<redactListProps<O>> {props ->
       h2{+"Redact List"}
           p{
               +"Redact ${props.name}"
               br {  }
               rObj()
               button {
                   +"Add"
                   attrs.onClickFunction = props.Add
               }
               button {
                   +"Delete"
                   attrs.onClickFunction = props.Delete
               }
               rComponent(props.elements, props.name, props.path)
           }

    }

fun <O> RBuilder.redactList(
    rComponent: RBuilder.(Array<O>, String, String) -> ReactElement,
    rObj: RBuilder.() -> ReactElement,
    elements: Array<O>,
    Add: (Event) -> Unit,
    Delete: (Event) -> Unit,
    name: String,
    path: String
) = child(fRedactList(rObj, rComponent)){
    attrs.elements=elements
    attrs.Add=Add
    attrs.Delete=Delete
    attrs.name=name
    attrs.path=path
}
