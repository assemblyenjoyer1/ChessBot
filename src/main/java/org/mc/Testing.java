package org.mc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Testing {

    public static void main(String[] args) {

        String name = "Dawidsonek";
        String apiKey = "RGAPI-bab6391d-2bfc-481a-b7d4-0991a10fb5de";
        String region = "euw1";
        String apiUrl = "https://" + region + ".api.riotgames.com/lol/summoner/v4/summoners/by-name/" + name + "?api_key=" + apiKey;

        try {
            URL url = new URL(apiUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                System.out.println("JSON Response:");
                System.out.println(response);
            } else {
                System.out.println("HTTP GET request failed with response code: " + responseCode);
            }

            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
