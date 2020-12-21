package com.example.demo.auth;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class TokenAuthorization {

    public TokenAuthorization() {

    }

    public static String getAccessToken() {
        HttpURLConnection connection;
        try {
            URL getAccessTokenUrl = new URL("https://covidtracker-auth.nw.r.appspot.com/api/token");
            connection = (HttpURLConnection) getAccessTokenUrl.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type","application/json");
            String jsonBody =  "{\"email\": \"sa.core@fii.covidtracker.test\",\"refresh_token\":\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ZmI2NzUzMTRhNzE4NzEzYzZlMDJkYWYiLCJlbWFpbCI6InNhLmNvcmVAZmlpLmNvdmlkdHJhY2tlci50ZXN0IiwiaWF0IjoxNjA1NzkzMDczfQ.E50kNxh5q7eK6rvXygf6CIarNSWIq9Qf2wkaMiW2Zz8\"}";
            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
            osw.write(jsonBody);
            osw.flush();
            osw.close();
            os.close();
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            JSONObject accessTokenJson = new JSONObject(response.toString());
            return accessTokenJson.getString("access_token");
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isRequestAuthorized(String accessToken) {
        HttpURLConnection connection;
        try {
            URL authorizeRequestUrl = new URL("https://covidtracker-auth.nw.r.appspot.com/api/authorize");
            connection = (HttpURLConnection) authorizeRequestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);
            connection.setDoOutput(true);
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            System.out.println(response.toString());
            return connection.getResponseCode() == 200;
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
