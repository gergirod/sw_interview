package ger.girod.interview.ui.utils

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.view.inputmethod.InputMethodManager

fun hideKeyBoard(context: Activity?) {
    val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm!!.hideSoftInputFromWindow(context?.window?.decorView?.windowToken, 0)
}

fun isIntenteSafe(context: Activity?, intent: Intent) : Boolean {
    val activities: List<ResolveInfo> = context?.packageManager!!.queryIntentActivities(
        intent,
        PackageManager.MATCH_DEFAULT_ONLY
    )
   return activities.isNotEmpty()
}