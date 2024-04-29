package com.example.nfkhusq.Communication

import timber.log.Timber
import java.io.IOException
import java.io.OutputStream

fun SendData(outputStream: OutputStream, data: String){
    try {
        outputStream.write(data.toByteArray())
        outputStream.flush()
        Timber.d("Data send: $data")
    }catch (e: IOException){
        Timber.e("Error when sending data", e)
    }
}