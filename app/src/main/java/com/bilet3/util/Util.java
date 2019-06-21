package com.bilet3.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bilet3.db.ArticoleDbHelper;
import com.bilet3.model.Articol;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.bilet3.activities.MainActivity.FILE_NAME;

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

    public static JSONArray getJSONArray(Context context) throws IOException, JSONException {
        FileInputStream fileInputStream = null;
        fileInputStream = context.openFileInput(FILE_NAME);
        InputStreamReader isr = new InputStreamReader(fileInputStream);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String text;

        while ((text = br.readLine()) != null) {
            sb.append(text);
        }

        return new JSONArray(sb.toString());
    }

    public static int getJsonArrayIndex(JSONArray jsonArray, Articol articol) {
        Gson gson = new Gson();
        JSONObject jsonObject;
        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObject = jsonArray.optJSONObject(i);
            JsonElement jsonElement = gson.fromJson(jsonObject.toString(), JsonElement.class);
            Articol articolFromJson = gson.fromJson(jsonElement, Articol.class);
            if (articolFromJson.equals(articol)) {
                return i;
            }
        }
        return 0;
    }

    public static void selectAll(ArticoleDbHelper mDbHelper, SQLiteDatabase sqLiteDatabase){
        Cursor cursor = mDbHelper.selectInfo(sqLiteDatabase);
        List<Articol> articolList = new ArrayList<>();
        if (cursor.moveToFirst()){

            do {
                String titlu,abstractArticol,autori;
                titlu = cursor.getString(0);
                abstractArticol = cursor.getString(1);
                autori = cursor.getString(2);

                Articol articolSelect = new Articol(titlu,abstractArticol,autori);
                articolList.add(articolSelect);
            }
            while (cursor.moveToNext());
        }
    }

}
