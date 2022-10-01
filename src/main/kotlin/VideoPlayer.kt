import csstype.*
import react.*
import emotion.react.css
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h3
import kotlinx.browser.window

external interface VideoPlayerProps : Props {
    var video: Video
    var onWatchedButtonPressed: (Video) -> Unit
    var unwatchedVideo: Boolean
}



val VideoPlayer = FC<VideoPlayerProps> { props ->

    val (screenWidth, handleResize) = useState<Int>(window.outerWidth)
    window.addEventListener("resize",{handleResize(window.outerWidth)})

    div {
        css {
            position = Position.relative
        }
        h3 {
            +"${props.video.speaker}: ${props.video.title}"
        }
        button {
            css {
                display = Display.block
                backgroundColor = if (props.unwatchedVideo) NamedColor.lightgreen else NamedColor.red
            }
            onClick = {
                props.onWatchedButtonPressed(props.video)
            }
            if (props.unwatchedVideo) {
                +"Mark as watched"
            } else {
                +"Mark as unwatched"
            }
        }
        div {
            css {
                position = Position.absolute
                top = 10.px
                right = 10.px
            }
            EmailShareButton {
                url = props.video.videoUrl
                EmailIcon {
                    size = 32
                    round = true
                }
            }
            TelegramShareButton {
                url = props.video.videoUrl
                TelegramIcon {
                    size = 32
                    round = true
                }
            }
        }
        ReactPlayer {
            url = props.video.videoUrl
            controls = true
            width = if (screenWidth <= 767) 90.vw else 40.vw
        }

    }
}