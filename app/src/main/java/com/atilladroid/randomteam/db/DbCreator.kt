package com.atilladroid.randomteam.db

import android.content.Context
import android.os.Environment
import com.atilladroid.randomteam.beans.Player
import com.atilladroid.randomteam.game.PlayerType
import com.atilladroid.randomteam.utils.Functions
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import timber.log.Timber
import java.io.BufferedReader
import java.io.FileWriter
import java.io.InputStreamReader
import java.util.*

/**
 * Created by sergey on 5/12/18.
 */

object DbCreator {

    fun createPlayers(dataBase: DataBase, context: Context) {
        val players = dataBase.playersDao().getAllPlayers()
        if (players.isEmpty()) {
            Timber.d("no players add")
            val somePlayersJson = Functions.readJsonFromAssets(context, "players.json")
            val gson = GsonBuilder().create()
            val somePlayersList: List<SomePlayer> = gson.fromJson(somePlayersJson,
                    object : TypeToken<List<SomePlayer>>() {}.type)

            for (somePlayer in somePlayersList) {
                dataBase.playersDao().insertPlayer(
                        Player(name = somePlayer.name,
                                locale = somePlayer.locale,
                                avatar = somePlayer.avatar,
                                type = PlayerType.STANDARD))
            }
        } else {
            Timber.d("Players already exists")
        }
    }

   /* fun createWords(dataBase: DataBase, context: Context) {
        val wordsList = dataBase.wordDao().getAllWords()
        if (wordsList.isNotEmpty()) return


        addWordsFromStream(context, dataBase, "words/easyRu", WordType.EASY, "ru")
        addWordsFromStream(context, dataBase, "words/normalRu", WordType.MEDIUM, "ru")
        addWordsFromStream(context, dataBase, "words/hardRu", WordType.HARD, "ru")
        addWordsFromStream(context, dataBase, "words/veryhardRu", WordType.VERY_HARD, "ru")

        addWordsFromStream(context, dataBase, "words/easyEn", WordType.EASY, "en")
        addWordsFromStream(context, dataBase, "words/mediumEn", WordType.MEDIUM, "en")
        addWordsFromStream(context, dataBase, "words/hardEn", WordType.HARD, "en")
        addWordsFromStream(context, dataBase, "words/veryhardEn", WordType.VERY_HARD, "en")
    }*/

    /*private fun addWordsFromStream(context: Context, dataBase: DataBase, fileName: String,
                                   type: WordType, lang: String) {
        val wordStream = context.assets.open(fileName)
        val reader = BufferedReader(InputStreamReader(wordStream))

        var line: String? = reader.readLine()

        val wordsList : MutableList<String> = mutableListOf()

        while (line != null) {
            Timber.d("load words from asset $line")
            wordsList.add(line)
            line = reader.readLine()
        }

        wordsList.filter { it.isNotEmpty() }
                .forEach {
                    it.trim()
                    dataBase.wordDao().insertWord(Word(it, type = type, lang = lang))
                }


    }*/

    fun writeArray(context: Context, name: String, arrayId: Int) {
        val array = context.resources.getStringArray(arrayId)
        val list = array.toList().filterNotNull()
        Collections.shuffle(list)

        val shortList = list.subList(0, 500).sorted()
        val fileWriter = FileWriter("${Environment.getExternalStorageDirectory()}/$name")
        shortList.forEach {
            fileWriter.write("$it \n")
        }

        fileWriter.close()
    }

    fun sortAndWriteWords(context: Context) {
        var wordStream = context.assets.open("words/easyRu")
        var reader = BufferedReader(InputStreamReader(wordStream))
        sort(reader, "easyRu")

        wordStream = context.assets.open("words/normalRu")
        reader = BufferedReader(InputStreamReader(wordStream))
        sort(reader, "normalRu")

        wordStream = context.assets.open("words/hardRu")
        reader = BufferedReader(InputStreamReader(wordStream))
        sort(reader, "hardRu")

        wordStream = context.assets.open("words/veryhardRu")
        reader = BufferedReader(InputStreamReader(wordStream))
        sort(reader, "veryhardRu")

        wordStream = context.assets.open("words/easyEn")
        reader = BufferedReader(InputStreamReader(wordStream))
        sort(reader, "easyEn")

        wordStream = context.assets.open("words/mediumEn")
        reader = BufferedReader(InputStreamReader(wordStream))
        sort(reader, "mediumEn")

        wordStream = context.assets.open("words/hardEn")
        reader = BufferedReader(InputStreamReader(wordStream))
        sort(reader, "hardEn")

        wordStream = context.assets.open("words/veryhardEn")
        reader = BufferedReader(InputStreamReader(wordStream))
        sort(reader, "veryhardEn")
    }

    private fun sort(reader: BufferedReader, name: String) {
        val list = mutableListOf<String>()
        var line: String? = reader.readLine()

        while (line != null) {
            list.add("$line\n")
            line = reader.readLine()
        }

        list.sort()

        val fileWriter = FileWriter("${Environment.getExternalStorageDirectory()}/$name")
        list.forEach { fileWriter.write(it) }

        fileWriter.close()
    }
}

data class SomePlayer(val name: String, val locale: String, val avatar: String)