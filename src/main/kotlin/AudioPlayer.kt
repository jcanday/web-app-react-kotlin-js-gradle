import csstype.*
import react.*
import emotion.react.css
import react.dom.html.ReactHTML.h3
import react.dom.html.ReactHTML.div
external interface AudioPlayerProps : Props {
    var audio: Audio
}

val AudioPlayer = FC<AudioPlayerProps> { props ->
    div {
        h3 {
            css {
                paddingBottom = 2.rem
            }
            +"${props.audio.artist}: ${props.audio.title}"
        }
        ReactPlayer {
            url = props.audio.audioUrl
            controls = true
        }
    }
}