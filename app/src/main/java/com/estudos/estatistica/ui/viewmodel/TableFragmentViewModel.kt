package com.estudos.estatistica.ui.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.service.controls.ControlsProviderService
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.FileProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estudos.estatistica.model.Calculo
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class TableFragmentViewModel(
    private val calculo: Calculo
): ViewModel() {


    private val _modelCalculo = MutableLiveData<Calculo>()
    val modelCalculo: LiveData<Calculo> = _modelCalculo

    private val _uri = MutableLiveData<Uri?>()
    val uri: LiveData<Uri?> = _uri

    init {
        _modelCalculo.value = calculo
    }

    fun processBitmap(context: Context, image: Bitmap){
            val imagesFolder = File(context.cacheDir, "images")
            try {
                imagesFolder.mkdirs()
                val file = File(imagesFolder, "shared_image.png")
                val stream = FileOutputStream(file)
                image.compress(Bitmap.CompressFormat.PNG, 90, stream)
                stream.flush()
                stream.close()
                _uri.value = FileProvider.getUriForFile(context, "com.estudos.estatistica.fileprovider", file)
            } catch (e: IOException) {
                _uri.value = null
                Log.d(ControlsProviderService.TAG, "IOException while trying to write file for sharing: " + e.message)
            }
    }
}