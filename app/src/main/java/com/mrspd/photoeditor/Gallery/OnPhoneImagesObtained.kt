package com.mrspd.photoeditor.Gallery

import java.util.*

interface OnPhoneImagesObtained {
    fun onComplete(albums: Vector<PhoneAlbum>)
    fun onError()
}