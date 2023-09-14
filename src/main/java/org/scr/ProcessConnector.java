package org.scr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ProcessConnector {

        public static void main(String[] args) {
            String processCommand = "C:\\\\Riot Games\\\\League of Legends\\\\LeagueClientUx.exe"; // Replace with the command for the target game

            try {
                Process process = Runtime.getRuntime().exec(processCommand);
                InputStream inputStream = process.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

                // Wait for the process to finish
                int exitCode = process.waitFor();
                System.out.println("pid:" + process.pid());
                System.out.println("Process finished with exit code: " + exitCode);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

}