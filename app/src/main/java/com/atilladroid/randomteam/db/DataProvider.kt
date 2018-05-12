package com.atilladroid.randomteam.db

import android.content.Context
import com.atilladroid.randomteam.beans.Player
import com.atilladroid.randomteam.game.PlayerType
import com.atilladroid.randomteam.utils.Functions
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.util.*

/**
 * Created by sergey on 5/11/18.
 */
class DataProvider(val context: Context) {
    val db = DataBase.getInstance(context)
    val gson = GsonBuilder().setPrettyPrinting().create()

    val locale : String = Locale.getDefault().language.toLowerCase()

    //we only need to use locales for which we have a translate
    val usefullLocale = when(locale) {
        "ru" -> locale
        else -> "en"
    }

    fun getPlayers(type: PlayerType = PlayerType.STANDARD): List<Player> {
        return db.playersDao().getPlayersByType(type, locale = usefullLocale)
    }


    fun getPlayer(id: Long) = db.playersDao().getPlayerById(id)

    fun insertPlayer(player: Player): Long {
        return db.playersDao().insertPlayer(player)
    }

    fun getListOfAvatars(): List<String> {
        val jsonList = Functions.readJsonFromAssets(context, "files.json")

        val fileNameList: List<String> = gson.fromJson(jsonList, object : TypeToken<List<String>>() {}.type)
        return fileNameList
    }
}