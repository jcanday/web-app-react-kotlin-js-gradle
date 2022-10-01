import csstype.*
import react.*
import emotion.react.css
import kotlinx.browser.window
import react.dom.html.ReactHTML.h3
import react.dom.html.ReactHTML.div
external interface AudioPlayerProps : Props {
    var audio: Audio
}

val AudioPlayer = FC<AudioPlayerProps> { props ->
    val (screenWidth, handleResize) = useState<Int>(window.outerWidth)
    window.addEventListener("resize",{handleResize(window.outerWidth)})

    div {
        h3 {
            css {
                paddingBottom = 1.3.rem
            }
            +"${props.audio.artist}: ${props.audio.title}"
        }
        ReactPlayer {
            url = props.audio.audioUrl
            controls = true
            width = if (screenWidth <= 767) 90.vw else 40.vw
        }
    }
}