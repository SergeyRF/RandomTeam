package com.atilladroid.randomteam.utils

import android.app.Activity
import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import timber.log.Timber
import java.io.IOException
import java.text.DateFormat
import java.util.*

/**
 * Created by sergey on 5/11/18.
 */
object Functions {

    fun timeToLocalDateWithTime(time: Long, context: Context): String {
        val date = Date(time)
        val dateFormat: DateFormat = android.text.format.DateFormat.getDateFormat(context)
        val dateString =  dateFormat.format(date)

        val timeFormat = android.text.format.DateFormat.getTimeFormat(context)
        val timeString = timeFormat.format(time)

        return "$dateString $timeString"
    }



    fun hideKeyboard(context: Context, focusableView: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromInputMethod(focusableView.windowToken, 0)
    }

    fun showKeyboard(context: Context, focusableView: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(focusableView, InputMethodManager.SHOW_IMPLICIT)
    }

    fun imageNameToUrl(name: String): String {

        return "file:///android_asset/$name"
    }

    fun readJsonFromAssets(context: Context, filePath: String): String {
        var result = "[]"

        try {
            val inputStream = context.assets.open(filePath)
            val size = inputStream.available()
            val buffer = ByteArray(size)

            inputStream.read(buffer)
            inputStream.close()
            result = String(buffer)
        } catch (ex: IOException) {
            Timber.e(ex)
        }

        return result
    }

   /* fun setTheme(activity: Activity) {
        val preferences = PreferenceHelper.defaultPrefs(activity)
        val themeRes: Int = preferences[Constants.THEME_PREF] ?: R.style.AppTheme

        activity.setTheme(themeRes)
    }

    fun selectTheme(theme: Int, activity: Activity) {
        val preferences = PreferenceHelper.defaultPrefs(activity)
        val oldTheme = preferences[Constants.THEME_PREF] ?: R.style.AppTheme

        if(theme != oldTheme) {
            preferences[Constants.THEME_PREF] = theme
            activity.recreate()
        }
    }

    fun getSelectedThemeId(context: Context) : Int {
        val preferences = PreferenceHelper.defaultPrefs(context)
        return preferences[Constants.THEME_PREF] ?: R.style.AppTheme
    }*/

    fun getScreenWidth(context: Context): Int {
        val display = context.resources.displayMetrics
        return display.widthPixels
    }

    fun dpToPx(context: Context, dp : Float) : Float {
        val r = context.getResources()
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics())
    }
}