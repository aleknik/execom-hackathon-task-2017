package com.example.aleknik.execomhackathon2017.utils;

import android.os.Environment;
import android.util.Log;

import org.androidannotations.annotations.EBean;

import java.io.File;
import java.io.IOException;

@EBean
public class FileUtils {

    public File createImageFile() throws IOException {
        // Create an image file name
        final String timeStamp = String.valueOf(System.currentTimeMillis());
        final String imageFileName = "JPEG_" + timeStamp + "_";
        Log.d("imageFileName", imageFileName);
        final File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        final File imageDir = new File(String.format("%s/ExecomHackathon2017", storageDir.getAbsolutePath()));
        imageDir.mkdir();

        return File.createTempFile(
                imageFileName,
                ".jpg",
                imageDir
        );
    }

    public boolean deleteImage(String path) {
        if (path == null)
        {
            return false;
        }
        final File file = new File(path);
        final File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        final File imageDir = new File(String.format("%s/ExecomHackathon2017", storageDir.getAbsolutePath()));

        return path.startsWith(imageDir.getAbsolutePath()) && file.delete();
    }

}