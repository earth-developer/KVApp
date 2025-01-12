package ecompose.utils.sl
import com.google.gson.Gson

abstract class SLData {
    abstract fun toJson(): String
    abstract fun fromJson(json: String)
}

object SLDataManager {
    private var saveDirName = "state"
    val dataMap = mutableMapOf<String, SLData>()

    fun register(key: String, data: SLData) {
        dataMap[key] = data
    }

    fun get(key: String): SLData? {
        return dataMap[key]
    }

    fun setAndSave(key: String, data: SLData) {
        dataMap[key] = data
        save()
    }

    fun save() {
        val gson = Gson()
        val dir = java.io.File(saveDirName)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val file = java.io.File("$saveDirName/$saveDirName.json")
        if (!file.exists()) {
            file.createNewFile()
        }
        val json = mutableMapOf<String, Any>()
        dataMap.forEach { (key, data) ->
            json[key] = gson.fromJson(data.toJson(), Map::class.java)
        }
        file.writeText(gson.toJson(json))
    }

    fun load() {
        val gson = Gson()
        val file = java.io.File("$saveDirName/$saveDirName.json")
        if (!file.exists()) {
            return
        }
        val json = file.readText()
        val map = gson.fromJson(json, Map::class.java)
        map.forEach { (key, value) ->
            val data = dataMap[key] ?: return@forEach
            data.fromJson(gson.toJson(value))
        }
    }
}