package com.estudos.estatistica.ui.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estudos.estatistica.model.CalculationData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class TableViewModel(
    calculationData: CalculationData
) : ViewModel() {


    private val _calculationData = MutableLiveData<CalculationData>()
    val calculationData: LiveData<CalculationData> = _calculationData

    private val _uri = MutableLiveData<Uri?>()
    val uriPath: LiveData<Uri?> = _uri

    init {
        _calculationData.value = calculationData
    }

    fun processBitmap(context: Context, image: Bitmap) {
        viewModelScope.launch(Dispatchers.Default) {
            kotlin.runCatching {
                val imagesFolder = File(context.cacheDir, "images")
                imagesFolder.mkdirs()
                val file = File(imagesFolder, "shared_image.png")
                val stream = FileOutputStream(file)
                image.compress(Bitmap.CompressFormat.PNG, 90, stream)
                stream.flush()
                stream.close()
                _uri.value = FileProvider.getUriForFile(context, "com.estudos.estatistica.file-provider", file)
            }.getOrElse {
                it.printStackTrace()
                _uri.value = null
            }
        }
    }
}