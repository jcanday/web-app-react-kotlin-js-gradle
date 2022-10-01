@file:JsModule("react-player")
@file:JsNonModule

import csstype.Width
import react.*

@JsName("default")
external val ReactPlayer: ComponentClass<ReactPlayerProps>

external interface ReactPlayerProps : Props {
    var url: String
    var controls: Boolean
    var width: Width
}