package ir.seometer.app.data

import android.content.Context
import ir.seometer.app.model.HistoryItem
import org.json.JSONArray
import org.json.JSONObject

class HistoryStore(context: Context){
    private val sp=context.getSharedPreferences("seo_history",Context.MODE_PRIVATE)
    fun load():List<HistoryItem>{ val a=JSONArray(sp.getString("items","[]")); return (0 until a.length()).map{a.getJSONObject(it)}.map{HistoryItem(it.getString("url"),it.getInt("score"),it.getLong("createdAt"))} }
    fun add(item:HistoryItem){ val list=(listOf(item)+load().filterNot{it.url==item.url}).take(20); val a=JSONArray(); list.forEach{a.put(JSONObject().put("url",it.url).put("score",it.score).put("createdAt",it.createdAt))}; sp.edit().putString("items",a.toString()).apply() }
}
