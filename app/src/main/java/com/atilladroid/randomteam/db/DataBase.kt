package com.atilladroid.randomteam.db

import android.arch.persistence.room.*
import android.content.Context
import com.atilladroid.randomteam.beans.Player
import com.atilladroid.randomteam.game.PlayerType

/**
 * Created by sergey on 5/11/18.
 */

@Database(entities = arrayOf(Player::class), version = Contract.DB_VERSION)
@TypeConverters( PlayerTypeConverter::class)
abstract class DataBase : RoomDatabase(){

    abstract fun playersDao() : PlayersDao


    companion object {
        private var INSTANCE : DataBase? = null

        fun getInstance(context : Context) : DataBase {
            if(INSTANCE == null) {
                synchronized(DataBase::class) {
                    INSTANCE = Room.databaseBuilder(context, DataBase::class.java, Contract.DB_NAME)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build()
                }
            }

            return INSTANCE!!
        }
    }
}

class PlayerTypeConverter {

    @TypeConverter
    fun typeToString(type: PlayerType): String {
        return type.toString()
    }

    @TypeConverter
    fun stringToType(type: String): PlayerType {
        return when (type) {
            PlayerType.USER.toString() -> PlayerType.USER
            PlayerType.STANDARD.toString() -> PlayerType.STANDARD
            else -> throw RuntimeException("Unsupported type")
        }
    }
}