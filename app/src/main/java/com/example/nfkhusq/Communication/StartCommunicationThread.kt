package com.example.nfkhusq.Communication

import android.bluetooth.BluetoothSocket
import androidx.glance.GlanceId
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.logging.Handler

fun startCommunicationThread(socket: BluetoothSocket, db: AppDatabase) {
    CoroutineScope(Dispatchers.IO).launch {
        val input = socket.inputStream
        val output = socket.outputStream
        val buffer = ByteArray(1024)
        var numBytes: Int
        try {
            while (true) {
                numBytes = input.read(buffer)
                val readMsg = String(buffer, 0, numBytes)
                db.messageDao().insertMessage(Message(content = readMsg))
            }
        } catch (e: IOException) {
            Timber.e("Input stream was disconnected", e)
        } finally {
            try {
                input.close()
                output.close()
                socket.close()
            } catch (e: IOException) {
                Timber.e("Error when closing the socket or streams")
            }
        }
    }
}


@Entity
data class Message(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "content") val content: String
)

@Dao
interface MessageDao {
    @Insert
    suspend fun insertMessage(message: Message)
}
@Database(entities = [Message::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
}
