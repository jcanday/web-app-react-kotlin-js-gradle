import csstype.*
import kotlinx.coroutines.async
import react.*
import kotlinx.browser.window
import kotlinx.coroutines.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import emotion.react.css
import react.dom.html.ReactHTML.h3
import react.dom.html.ReactHTML.div


suspend fun fetchVideo(id: Int): Video {
    val response = window
        .fetch("https://my-json-server.typicode.com/kotlin-hands-on/kotlinconf-json/videos/$id")
        .await()
        .text()
        .await()
    return Json.decodeFromString(response)
}

suspend fun fetchVideos(): List<Video> = coroutineScope {
    (1..25).map { id ->
        async {
            fetchVideo(id)
        }
    }.awaitAll()
}

val mainScope = MainScope()

val App = FC<Props> {
    val (screenWidth, handleResize) = useState<Int>(window.outerWidth)
    var currentVideo: Video? by useState(null)
    var unwatchedVideos: List<Video> by useState(emptyList())
    var watchedVideos: List<Video> by useState(emptyList())
    val currentAudio = Audio("https://soundcloud.com/producersnafu/producer-snafu-fall-back?utm_source=clipboard&utm_medium=text&utm_campaign=social_sharing", "Fallback", "Producer Snafu")
    useEffectOnce {
        mainScope.launch {
            unwatchedVideos = fetchVideos()
        }
    }

    window.addEventListener("resize",{handleResize(window.outerWidth)})
    Header()

    div {
        css{
            display = Display.flex
            flexDirection = FlexDirection.columnReverse
            backgroundColor = Color("#FFFFFF50")
            backgroundBlendMode = BlendMode.overlay
            backgroundSize = BackgroundSize.cover
            backgroundImage = url("https://picsum.photos/1980/800")
            alignItems = AlignItems.center
        }

        div {
            css{
                display = Display.flex
                flexDirection = if (screenWidth <= 767) FlexDirection.column else FlexDirection.row
                justifyContent = JustifyContent.spaceAround
                width = 90.vw
            }
            div{
                h3 {
                    +"Videos to watch"
                }
                VideoList {
                    videos = unwatchedVideos
                    selectedVideo = currentVideo
                    onSelectVideo = { video ->
                        currentVideo = video
                    }
                }
            }
            div {
                h3 {
                    +"Videos watched"
                }
                VideoList {
                    videos = watchedVideos
                    selectedVideo = currentVideo
                    onSelectVideo = { video ->
                        currentVideo = video
                    }
                }
            }

        }
        div{
            css {
                display = Display.flex
                alignItems = AlignItems.start
                gap = 16.px
                flexDirection = if (screenWidth <= 767) FlexDirection.columnReverse else FlexDirection.row
            }
            AudioPlayer{
                audio = currentAudio
            }
            currentVideo?.let { curr ->
                VideoPlayer {
                    video = curr
                    unwatchedVideo = curr in unwatchedVideos
                    onWatchedButtonPressed = {
                        if (video in unwatchedVideos) {
                            unwatchedVideos = unwatchedVideos - video
                            watchedVideos = watchedVideos + video
                        } else {
                            watchedVideos = watchedVideos - video
                            unwatchedVideos = unwatchedVideos + video
                        }
                    }
                }
            }

        }
    }
    Footer()
}