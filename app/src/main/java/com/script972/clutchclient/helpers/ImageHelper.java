package com.script972.clutchclient.helpers;

import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;

import com.script972.clutchclient.R;
import com.script972.clutchclient.core.ClutchApplication;
import com.script972.clutchclient.domain.api.ApiClient;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class ImageHelper {

    private static String pachImageStorage = "/data/user/0/com.script972.clutchclient/app_imageDir";

    public static Bitmap readBitmapFromInternalStorage(String filePath) {
        Log.i("imageservice", "imageservice="+filePath);
        try {
            File f = new File(pachImageStorage, filePath);
            Log.i("imageservice", "imageservice="+f.getPath());
            return BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String saveBitmapToInternalStorage(Bitmap bitmapImage, Uri uri) {
        String fileName = formatFileName(uri);
        Context context = ClutchApplication.getApplication().getBaseContext();
        // path to /data/data/yourapp/app_data/imageDir
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath = new File(directory, fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath()+"/"+fileName;
    }

    private static String formatFileName(Uri uri){
        Context context = ClutchApplication.getApplication().getBaseContext();
        ContentResolver cR = context.getContentResolver();
        ContextWrapper cw = new ContextWrapper(context);
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        // path to /data/data/yourapp/app_data/imageDir
       return String.valueOf(Calendar.getInstance().getTimeInMillis()) + "." +
                mime.getExtensionFromMimeType(cR.getType(uri));
    }


}
