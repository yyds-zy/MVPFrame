package com.free.fileupload.util;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class FileUtils {

    public static boolean createJsonFile(String jsonData,String filePath){
        boolean flag = true;
        try {
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            Writer writer = new OutputStreamWriter(new FileOutputStream(file));
            writer.write(jsonData);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }


    public static String readJson(String filePath){
        File file = new File(filePath);
        BufferedReader reader = null;
        StringBuffer data = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String temp = null;
            while ((temp = reader.readLine()) != null) {
                data.append(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return data.toString();
    }

    public static File uriToFileApiQ(Uri uri, Context context) {
        File file = null;
        if (uri == null) return file;
        //android10????????????
        if (uri.getScheme().equals(ContentResolver.SCHEME_FILE)) {
            file = new File(uri.getPath());
        } else if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //??????????????????????????????
            ContentResolver contentResolver = context.getContentResolver();
            String displayName = System.currentTimeMillis() + Math.round((Math.random() + 1) * 1000)
                    + "." + MimeTypeMap.getSingleton().getExtensionFromMimeType(contentResolver.getType(uri));

            try {
                InputStream is = contentResolver.openInputStream(uri);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;//????????????????????????1/2
                options.inPreferredConfig = Bitmap.Config.ARGB_4444;
                Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);

                File cache = new File(context.getCacheDir().getAbsolutePath(), displayName);
                FileOutputStream fos = new FileOutputStream(cache);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);

                android.os.FileUtils.copy(is, fos);
                file = cache;
                fos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}
