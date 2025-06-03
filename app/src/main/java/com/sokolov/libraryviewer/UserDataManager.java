package com.sokolov.libraryviewer;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;

public class UserDataManager {
    private static final String FILE_NAME = "user_data.json";

    private static UserData user;

    public static void saveUserData(Context context, UserData data) {
        try {
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            osw.write(new Gson().toJson(data));
            osw.close();
            user = data;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadUserData(Context context) {
        try {
            FileInputStream fis = context.openFileInput(FILE_NAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            StringBuilder jsonBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }

            reader.close();
            Type type = new TypeToken<UserData>() {}.getType();
            user = new Gson().fromJson(jsonBuilder.toString(), type);

        } catch (IOException e) {
            user = null;
        }
    }

    public static void deleteUserData(Context context) {
        context.deleteFile(FILE_NAME);
    }

    public static UserData getUser() {
        return user;
    }
}

