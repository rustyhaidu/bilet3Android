package com.bilet3.util;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Util {

    public static JSONArray  getJsonFromAssetFile(Context context){
        String json;
        JSONArray jsonArray = null;
        try {
            InputStream is = context.getAssets().open("a.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, StandardCharsets.UTF_8);
            jsonArray = new JSONArray(json);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public static void writeFile(byte[] data, File file) throws IOException {

        BufferedOutputStream bos = null;

        try {
            FileOutputStream fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(data);
        }
        finally {
            if (bos != null) {
                try {
                    bos.flush ();
                    bos.close ();
                }
                catch (Exception e) {
                }
            }
        }
    }

    public  static void writeFileOnInternalStorage(Context mcoContext,String sFileName, String sBody){
        File file = new File(mcoContext.getFilesDir(),"mydir");
        if(!file.exists()){
            file.mkdir();
        }

        try{
            File gpxfile = new File(file, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();

        }catch (Exception e){
            e.printStackTrace();

        }
    }
}
