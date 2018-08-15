package com.example.app.testapp.httpCon;

import android.content.Context;
import android.util.Log;

import com.example.app.testapp.Person;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PersonFetchr {
    private static final String LOG_TAG = "myLogs";

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                ": with " + urlSpec);
            }

            int bytesRead = 0;
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

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public List<Person> fetchItems() {

        List<Person> items = new ArrayList<>();

        try {
            String jsonString = getUrlString(
                    "http://gitlab.65apps.com/65gb/static/raw/master/testTask.json");
            Log.d(LOG_TAG, "Received JSON: ");
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
        throws IOException, JSONException {
        JSONArray responseJsonArray = jsonBody.getJSONArray("response");

        for (int i = 0; i < responseJsonArray.length(); i++) {
            JSONObject responseJsonObject = responseJsonArray.getJSONObject(i);
            JSONArray specialtyJsonArray = responseJsonObject.getJSONArray("specialty");

            Person item = new Person();
            item.setId(i);
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

            for (int j = 0; j < specialtyJsonArray.length(); j++) {
                JSONObject specialtyJsonObject = specialtyJsonArray.getJSONObject(j);
                item.setSpec(specialtyJsonObject.getString("name"));
            }
            items.add(item);
        }
    }
}
