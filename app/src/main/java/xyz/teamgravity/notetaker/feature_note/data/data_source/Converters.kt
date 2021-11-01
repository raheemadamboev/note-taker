package xyz.teamgravity.notetaker.feature_note.data.data_source

import androidx.room.TypeConverter
import java.util.*

class Converters {

    @TypeConverter
    fun fromDate(time: Date): Long = time.time

    @TypeConverter
    fun toDate(time: Long): Date = Date(time)
}