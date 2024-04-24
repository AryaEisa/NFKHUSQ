package com.example.nfkhusq.Connection

import android.bluetooth.BluetoothDevice
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import java.time.Instant

@Entity
data class StoredDevice(
    @PrimaryKey val address: String,
    val name: String,
    val lastConnected: Long,
    val isConnected: Boolean
)
data class BluetoothDeviceItem(
    val device: BluetoothDevice,
    var lastSeen: Instant,
    var isConnected: Boolean = false
)
@Dao
interface DeviceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDevice(device: StoredDevice)

    @Query("SELECT * FROM StoredDevice WHERE isConnected = 1")
    fun getConnectedDevices(): List<StoredDevice>
}
