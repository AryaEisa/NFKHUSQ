package com.example.nfkhusq.Connection

import android.bluetooth.BluetoothClass
import android.bluetooth.BluetoothDevice
import java.time.Instant
import java.util.UUID




// Helper functions to interpret Bluetooth API constants into human-readable strings
fun getBondState(bondState: Int): String {
    return when (bondState) {
        BluetoothDevice.BOND_BONDED -> "Bonded"
        BluetoothDevice.BOND_NONE -> "Not Bonded"
        else -> "Unknown"
    }
}

fun getDeviceType(deviceType: Int): String {
    return when (deviceType) {
        BluetoothDevice.DEVICE_TYPE_CLASSIC -> "Classic Bluetooth"
        BluetoothDevice.DEVICE_TYPE_LE -> "Bluetooth Low Energy"
        BluetoothDevice.DEVICE_TYPE_DUAL -> "Support both BLE & LE"
        else -> "Unknown"
    }
}

fun getDeviceClass(deviceClass: BluetoothClass): String {
    return when (deviceClass.majorDeviceClass) {
        BluetoothClass.Device.Major.COMPUTER -> "Computer"
        BluetoothClass.Device.Major.PHONE -> "Phone"
        BluetoothClass.Device.Major.WEARABLE -> "Wearable"
        BluetoothClass.Device.Major.PERIPHERAL -> "Peripheral"
        BluetoothClass.Device.Major.IMAGING -> "Imaging"
        BluetoothClass.Device.Major.MISC -> "Misc"
        BluetoothClass.Device.Major.HEALTH -> "Health"
        BluetoothClass.Device.Major.NETWORKING -> "Networking"
        BluetoothClass.Device.Major.TOY -> "Toy"
        BluetoothClass.Device.Major.AUDIO_VIDEO -> "Audio_Video"
        BluetoothClass.Device.Major.UNCATEGORIZED -> "Uncategorized"
        BluetoothClass.Device.WEARABLE_WRIST_WATCH -> "WEARABLE_WRIST_WATCH"
        BluetoothClass.Device.AUDIO_VIDEO_CAMCORDER -> "AUDIO_VIDEO_CAMCORDER"
        BluetoothClass.Device.AUDIO_VIDEO_CAR_AUDIO -> "AUDIO_VIDEO_CAR_AUDIO"
        BluetoothClass.Device.WEARABLE_PAGER -> "WEARABLE_PAGER"
        BluetoothClass.Device.WEARABLE_JACKET -> "WEARABLE_JACKET"
        BluetoothClass.Device.WEARABLE_HELMET -> "WEARABLE_HELMET"
        BluetoothClass.Device.WEARABLE_GLASSES -> "WEARABLE_GLASSES"
        BluetoothClass.Device.TOY_VEHICLE -> "TOY_VEHICLE"
        BluetoothClass.Device.TOY_ROBOT -> "TOY_ROBOT"
        BluetoothClass.Device.TOY_GAME -> "TOY_GAME"
        BluetoothClass.Device.TOY_DOLL_ACTION_FIGURE -> "TOY_DOLL_ACTION_FIGURE"
        BluetoothClass.Device.TOY_CONTROLLER -> "TOY_CONTROLLER"
        BluetoothClass.Device.PHONE_SMART -> "PHONE_SMART"
        BluetoothClass.Device.PHONE_MODEM_OR_GATEWAY -> "PHONE_MODEM_OR_GATEWAY"
        BluetoothClass.Device.PHONE_ISDN -> "PHONE_ISDN"
        BluetoothClass.Device.PHONE_CORDLESS -> "PHONE_CORDLESS"
        BluetoothClass.Device.PHONE_CELLULAR -> "PHONE_CELLULAR"
        BluetoothClass.Device.PERIPHERAL_POINTING -> "PERIPHERAL_POINTING"
        BluetoothClass.Device.PERIPHERAL_NON_KEYBOARD_NON_POINTING -> "PERIPHERAL_NON_KEYBOARD_NON_POINTING"
        BluetoothClass.Device.PERIPHERAL_KEYBOARD_POINTING -> "PERIPHERAL_KEYBOARD_POINTING"
        BluetoothClass.Device.PERIPHERAL_KEYBOARD -> "PERIPHERAL_KEYBOARD"
        BluetoothClass.Device.HEALTH_WEIGHING -> "HEALTH_WEIGHING"
        BluetoothClass.Device.HEALTH_THERMOMETER -> "HEALTH_THERMOMETER"
        BluetoothClass.Device.HEALTH_PULSE_RATE -> "HEALTH_PULSE_RATE"
        BluetoothClass.Device.HEALTH_PULSE_OXIMETER -> "HEALTH_PULSE_OXIMETER"
        BluetoothClass.Device.HEALTH_GLUCOSE -> "HEALTH_GLUCOSE"


        else -> "Other"
    }
}

data class BluetoothDeviceItem(
    val device: BluetoothDevice,
    var lastSeen: Instant,
    var supportedUuids: List<UUID> = listOf()
)