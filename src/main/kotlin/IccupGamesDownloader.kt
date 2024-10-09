package com.flame239

import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import me.tongfei.progressbar.ProgressBar
import java.io.File

class IccupGamesDownloader {
    private val pageTemplate = "https://iccup.com/dota/details/%d.html"

    fun download() {
        val range = IntRange(100, 500/*500000*/).toList()

        ProgressBar.wrap(range, "Downloading games").forEach { i ->
            skrape(HttpFetcher) {
                request {
                    url = pageTemplate.format(i)
                    userAgent = UA
                }

                response {
                    File(GAME_FILENAME.format(i)).writeText(responseBody)
                }
            }
        }
    }
}

fun main() {
    IccupGamesDownloader().download()
}
