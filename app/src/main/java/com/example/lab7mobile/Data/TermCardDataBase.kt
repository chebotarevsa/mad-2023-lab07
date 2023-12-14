import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.lab7mobile.Data.BitmapConverter
import com.example.lab7mobile.Data.TermCard
import com.example.lab7mobile.Data.TermCardDao

@Database(entities = [TermCard::class], version = 1)
@TypeConverters(BitmapConverter::class)
abstract class TermCardDataBase : RoomDatabase() {

    abstract fun termCardDao(): TermCardDao

    companion object {
        @Volatile
        private var INSTANCE: TermCardDataBase? = null

        fun getDatabase(context: Context): TermCardDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TermCardDataBase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}