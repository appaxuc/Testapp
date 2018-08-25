package com.example.app.testapp;

import android.annotation.SuppressLint;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PersonFetchr {
    private static final String LOG_TAG = "myLogs";
    private static Calendar todayC = Calendar.getInstance();

    private byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                ": with " + urlSpec);
            }

            int bytesRead;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    private String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public List<Person> fetchItems() {

        List<Person> items = new ArrayList<>();

        try {
            String jsonString = getUrlString(
                    "http://gitlab.65apps.com/65gb/static/raw/master/testTask.json");
            JSONObject jsonBody = new JSONObject(jsonString);
            parseItems(items, jsonBody);
        } catch (IOException ioe) {
            Log.e(LOG_TAG, "Failed to fetch URL: ", ioe);
        } catch (JSONException je) {
            Log.e(LOG_TAG, "Failed to parse JSON", je);
        }
        return items;
    }

    private void parseItems(List<Person> items, JSONObject jsonBody)
        throws JSONException {
        JSONArray responseJsonArray = jsonBody.getJSONArray("response");

        for (int i = 0; i < responseJsonArray.length(); i++) {
            JSONObject responseJsonObject = responseJsonArray.getJSONObject(i);
            JSONArray specialtyJsonArray = responseJsonObject.getJSONArray("specialty");

            Person item = new Person();
            String fName = responseJsonObject.getString("f_name");
            fName = fName.substring(0, 1).toUpperCase() + fName.substring(1).toLowerCase();
            item.setFirstName(fName);
            String lName = responseJsonObject.getString("l_name");
            lName = lName.substring(0, 1).toUpperCase() + lName.substring(1).toLowerCase();
            item.setLastName(lName);
            String birth = responseJsonObject.getString("birthday");
            if (birth.isEmpty() || birth == "null") {
                birth = "-";
            } else if (birth.charAt(4) == '-') {
                birth = birth.substring(8, 10) + "." +
                        birth.substring(5, 7) + "." +
                        birth.substring(0,4);
            } else {
                birth = birth.replace('-', '.');
            }
            item.setBirth(birth);

            int age;
            if (birth.length() < 4) {
                age = -1;
                item.setAge(age);
            } else {
                age = CalculateAge(birth);
                item.setAge(age);
            }
            for (int j = 0; j < specialtyJsonArray.length(); j++) {
                JSONObject specialtyJsonObject = specialtyJsonArray.getJSONObject(j);
                item.setSpec(specialtyJsonObject.getString("name"));
                item.setSpecId(specialtyJsonObject.getInt("specialty_id"));
            }
            items.add(item);
        }
    }

    private static int CalculateAge(String birthday) {
        Date birthD;
        Calendar birthC = Calendar.getInstance();

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        try {
            birthD = format.parse(birthday);
        } catch (ParseException e) {
            birthD = new Date();
            e.printStackTrace();
        }
        birthC.setTime(birthD);

        int age = todayC.get(Calendar.YEAR) - birthC.get(Calendar.YEAR);
        if (todayC.get(Calendar.DAY_OF_YEAR) <= birthC.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        return age;
    }
}
