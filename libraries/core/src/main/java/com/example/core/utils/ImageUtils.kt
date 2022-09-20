package com.example.core.utils

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

fun ImageView.loadImage(resDrawable: Int) {
    Glide.with(this)
        .load(resDrawable)
        .into(this)
}

fun ImageView.loadImage(urlImage: String?, placeHolder: Int, isCircle: Boolean = false) {
    if (isCircle) {
        Glide.with(this)
            .load(urlImage)
            .circleCrop()
            .placeholder(placeHolder)
            .into(this)
    } else {
        Glide.with(this)
            .load(urlImage)
            .placeholder(placeHolder)
            .into(this)
    }

}

fun ImageView.loadImage(urlImage: String?, isCircle: Boolean = false) {
    if (isCircle) {
        Glide.with(this)
            .load(urlImage)
            .circleCrop()
            .into(this)
    } else {
        Glide.with(this)
            .load(urlImage)
            .into(this)
    }
}

fun ImageView.loadImage(urlImage: File, placeHolder: Int, isCircle: Boolean = false) {
    if (isCircle) {
        Glide.with(this)
            .load(urlImage)
            .circleCrop()
            .placeholder(placeHolder)
            .into(this)
    } else {
        Glide.with(this)
            .load(urlImage)
            .placeholder(placeHolder)
            .into(this)
    }

}

fun ImageView.loadImage(urlImage: Uri?, placeHolder: Int, isCircle: Boolean = false) {
    if (isCircle) {
        Glide.with(this)
            .load(urlImage)
            .circleCrop()
            .placeholder(placeHolder)
            .into(this)
    } else {
        Glide.with(this)
            .load(urlImage)
            .placeholder(placeHolder)
            .into(this)
    }
}

fun Context.createImageFile(): File {
    // Create an image file name
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val storageDir = File(this.filesDir.path + "/ProfileImage")
    if (!storageDir.exists()) {
        storageDir.mkdirs()
    }
    return File.createTempFile(
        imageFileName,  // prefix
        ".jpg",         // suffix
        storageDir      // directory
    )
}

fun getPathFromInputStreamUri(
    context: Context,
    uri: Uri
): String? {
    var inputStream: InputStream? = null
    var filePath: String? = null
    if (uri.authority != null) {
        try {
            inputStream = context.contentResolver.openInputStream(uri)
            val photoFile: File? = createTemporalFileFrom(context, inputStream)
            filePath = photoFile?.path
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                inputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    return filePath
}

//fun getRealPathFromURI(
//    mContext: Context,
//    contentURI: Uri?
//): String? {
//    var result: String? = null
//    var cursor: Cursor? = null
//    try {
//        val proj = arrayOf(MediaStore.Video.Media.DATA)
//        val mContentResolver = mContext.contentResolver
//        val mime = mContentResolver.getType(contentURI!!)
//        cursor = mContentResolver.query(contentURI, proj, null, null, null)
//        if (cursor == null) {
//            return null
//        } else {
//            cursor.moveToFirst()
//            val columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
//            if (columnIndex > -1) result = cursor.getString(columnIndex)
//            cursor.close()
//        }
//    } catch (e: Exception) {
//        return null
//    } finally {
//        cursor?.close()
//    }
//    return result
//}

fun createTemporalFileFrom(
    context: Context,
    inputStream: InputStream?
): File? {
    var targetFile: File? = null
    if (inputStream != null) {
        var read: Int
        val buffer = ByteArray(8 * 1024)
        targetFile = File(context.cacheDir, "tempPicture.jpg")
        val outputStream: OutputStream = FileOutputStream(targetFile)
        while (inputStream.read(buffer).also { read = it } != -1) {
            outputStream.write(buffer, 0, read)
        }
        outputStream.flush()
        try {
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    return targetFile
}

fun getRealPathFromURI(context: Context, contentUri: Uri): String? {
    val proj = arrayOf(MediaStore.Audio.Media.DATA)
    //Cursor cursor = managedQuery(contentUri, proj, null, null, null);
    val cursor = context.contentResolver.query(
        contentUri,
        proj,
        null,
        null,
        null
    ) //Since manageQuery is deprecated
    val column_index = cursor?.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
    cursor?.moveToFirst()
    return cursor?.getString(column_index!!)
}