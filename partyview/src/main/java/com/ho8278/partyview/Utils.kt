package com.ho8278.partyview

import android.app.Activity
import android.content.Context
import android.content.res.Resources

object Utils {
    fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    fun getScreenHeight():Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }
}