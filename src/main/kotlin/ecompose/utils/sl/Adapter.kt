package ecompose.utils.sl

import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter


class DpOffsetAdapter : TypeAdapter<DpOffset>() {
    override fun write(out: JsonWriter, value: DpOffset) {
        out.beginObject()
        out.name("x").value(value.x.value)
        out.name("y").value(value.y.value)
        out.endObject()
    }

    override fun read(`in`: JsonReader): DpOffset {
        var x = 0f
        var y = 0f
        `in`.beginObject()
        while (`in`.hasNext()) {
            when (`in`.nextName()) {
                "x" -> x = `in`.nextDouble().toFloat()
                "y" -> y = `in`.nextDouble().toFloat()
            }
        }
        `in`.endObject()
        return DpOffset(x.dp, y.dp)
    }
}

class DpSizeAdapter : TypeAdapter<DpSize>() {
    override fun write(out: JsonWriter, value: DpSize) {
        out.beginObject()
        out.name("width").value(value.width.value)
        out.name("height").value(value.height.value)
        out.endObject()
    }

    override fun read(`in`: JsonReader): DpSize {
        var width = 0f
        var height = 0f
        `in`.beginObject()
        while (`in`.hasNext()) {
            when (`in`.nextName()) {
                "width" -> width = `in`.nextDouble().toFloat()
                "height" -> height = `in`.nextDouble().toFloat()
            }
        }
        `in`.endObject()
        return DpSize(width.dp, height.dp)
    }
}
