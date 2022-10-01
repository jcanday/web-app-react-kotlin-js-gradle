import csstype.*
import react.*
import emotion.react.css
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.p

val Footer = FC<Props> {
    div {
        css{
            backgroundColor = NamedColor.black
            color = NamedColor.white
            textAlign = TextAlign.center
            paddingTop = 16.px
            paddingBottom = 16.px
            display = Display.flex
            justifyContent = JustifyContent.spaceAround
            alignItems = AlignItems.center
        }
        p{
            +"Youtube"
        }
        p{
            +"Kotlin"
        }
        p{
            +"SoundCloud"
        }
    }
}