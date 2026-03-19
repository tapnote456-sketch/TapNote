package uk.ac.tees.mad.tapnote.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uk.ac.tees.mad.tapnote.data.local.dao.NoteDao
import uk.ac.tees.mad.tapnote.data.local.entity.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 1
)
abstract class TapNoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {

        @Volatile
        private var INSTANCE: TapNoteDatabase? = null

        fun getInstance(context: Context): TapNoteDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    TapNoteDatabase::class.java,
                    "tapnote_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
