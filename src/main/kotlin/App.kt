import csstype.*
import kotlinx.coroutines.async
import react.*
import react.dom.*
import kotlinx.browser.window
import kotlinx.coroutines.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import emotion.react.css
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.h3
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.img

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
    var currentVideo: Video? by useState(null)
    var unwatchedVideos: List<Video> by useState(emptyList())
    var watchedVideos: List<Video> by useState(emptyList())
    var currentAudio = Audio("https://soundcloud.com/nba-youngboy/youngboy-never-broke-again-put?utm_source=clipboard&utm_medium=text&utm_campaign=social_sharing", "Youngboy NBA", "Put It On Me")

    useEffectOnce {
        mainScope.launch {
            unwatchedVideos = fetchVideos()
        }
    }

    Header()
    div {
        css{
            display = Display.flex
            flexDirection = FlexDirection.row
            justifyContent = JustifyContent.spaceBetween
        }
        div {
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
        div{
            css {
                position = Position.relative
//                top = 10.px
//                right = 10.px
                display = Display.flex
                alignItems = AlignItems.start
                gap = 16.px
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


}