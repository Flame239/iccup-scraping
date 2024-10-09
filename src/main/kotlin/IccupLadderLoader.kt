package com.flame239

import com.clickhouse.client.api.Client
import it.skrape.core.htmlDocument
import it.skrape.selects.html5.div
import java.io.File

data class Player(val rank: String, val nick: String, val profileLink: String, val page: Int) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Player) return false
        return nick == other.nick
    }

    override fun hashCode() = nick.hashCode()
}

private val players = mutableSetOf<Player>()

class IccupLadderLoader {
    fun parseAndLoad() {
//        parsePlayers()
        load()
    }

    fun parsePlayers() {
        for (i in 1..1000) {
            htmlDocument(File(LADDER_FILENAME.format(i)).readText()) {
                div {
                    withClass = "t-corp3"
                    findAll {
                        drop(1).forEach { e ->
                            val rank = e.children[1].children[1].className
                            val nick = e.children[2].children.last().text
                            val profileLink = e.children[2].children.last().attributes["href"]!!
                            players.add(Player(rank, nick, profileLink, i))
                        }
                    }
                }
            }
        }

        println(players.filter { it.nick.isNotBlank() }.size)
    }

    fun load() {
        val client = Client.Builder()
            .addEndpoint("http://localhost:8123/")
            .setUsername("default")
            .setPassword("")
            .setDefaultDatabase("iccup")
            .build()

        client.execute("CREATE TABLE IF NOT EXISTS players (rank String, nick String, profileLink String, page Int) ENGINE = MergeTree() PRIMARY KEY nick")

        client.register(Player::class.java, client.getTableSchema("players"))

//        val response = client.insert("players", players.filter { it.nick.isNotBlank() }).get(1, TimeUnit.SECONDS)
//        println(response)

        client.queryAll("SELECT * FROM players limit 10").forEach {
            println(it.getString("nick"))
        }
    }
}
