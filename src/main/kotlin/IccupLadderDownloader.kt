package com.flame239

import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import java.io.File

class IccupLadderDownloader {
    private val pageTemplate = "https://iccup.com/dota/ladder/5x5/page%d.html"

    fun download() {
        for (i in 1..1000) {
            skrape(HttpFetcher) {
                request {
                    url = pageTemplate.format(i)
                    userAgent = UA
                }

                response {
                    File(LADDER_FILENAME.format(i)).writeText(responseBody)
                    if (i % 10 == 0) {
                        println("$i  ${status { code }}")
                    }
                }
            }
        }
    }
}

fun main() {
    IccupLadderDownloader().download()
}
