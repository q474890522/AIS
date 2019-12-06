package com.example.ais;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;


public class auth {


    public static String getAuth() {

        String clientId = "G7AqxHlhGNrb3t8tZwzU1MVf";

        String clientSecret = "pFuCgB4HscigzVBs7lFOGqhZFBTGB5xa";
        return getAuth(clientId, clientSecret);
    }


    public static String getAuth(String ak, String sk) {

        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost

                + "grant_type=client_credentials"

                + "&client_id=" + ak

                + "&client_secret=" + sk;
        try {
            URL realUrl = new URL(getAccessTokenUrl);

            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            Map<String, List<String>> map = connection.getHeaderFields();

            for (String key : map.keySet()) {
                System.err.println(key + "--->" + map.get(key));
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }

            System.err.println("result:" + result);
            JSONObject jsonObject = new JSONObject(result);
            String access_token = jsonObject.getString("access_token");
            connection.disconnect();
            return access_token;
        } catch (Exception e) {
            System.err.printf("get token fail!");
            e.printStackTrace(System.err);
        }
        return null;
    }

}
