package com.atilladroid.randomteam.beans

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import com.atilladroid.randomteam.db.Contract
import com.atilladroid.randomteam.game.PlayerType

/**
 * Created by sergey on 5/11/18.
 */

@Entity(tableName = Contract.PLAYER_TABLE,
        indices = [(Index(value = Contract.PLAYER_NAME, unique = true))])
class Player(@ColumnInfo(name = Contract.PLAYER_NAME) var name: String = "Nameless",
             @ColumnInfo(name = Contract.PLAYER_LOCALE) var locale: String = "en",
             @PrimaryKey(autoGenerate = true) @ColumnInfo(name = Contract.PLAYER_ID) var id: Long = 0,
             @ColumnInfo(name = Contract.PLAYER_AVATAR) var avatar: String = "",
             @ColumnInfo(name = Contract.PLAYER_TYPE) var type: PlayerType = PlayerType.USER
) {

    override fun toString(): String {
        return "Player $name id $id "
    }

    override fun equals(other: Any?): Boolean {
        if (other is Player) {
            return other.id == id
        } else return false
    }
}