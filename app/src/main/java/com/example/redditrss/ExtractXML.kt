package com.example.redditrss

import android.util.Log

class ExtractXML(var tag: String?, var xml: String?) {

    val TAG: String = "ExtractXML"

    fun start(): ArrayList<String?> {
        val result = ArrayList<String?>()

        val splitXML: List<String>? = xml?.split(tag + "\"")   // <a href =" , ........
        val count = splitXML?.size ?: 1
        for (i in 1 until count) {
            var temp = splitXML?.get(i)
            val index: Int = temp?.indexOf("\"") ?: 0
            Log.d(TAG, "index of double quotation" + index)   // find next double quote
            Log.d(TAG, "extracted" + temp)
            temp = temp?.substring(0, index)
            Log.d(TAG, "start : snipped " + temp)
            result.add(temp)
        }

        return result
    }
}