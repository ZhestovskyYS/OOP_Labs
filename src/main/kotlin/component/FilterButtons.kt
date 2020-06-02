package component

import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.button

interface ButtonsProps: RProps{
    var onClick: () -> Unit
}

class FilterButtons(props: ButtonsProps) : RComponent <ButtonsProps, RState>(props){
    override fun RBuilder.render() {
        button {
            attrs.onClickFunction = { props.onClick() }
            children()
        }
    }
}
