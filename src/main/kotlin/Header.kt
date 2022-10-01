import csstype.*
import react.*
import emotion.react.css
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1

val Header = FC<Props> {
    div {
        css{
            backgroundColor = NamedColor.black
            color = NamedColor.white
            textAlign = TextAlign.center
            paddingTop = 16.px
            paddingBottom = 16.px
        }
        h1 {
            +"Hello, React+Kotlin/JS!"
        }
    }
}