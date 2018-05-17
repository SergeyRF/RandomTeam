package com.atilladroid.randomteam

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.atilladroid.randomteam.db.DataBase
import com.atilladroid.randomteam.db.DbCreator
import com.atilladroid.randomteam.db.DbExporter
import com.atilladroid.randomteam.game.Logika
import com.atilladroid.randomteam.utils.PreferenceHelper
import timber.log.Timber

/**
 * Created by sergey on 5/12/18.
 */class App : Application() {

    override fun onCreate() {
        super.onCreate()

        /*if(BuildConfig.DEBUG) {
            Timber.plant(TimberDebugTree())
        } else {
            Timber.plant(TimberReleaseTree())
        }*/

        Timber.plant(Timber.DebugTree())

        val namesArray = resources.getStringArray(R.array.teams)
        Logika.teamNames = namesArray.toMutableList()

      DbCreator.createPlayers(DataBase.getInstance(this), this)
//        DbCreator.createWords(DataBase.getInstance(this), this)
//        DbCreator.sortAndWritesWordsFromRes(this)
//        DbCreator.sortAndWriteWords(this)
//        DbCreator.loadFileList(this)
//        DbExporter().exportDbToFile(this, Contract.DB_NAME)

        val preferences = PreferenceHelper.defaultPrefs(this)
        val dbImported = preferences.getBoolean(DB_IMPORTED, false)
        /*if (!dbImported) {
            val success = DbExporter().importDbFromAsset(this, "shlyapa_db")
            preferences[DB_IMPORTED] = success
        } else {
            Timber.d("Db already imported")
        }*/

        buildCaoc()
    }

    fun buildCaoc() {
        //Catch fatal exceptions
        CaocConfig.Builder.create()
                .logErrorOnRestart(true)
                .showErrorDetails(BuildConfig.DEBUG)
                .apply()
    }

    companion object {
        private const val DB_IMPORTED = "db_imported"
    }
}

