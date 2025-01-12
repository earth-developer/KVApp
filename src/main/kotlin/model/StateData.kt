package model

import EThemeColor
import LText
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.google.gson.GsonBuilder
import ecompose.utils.sl.DpOffsetAdapter
import ecompose.utils.sl.DpSizeAdapter
import ecompose.utils.sl.SLData

data class WindowsSLData(
    var position: DpOffset = DpOffset(0f.dp, 0f.dp),
    var size: DpSize = DpSize(400f.dp, 600f.dp),
    var groupWidth: Float = 100f,
    var titleWeight: Float = 1f,
    var keyWeight: Float = 1f,
    var valueWeight: Float = 1f,
    var currentThemeOrdinal: Int = EThemeColor.Theme.Blue.ordinal,
    var currentLanguageOrdinal:Int = LText.Language.English.ordinal,
) : SLData() {
    constructor() : this(DpOffset(0f.dp, 0f.dp), DpSize(400f.dp, 600f.dp))

    override fun toJson(): String {
        val gson = GsonBuilder()
            .registerTypeAdapter(DpOffset::class.java, DpOffsetAdapter())
            .registerTypeAdapter(DpSize::class.java, DpSizeAdapter())
            .create()
        return gson.toJson(this)
    }

    override fun fromJson(json: String) {
        val gson = GsonBuilder()
            .registerTypeAdapter(DpOffset::class.java, DpOffsetAdapter())
            .registerTypeAdapter(DpSize::class.java, DpSizeAdapter())
            .create()
        val windowsData = gson.fromJson(json, WindowsSLData::class.java)
        position = windowsData.position
        size = windowsData.size
        groupWidth = windowsData.groupWidth
        titleWeight = windowsData.titleWeight
        keyWeight = windowsData.keyWeight
        valueWeight = windowsData.valueWeight
        currentThemeOrdinal = windowsData.currentThemeOrdinal
        currentLanguageOrdinal = windowsData.currentLanguageOrdinal
    }

    companion object {
        const val KEY = "windows_state"
    }
}

data class KVSLData(
    var groups: MutableList<Group> = mutableListOf(),
    var kvItems: MutableList<KVItem> = mutableListOf()
) : SLData() {
    constructor() : this(mutableListOf(), mutableListOf())

    companion object {
        const val KEY = "kv_data"
    }

    override fun toJson(): String {
        return GsonBuilder().create().toJson(this)
    }

    override fun fromJson(json: String) {
        val kvData = GsonBuilder().create().fromJson(json, KVSLData::class.java)
        groups = kvData.groups
        kvItems = kvData.kvItems
    }
}

